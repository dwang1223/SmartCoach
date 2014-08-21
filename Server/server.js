var storage = require('node-persist');
var express = require('express');
var bodyParser = require('body-parser')
var fs = require('fs');
var url = require('url');

var redirects = [];

storage.initSync();



if(!storage.getItem('pending')){
	storage.setItem('pending', []);
}

if(!storage.getItem('solutions')){
	storage.setItem('solutions', []);
}

var httpApp = express();

httpApp.use(bodyParser.json());
httpApp.use(bodyParser.urlencoded());


httpApp.get("/admin", function(req, res){
	console.log("index="+redirects.indexOf(req.query.redirect));

	res.writeHead(200, {'Content-Type': 'text/html'});	
	res.write("<html><head><title></title></head><body background='#eeeeee'><h1>SmartCoach Social Features Manager</h1>");
	if(req.query.status){
		if(req.query.status == 'fail'){
			res.write("<h3><span style='color:red;'>Action failed, please try again.</span></h3>");
		} else if (req.query.status == 'approved' || req.query.status == 'rejected') {
			res.write("<h3><span style='color:green;'>"+req.query.count + " submission(s) "+req.query.status+".</span></h3>");
		}
	}
	res.write(listPendingSubmissions());
	res.end("</body></html>");
});

httpApp.post("/admin/approve", function(req, res){

	var pending = storage.getItem('pending');
	var solutions = storage.getItem('solutions');

	var selected = [];

	pending.forEach(function(item){
		if(req.body[item.id]){
			selected.push(item);
			pending.splice(pending.indexOf(item), 1);
		}
	});

	storage.setItem('pending', pending);


	console.log("selected: "+selected);

	var redirect = new Date().getTime();
	redirects.push(redirect);
	console.log("redirects= "+redirects);

	if(req.body.action == 'approve'){

		console.log("Approving "+selected.length+" submissions");
		selected.forEach(function(item){
			solutions.push(item);
		})

		storage.setItem('solutions', solutions);

		res.redirect('/admin?status=approved&count='+selected.length+"&redirect="+redirect);
	} else if (req.body.action == 'reject'){
		res.redirect('/admin?status=rejected&count='+selected.length+"&redirect="+redirect);
	} else {
		res.redirect('/admin?status=fail&redirect='+redirect);
	}

});

httpApp.post("/submit", function(req, res){
	var submission = JSON.parse(req.body.submission);
	submission.id = "submission"+(new Date().getTime());
	console.log("Incoming Submission: "+submission);
	var pending = storage.getItem('pending');
	pending.push(submission);
	storage.setItem('pending', pending);
});

httpApp.post("/suggest", function(req, res){
	var category = req.body.category;
	var path = JSON.parse(req.body.path);
	var suggestions = getSuggestions(category, path);
	console.log(suggestions);
	res.json(suggestions);
})

function listPendingSubmissions(){
	console.log("listing submissions...");
	var html = "";
	var pending = storage.getItem('pending');
	if(pending.length > 0){
		html += "<form method='post' action='/admin/approve'><table width='80%' align='center' border='1'>";
		  for(var i = 0; i < pending.length; i++){
		  	html += "<tr><td><input type='checkbox'name='"+pending[i].id+"''></td>";
		  	html += "<td width='90%'>";
		  	html += renderSubmission(pending[i]);
		  	html += "</td></tr>";
		  }
		  html += "</table><br/><br/>"
		  html += "<button name='action' type='submit' value='approve' />Approve</button>";
		  html += "<button name='action' type='submit' value='reject' />Reject</button></form>";
	} else {
		html = "No new submissions.";
	}
	  return html;
}

function renderSubmission(submission){
	var html = "";	
	html += "<h3 style='display:inline;'> ["+submission.category+"]</h3>";
	html += "<h2 style='display:inline;'> "+submission.solution+"</h2>";
	html += "<br/><b>Path:</b>";
	html += "<ul>";
	for(var i = 0; i < submission.path.length; i++){
		html += "<li><b>"+submission.path[i].questionID+": </b>";

		for(var j = 0; j < submission.path[i].responses.length-1; j++){
			html += submission.path[i].responses[j] + ", ";
		}
		html += submission.path[i].responses[submission.path[i].responses.length-1];
		html += "</li>";

	}
	html += "<ul>";

	return html;
}

function getSuggestions(category, path){
	var solutions = storage.getItem('solutions');
	var selected = [];
	solutions.forEach(function(item){
		if(category == item.category){
			selected.push(item.solution);
		}
	});

	return selected;
}

httpApp.listen(80);
console.log('Server running');
