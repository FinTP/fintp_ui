var urlRoot =  $("#apiUrl").text();

$(function() {
	
	//open buttons
	
	$("button.edit").each(function(){
		$(this).button({ icons: { primary: "ui-icon-pencil" }, disabled: false });
		$(this).click(function(){
			var timelimitName = $(this).parent().parent().find("td").first().text();
			document.location.href = "./editTimelimit.htm?limitname="+ timelimitName;
		});
	});
	$("button.delete").each(function(){
		$(this).button({ icons: { primary: "ui-icon-trash" }, disabled : false });
		$(this).click(function(){
			var timelimitName = $(this).parent().parent().find("td").first().text();
			if(confirm("Are you sure you want to delete this time limit?")){
			document.location.href = "./timelimits/deleteTimelimit.htm?limitname="+ timelimitName;
			}
		});
	});
	
	$("#addTimelimit").click(function(){
		document.location.href = "./addTimelimit.htm";
	});
});