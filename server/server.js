var app = require('express')(),
	server = require('http').Server(app),
	io = require('socket.io')(server),
	port = process.env.PORT || 1337;

server.listen(port, function() {
	console.log("Server listening at port "+port+"...");
});

var connectedPlayers = [];
var games = {};
var gamesSync = {};

io.on('connection', function(socket) {
	// Lobby
	socket.on('getConnectedPlayers', function() {
		io.emit('connectedPlayers', connectedPlayers);
	});
	socket.on('userConnected', function(username){
		socket.username = username;
		connectedPlayers.push({id: socket.id, username: username});
		io.emit('connectedPlayers', connectedPlayers);
		// Wait 500 ms before sending the userConnected event
		setTimeout(function() {
			socket.broadcast.emit('userConnected', username);
		}, 500);
		console.log(username + " connected!");
	});
	socket.on('sendMessage', function(data) {
		var id = getPlayerId(data.to);
		socket.broadcast.to(id).emit('getMessage', data);
	});
	socket.on('friendRequest', function(data) {
		// Check if the recipient is connected
		if (getPlayerId(data.to) != null) {
			var id = getPlayerId(data.to);
			socket.broadcast.to(id).emit('friendRequest', data.from);
		}		
	});
	socket.on('friendRequestAck', function(data) {
		if (getPlayerId(data.to) != null) {
			var id = getPlayerId(data.to);
			socket.broadcast.to(id).emit('friendRequestAck', data.from);
		}
		io.emit('connectedPlayers', connectedPlayers);
	});
	socket.on('challenge', function(data) {
		if (getPlayerId(data.recipient) != null) {
			var id = getPlayerId(data.recipient);
			socket.broadcast.to(id).emit('challenge', data.initiator);
		}
	});
	socket.on('challengeAck', function(data) {
		if (getPlayerId(data.initiator) != null) {
			var id = getPlayerId(data.initiator);
			socket.broadcast.to(id).emit('challengeAck', {opponent: data.recipient, accepted: data.accepted});
		}
	});
	socket.on('startGame', function(data) {
		if (getPlayerId(data.player1) != null && getPlayerId(data.player2) != null) {
			io.emit('startGame', data);
		}
	});
	// Game
	socket.on('paddleMoved', function(data) {
		socket.broadcast.emit('paddleMoved', data);
	});
	socket.on('playerScored', function(data) {
		if (games[socket.gameRoom].paused == false) {
			games[socket.gameRoom].paused = true;
			io.to(socket.gameRoom).emit('pause');
			var ya = getRandomIntInclusive(2, 3);
			io.to(socket.gameRoom).emit('reset', {username: data.username, ya: ya});
			games[socket.gameRoom].paused = false;
		}
	});
	socket.on('initializeGame', function(num) {
		var now = new Date();
		if (num == 1)
			gamesSync[socket.gameRoom].home = now;
		else if (num == 2)
			gamesSync[socket.gameRoom].away = now;
		var homeTime = gamesSync[socket.gameRoom].home;
		var awayTime = gamesSync[socket.gameRoom].away;
		if (homeTime !== null && awayTime !== 0) {
			if (Math.abs(homeTime.getTime() - awayTime.getTime()) <= 100 && !gamesSync[socket.gameRoom].synchro) {
				gamesSync[socket.gameRoom].synchro = true;
				var xa = 2;
				var ya = getRandomIntInclusive(2, 3);
				io.to(socket.gameRoom).emit('initializeGame', {xa: xa, ya: ya});
				games[socket.gameRoom] = new Game();
			}
		}
	});
	// Others
	socket.on('joinRoom', function(name) {
		socket.join(name);
		socket.gameRoom = name;
		gamesSync[socket.gameRoom] = {home: null, away: 0, synchro: false};
	});
	socket.on('disconnect', function() {
		for(var i = 0; i < connectedPlayers.length; i++) {
			if (connectedPlayers[i].id == socket.id) {
				console.log(connectedPlayers[i].username + " disconnected!");
				socket.broadcast.emit('userDisconnected', connectedPlayers[i].username);
				connectedPlayers.splice(i, 1);
				io.emit('connectedPlayers', connectedPlayers);
			}
		}
	});
});

function Game() {
	this.paused = false;
	this.score1 = 0;
	this.score2 = 0;
}

function getPlayerId(username) {
	var result = connectedPlayers.filter(function(player) { return player.username == username });
	if (result.length != 0) {
		var playerId = result[0].id;
		return playerId;
	}
	return null;
}

function getRandomIntInclusive(min, max) {
  return Math.floor(Math.random() * (max - min + 1)) + min;
}
