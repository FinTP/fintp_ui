var messageIds;

$(function(){
	
	$("#moveDialog").dialog({
		autoOpen: false,
		title: "Move messages",
		height: 300,
	    width: 350,
	    modal: true,
	    buttons: {
	    	"Move" : function(){
	    		console.log("move to queue");
	    		console.log(messageIds);
	    		routeMesseges("move", messageIds, $("#destinationQueue").val());
	    	},
			"Cancel" : function(){
				$(this).dialog('close');
			}
	    }
	});
	$(".moveButton").click(function(){
		selectRoutedMessages($(this));
		$("#moveDialog").dialog("open");
	});
	$(".rejectButton").click(function(){
		selectRoutedMessages($(this));
		routeMesseges("reject", messageIds);
	});
	$(".authorizeButton").click(function(){
		selectRoutedMessages($(this));
		routeMesseges("authorize", messageIds);
	});
});

function selectRoutedMessages(button) {
	messageIds = new Array();
	var $table = $(button).parent().find("table");
	$table.find("input[class='routeCheckbox']").each(function() {
		if ($(this).prop("checked")) {
			messageIds.push($(this).closest("tr").attr("id"));
		}
	});
}

function routeMesseges(action, messages, destination){
	console.log(action + " " + messages + " from source to " + destination );
	$.ajax({
		data:{
			action : action,
			messages: messages,
			source: $("#queueName").text(),
			destination : destination
		},
		method : 'POST',
		url : "../routeRequest",
		dataType : 'json',
		async : false,
		success: function(data, textStatus, xhr){
			console.log("SUCCESS STATUS " + xhr.status );
		},
		error: function (xhr, textStatus, error){
			switch (xhr.status) {
			case 403:
				alert("You don't have necessary authorities to perform this action.");
				break;
			case 406:
				var json = $.parseJSON(xhr.responseText);
				alert(json.message);
				break;
			default:
				break;
			}
			
		}

	});
}