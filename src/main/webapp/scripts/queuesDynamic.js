var urlRoot =  $("#apiUrl").text();
var refreshSeconds = 60;
var timeOut = refreshSeconds * 1000;


$(function() {
	ajaxGetQueues();
	setInterval(function() {
		ajaxGetQueues();
	}, timeOut);
	
	//open buttons
	$("button.view").each(function(){
		$(this).button({ icons: { primary: "ui-icon-folder-open" } });
		$(this).click(function(){
			var queueName = $(this).parent().parent().find("td").eq(1).text();
			document.location.href = "./queues/"+ queueName+".htm";
			
		});
	});
	$("button.edit").each(function(){
		$(this).button({ icons: { primary: "ui-icon-pencil" }, disabled: false });
		$(this).click(function(){
			var queueName = $(this).parent().parent().find("td").eq(1).text();
			document.location.href = "./editQueue.htm?queue="+ queueName;
		});
	});
	$("button.delete").each(function(){
		$(this).button({ icons: { primary: "ui-icon-trash" }, disabled : false });
		$(this).click(function(){
			var queueName = $(this).parent().parent().find("td").eq(1).text();
			document.location.href = "./queues/deleteQueue.htm?queue="+ queueName;
		});
	});
	
	$("#addQueue").click(function(){
		document.location.href = "./addQueue.htm";
	});
	
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
			var numbers = response.numbers;
			for(var index = 0; index < numbers.length; index++){
				$("#queuesTable tr:nth-child("+ (index + 2) +") td:nth-child(5)").text(numbers[index]);
			}
		},
		error : function(jqXHR, text, errorThrown) {
			//HandleAjaxError(jqXHR, text, errorThrown);
		}
	});
}

function getQueuesArray(){
	
	var data = [];
	$("#queuesTable tr td:nth-child(2)").each(function(){
		data.push($(this).text());
	});
	return data;
}