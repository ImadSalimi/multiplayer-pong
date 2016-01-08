var app = require('express')(),
	server = require('http').Server(app),
	io = require('socket.io')(server);

server.listen(1337, function() {
	console.log("Server listening at port 1337...");
});

var connectedPlayers = [];

io.on('connection', function(socket) {
	socket.on('userConnected', function(data){
		connectedPlayers.push({id: socket.id, username: data.username});
		console.log(data.username + " connected!");
		io.emit('connectedPlayers', connectedPlayers);
	});
	socket.on('disconnect', function() {
		for(var i = 0; i < connectedPlayers.length; i++) {
			if (connectedPlayers[i].id == socket.id) {
				console.log(connectedPlayers[i].username + " disconnected!");
				connectedPlayers.splice(i, 1);
			}
		}
	});
});

