$(function() {
	//open buttons
	$("button.viewRules").each(function(){
		$(this).click(function(){
			var schema = $(this).parent().parent().find("td").first().text();
			document.location.href = "./routingrules.htm?schema="+ schema;
		});
	});
	$("button.edit").each(function(){
		$(this).button({ icons: { primary: "ui-icon-pencil" }});
		$(this).click(function(){
			var schemaName = $(this).parent().parent().find("td").first().text();
			document.location.href = "./editSchema.htm?schema="+ schemaName;
		});
	});
	$("button.delete").each(function(){
		$(this).button({ icons: { primary: "ui-icon-trash" }});
		$(this).click(function(){
			var schemaName = $(this).parent().parent().find("td").first().text();
			if(confirm("Are you sure you want to delete schema?")){
				if(confirm("Are you really sure? Deleting it, the rules in it will also be removed!!")){
					document.location.href = "./schemas/deleteSchema.htm?schema="+ schemaName;
				}
				
			}
			
		});
		
	});
	$("#addSchema").click(function(){
		document.location.href = "./addSchema.htm";
	});
});