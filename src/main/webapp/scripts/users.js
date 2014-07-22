$(function() {
	$("#addUser").click(function(){
			document.location.href = "./addUser.htm";
		});

	$("button.edit").each(function(){
		$(this).button({ icons: { primary: "ui-icon-pencil" }});
		$(this).click(function(){
			var user = $(this).parent().parent().find("td").first().text();
			document.location.href = "./editUser.htm?user="+ user;
		});
	});
	
	$("button.delete").each(function(){
		$(this).button({ icons: { primary: "ui-icon-trash" }});
		$(this).click(function(){
			var user = $(this).parent().parent().find("td").first().text();
				if(confirm("Are you sure you want to delete user: '"+ user +"' ?")){
					if(confirm("Are you really sure? ")){
						document.location.href = "./users/deleteUser.htm?user="+ user;
					}
					
				}
		});
		
	});
});