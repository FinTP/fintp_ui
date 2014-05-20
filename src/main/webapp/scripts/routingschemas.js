$(function() {
	//open buttons
	$("button.viewRules").each(function(){
		$(this).click(function(){
			var schema = $(this).parent().parent().find("td").first().text();
			document.location.href = "./routingrules.htm?schema="+ schema;
		});
	});
	$("button.edit").each(function(){
		$(this).button({ icons: { primary: "ui-icon-pencil" }, disabled: true });
	});
	$("button.delete").each(function(){
		$(this).button({ icons: { primary: "ui-icon-trash" }, disabled : true });
	});
});