var urlRoot = "http://localhost:8080/fintpWebServices/api/";
var refreshSeconds = 30;


$(function() {
	ajaxGetQueues();
	(function poll(){
		setTimeout(function(){
			console.log("update");
			ajaxGetQueues();
		}, refreshSeconds * 1000);
	})();
	
});

function ajaxGetQueues(){
	var url = urlRoot + "queues";
	var object = new Object();
	object.queues = getQueuesArray();
	var dataJSON = JSON.stringify(object);
	
	$.ajax({
		
		data: {data: dataJSON, name:'test'},
		method : 'POST',
		url : "queuesDynamic",
		dataType : 'json',
		async : true,
		success : function(response) {
			console.log(response);
			var numbers = response.numbers;
			for(var index = 0; index < numbers.length; index++){
				console.log("index " + index + " : " + numbers[index]);
				$("#queuesTable tr:nth-child("+ (index + 2) +") td:nth-child(4)").text(numbers[index]);
			}
		},
		error : function(jqXHR, text, errorThrown) {
			//HandleAjaxError(jqXHR, text, errorThrown);
		}
	});
}

function getQueuesArray(){
	var data = [];
	$("#queuesTable tr td:first-child").each(function(){
		data.push($(this).text());
	});
	return data;
}