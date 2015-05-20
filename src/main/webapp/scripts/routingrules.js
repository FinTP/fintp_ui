$(function() {
	$("button.edit").each(function(){
		$(this).button({ icons: { primary: "ui-icon-pencil" }});
		$(this).click(function(){
			var ruleId = $(this).parent().parent().attr("id");
			document.location.href = "./editRule.htm?rule="+ ruleId;
		});
	});
	$("button.delete").each(function(){
		$(this).button({ icons: { primary: "ui-icon-trash" }});
		$(this).click(function(){
			var ruleId = $(this).parent().parent().attr("id");
			document.location.href = "./routingrules/deleteRule.htm?rule="+ ruleId;
		});
	});
	$("#addRule").click(function(){
		var schemaName = $(this).attr("var");
		document.location.href = "./addRule.htm?schema="+schemaName;
	});
	
});