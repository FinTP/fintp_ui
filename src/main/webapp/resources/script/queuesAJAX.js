var urlRoot = "http://localhost:8080/fintpWebServices/api/";

$(function() {
	ajaxGetQueues();
});

function ajaxGetQueues(){
	var url = urlRoot + "queues";
	$.ajax({
		type : 'GET',
		url : url,
		dataType : 'json',
		async : false,
		beforeSend: function (xhr) {
			var string = btoa("admin:admin");
		    xhr.setRequestHeader ("Authorization", "Basic "+ string);
		},
		success : function(data) {
			console.log(data);
			total = data.total;
			populateQueuesTable(data.queues);
		},
		error : function(jqXHR, text, errorThrown) {
			console.log('eroare');
			HandleAjaxError(jqXHR, text, errorThrown);
		}
	});
}


function populateQueuesTable(queuesJSON){
	for(var i = 0; i < queuesJSON.length; i++){
		var queueAsJson = queuesJSON[i];
		var $tr = $("<tr>").attr("id", queueAsJson.name);
		$tr.append($("<td>").text(queueAsJson.name));
		$tr.append($("<td>").text(queueAsJson.description));
		$tr.append($("<td>").text(queueAsJson.holdstatus));
		$("#queuesTable").append($tr);
	}
}

