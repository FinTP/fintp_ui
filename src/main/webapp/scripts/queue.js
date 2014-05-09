var refreshSeconds = 60;
var timeOut = refreshSeconds * 1000;


$(function() {
	$("#tabs").tabs();
	$(".accordion").accordion({
		heightStyle : "content",
		collapsible : true,
		active : false
	});
	$('.accordion input[type="checkbox"]').click(function(e) {
	    e.stopPropagation();
	});
	
	$('.notGroupable').each(function(){
		loadTable($(this), true);
	});
	
	$('.groupable').each(function(){
		loadTable($(this), false);
	});
	
	$("#itemsPerPage").change(function(){
		$(".fintpTable").each(function(){
			$(this).parent().find("input[name='page']").val(1);
			loadTable($(this), false);
		});
		$(".paginator").each(function(){
			loadPaginator($(this));
		});
	});
	completeBatchesTable(true);
	setInterval(function() {
			completeBatchesTable(true);
	}, timeOut);
	
	
	
	
});

function loadTable(table, isTotalRequested,  filterArguments){
	
	var $parentDiv = table.parent();
	var $totalSpan = $parentDiv.find("span[class='total']");
		
	
	//get table fields (to read from json)
	var fields = new Array();
	table.find("thead").find("th").each(function(){
		var field = $(this).find("input[name='field']").val();
		if(field != null){
			fields.push(field);
		}
		
	});
	
	//empty the table in order to append to it
	table.find("tbody").empty();
	
	//get pagination options
	var pageSize =  $("#itemsPerPage").val();
	var page = table.parent().find("input[name='page']").val();
	
	//get sort values
	var sortField = table.parent().find("input[name='sortField']").val();
	var sortOrder = table.parent().find("input[name='sortOrder']").val();
	
	// if the table is grouped, find out the field names and 
	//	values in order to filter properly
	var groupFieldsNames = new Array();
	var groupFieldsValues = new Array();
	
	var $listKeys = table.parent().find("ul[class='groupFields']");
	var $listValues = table.parent().find("ul[class='groupValues']");
	$listKeys.find("li").each(function(){
		groupFieldsNames.push($(this).text());
	});
	
	$listValues.find("li").each(function(){
		groupFieldsValues .push($(this).text());
	});
	
	
	$.ajax({
		
		data: {
			queue: $("#queueName").text(), 
			type:table.find("input[name='messageType']").val(),
			page : page,
			pageSize : pageSize,
			sortField : sortField,
			sortOrder : sortOrder,
			isTotalRequested: isTotalRequested,
			groupFieldsNames : groupFieldsNames,
			groupFieldsValues : groupFieldsValues},
		method : 'POST',
		url : "../messagesDynamic",
		dataType : 'json',
		async : false,
		success : function(data) {
			for(var index = 0; index < data.messages.length; index++){
				var message = data.messages[index];
				var $tr = $("<tr>");
				$tr.attr("id" , message.guid);
				$tr.append($("<td>")
						.append($("<input>").attr("type","checkbox").addClass("routeCheckbox"))
						.append($("<button>").addClass("viewPayload"))
					);
	
				jQuery.each(fields, function(i, fieldName){
					$tr.append($("<td>").append(message[fieldName]));
				});
				table.find("tbody").append($tr);
				if(isTotalRequested){
					$totalSpan.text(data.total);
					$parentDiv.find("input[name='total']").first().val(data.total);
				}
			}
			$("button.viewPayload").each(function(){
				$(this).button({ icons: { primary: "ui-icon-folder-open" } });
				var messageId = $(this).closest("tr").attr("id");
				var $parentTable = $(this).closest("table");
				var messageType = $parentTable.find("input[name='messageType']").val();
				var functionCall = "viewPayload('"+ messageId + "', '" + messageType + "');";
				$(this).attr("onclick", functionCall);
				
			});
			
		},
		error : function(jqXHR, text, errorThrown) {
			//HandleAjaxError(jqXHR, text, errorThrown);
		}
	});
	
}

function viewPayload(id, type) {
	document.location.href = '/fintp_ui/viewPayload.htm?id=' + id + '&type='
			+ type;
}



