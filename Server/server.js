
global.submissions = submissons = ["test1", "test2", "test3"];

var addr = "162.243.254.35"
var addr = "localhost"; 

var http = require('http');
http.createServer(function (req, res) {
  res.writeHead(200, {'Content-Type': 'text/html'});

  if(submissons.length > 0){
  	res.write("<table width='100%' border='1'>");
	  for(var i = 0; i < submissons.length; i++){
	  	res.write("<tr><td><input type='checkbox'>Approve</input></td><td><input type='checkbox'>Reject</input></td>");
	  	res.write("<td width='80%'>"+submissions[i]+"<br/></td></tr>");
	  }
	  res.write("</table><br/><br/><input type='submit' value='Approve'/><input type='submit' value='Reject'>");
	}
  res.end();
}).listen(80, addr);

var net = require("net");
net.createServer(function(socket){

	socket.on("data", function(data){
//		var s = data.split("\n");
		
		submissions[submissions.length] = data;
		
	});

}).listen(1337, addr);

console.log('Server running at http://127.0.0.1:1337/');
