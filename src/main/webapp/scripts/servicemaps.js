var urlRoot =  $("#apiUrl").text();

$(function() {
	
	//open buttons
	
	$("button.edit").each(function(){
		$(this).button({ icons: { primary: "ui-icon-pencil" }, disabled: false });
		$(this).click(function(){
			var conectorName = $(this).parent().parent().find("td").siblings().eq(1).first().text();
			document.location.href = "./editConnector.htm?connector="+ conectorName;
		});
	});
	
});