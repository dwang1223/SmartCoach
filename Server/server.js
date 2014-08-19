
global.submissions = submissons = ["test1", "test2", "test3"];

var addr = "162.243.254.35"
//var addr = "localhost"; 

var http = require('http');
http.createServer(function (req, res) {
  res.writeHead(200, {'Content-Type': 'text/html'});

  if(submissons.length > 0){
	res.write("<h1>SmartCoach Social Features Manager</h1><br/>");
  	res.write("<table width='80%' align='center' border='1'>");
	  for(var i = 0; i < submissons.length; i++){
	  	res.write("<tr><td><input type='checkbox'></td>");
	  	res.write("<td width='90%'>"+submissions[i]+"<br/></td></tr>");
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
