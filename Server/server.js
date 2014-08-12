
global.submissions = submissons = ["test1", "test2"];

var http = require('http');
http.createServer(function (req, res) {
  res.writeHead(200, {'Content-Type': 'text/html'});

  for(var i = 0; i < submissons.length; i++){
  	res.write(submissions[i]+"<br/>");
  }
  res.end();
}).listen(80, '162.243.254.35');

var net = require("net");
net.createServer(function(socket){

	socket.on("data", function(data){
//		var s = data.split("\n");
		
		submissions[submissions.length] = data;
		
	});

}).listen(1337, '162.243.254.35');

console.log('Server running at http://127.0.0.1:1337/');
