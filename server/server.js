var app = require('express')(),
	server = require('http').Server(app),
	io = require('socket.io')(server);

server.listen(8080, function() {
	console.log("Server listening at port 8080...");
})

io.on('connection', function(socket) {
	console.log(socket.id);
});
