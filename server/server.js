var app = require('express')(),
	server = require('http').Server(app),
	io = require('socket.io')(server);

server.listen(1337, function() {
	console.log("Server listening at port 1337...");
});

var connectedPlayers = [];

io.on('connection', function(socket) {
	// Lobby
	socket.on('getConnectedPlayers', function() {
		io.emit('connectedPlayers', connectedPlayers);
	});
	socket.on('userConnected', function(username){
		connectedPlayers.push({id: socket.id, username: username});
		io.emit('connectedPlayers', connectedPlayers);
		// Wait 500 ms before sending the userConnected event
		setTimeout(function() {
			socket.broadcast.emit('userConnected', username);
		}, 500);
		console.log(username + " connected!");
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
	socket.on('ballLaunched', function() {
		var xa = Math.random() + 2;
		var ya = Math.random() + 2;
		io.to(socket.gameRoom).emit('ballLaunched', {xa: xa, ya: ya});
	});
	// Others
	socket.on('joinRoom', function(name) {
		socket.join(name);
		socket.gameRoom = name;
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

function getPlayerId(username) {
	var result = connectedPlayers.filter(function(player) { return player.username == username });
	if (result.length != 0) {
		var playerId = result[0].id;
		return playerId;
	}
	return null;
}

