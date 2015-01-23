//what percentage of conditions have to match for the solution to be suggested to the user
var MATCH_THRESHOLD = 0.3;

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

/**
* Get the admin panel page. Shows list of pending solutions
*/
httpApp.get("/", function(req, res){

	res.writeHead(200, {'Content-Type': 'text/html'});	
	res.write("<html><head><title></title></head><body background='#eeeeee'><h1 style='text-align:center;'>SmartCoach Social Features Manager</h1>");
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

/**
* Submit form to approve/reject pending solutions
* Redirects back to / with a status
*/
httpApp.post("/approve", function(req, res){
	var pending = storage.getItem('pending');
	var solutions = storage.getItem('solutions');

	var selected = [];

	//add items with selected ids to selected list
	for(var p = 0; p < pending.length; p++){
		var item = pending[p];
		if(req.body[item.id]){
			console.log("approving "+item.id);
			selected.push(item);			
		}
	}

	//remove selected items from the pending list
	selected.forEach(function(s){
			pending.splice(pending.indexOf(s), 1);
	});
	storage.setItem('pending', pending);


	var redirect = new Date().getTime();
	redirects.push(redirect);
	console.log("redirects= "+redirects);

	//if 'approve' was selected, add the selected items to the approved solutions
	if(req.body.action == 'approve'){
		selected.forEach(function(item){
			solutions.push(item);
		});
		storage.setItem('solutions', solutions);

		res.redirect('?status=approved&count='+selected.length+"&redirect="+redirect);
	} else if (req.body.action == 'reject'){
		res.redirect('?status=rejected&count='+selected.length+"&redirect="+redirect);
	} else {
		res.redirect('?status=fail&redirect='+redirect);
	}

});

/**
* Handles solution submissions from the app
* Stores submitted solution in the pending list for approval
*/
httpApp.post("/submit", function(req, res){
	var submission = JSON.parse(req.body.submission);
	submission.id = "submission"+(new Date().getTime());
	var pending = storage.getItem('pending');
	for(var i = 0; i < pending.length; i++){
		if(pending[i].solution == submission.solution){
			return;
		}
	}
	pending.push(submission);
	storage.setItem('pending', pending);
	
	res.writeHead(200, {'Content-Type': 'text/html'})
	res.write("success");
	res.end();
});

/**
* Handles suggesstion requests from the app.
* Responds a list of solutions (string) 
*/
httpApp.post("/suggest", function(req, res){
	var category = req.body.category;
	var conditions = JSON.parse(req.body.conditions);
	var suggestions = getSuggestions(category, conditions);
	console.log(suggestions);
	res.json(suggestions);
	res.end();
})

/**
* Creates the table to pending solutions for the admin page
*/
function listPendingSubmissions(){
	var html = "";
	var pending = storage.getItem('pending');
	if(pending.length > 0){
		html += "<form method='post' action='/approve'><table width='80%' align='center' border='1'>";
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
		html = "<p style='text-align:center'>No new submissions</p>";
	}
  	return html;
}

/**
* Creates element to display the given solutions
*/
function renderSubmission(submission){
	var html = "";	
	html += "<h3 style='display:inline;'> ["+submission.category+"]</h3>";
	html += "<h2 style='display:inline;'> "+submission.solution+"</h2>";
	html += "<br/><b>Conditions:</b>";
	html += "<ul>";
	for(var i = 0; i < submission.conditions.length; i++){
		html += "<li><b>"+submission.conditions[i]+"</b>";
		html += "</li>";
	}
	html += "<ul>";

	return html;
}

function getSuggestions(category, conditions){
	var solutions = storage.getItem('solutions');
	var selected = [];
	solutions.forEach(function(item){
		if(category == item.category){
			
			var matchedConditions = 0;

			//count how many of the conditions match
			item.conditions.forEach(function(c){
				if(conditions.indexOf(c) != -1){
					matchedConditions++;
				}
			});

			console.log(matchedConditions/conditions.length+" : "+item.solution);
			if(matchedConditions/conditions.length >= MATCH_THRESHOLD){
				selected.push(item.solution);
			}
		}
	});
	
	return selected;
}

httpApp.listen(80);
console.log('Server running');
