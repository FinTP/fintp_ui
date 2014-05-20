$(function() {
	$("button.edit").each(function(){
		$(this).button({ icons: { primary: "ui-icon-pencil" }, disabled: true });
	});
	$("button.delete").each(function(){
		$(this).button({ icons: { primary: "ui-icon-trash" }, disabled : true });
	});
});