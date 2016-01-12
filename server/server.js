var app = require('express')(),
	server = require('http').Server(app),
	io = require('socket.io')(server);

server.listen(1337, function() {
	console.log("Server listening at port 1337...");
});

var connectedPlayers = [];

io.on('connection', function(socket) {
	socket.on('userConnected', function(username){
		connectedPlayers.push({id: socket.id, username: username});
		io.emit('connectedPlayers', connectedPlayers);
		// Wait 500 ms before sending the userConnected event
		setTimeout(function() {
			socket.broadcast.emit('userConnected', username);
		}, 500);
		console.log(username + " connected!");
	});
	socket.on('disconnect', function() {
		for(var i = 0; i < connectedPlayers.length; i++) {
			if (connectedPlayers[i].id == socket.id) {
				console.log(connectedPlayers[i].username + " disconnected!");
				socket.broadcast.emit('userDisconnected', connectedPlayers[i].username);
				connectedPlayers.splice(i, 1);
			}
		}
	});
	socket.on('lobbySync', function() {
		socket.emit('lobbySync');
	});
});

