var app = require('express')(),
	server = require('http').Server(app),
	io = require('socket.io')(server);

server.listen(8080, function() {
	console.log("Server listening at port 8080...");
});

var connectedPlayers = [];

io.on('connection', function(socket) {
	console.log(socket.id + " connected");
	socket.on('userConnected' ,function(data){
		
		console.log(connectedPlayers);
	})
	socket.on('getPlayers',function(){
		socket.emit('connectedPlayers', connectedPlayers);

	})
	socket.on('disconnect', function() {
		
	});
	connectedPlayers.push({username : "",id : socket.id});
});

