$(function() {

	console.log("script loaded");
	console.log($("#actionsNoParamList"));
	var actionsNoParamList = new Array();
	$("#actionsNoParamList").find("li").each(function(){
		actionsNoParamList.push($(this).text());
		console.log($(this).text());
	});
	console.log("NO args " + $("#actionsNoParamList"));
	 
	var actionsParamList = new Array();
	$("#actionsParamList").find("li").each(function(){
		actionsParamList.push($(this).text());
	});
	var actionsDropDownList = new Array();
	$("#actionsDropDownList").find("li").each(function(){
		actionsDropDownList.push($(this).text());
	});
	
	$("#actionSelect").change(function() {
		console.log("action changed!");
		console.log("No arg : " +actionsNoParamList);
		console.log("Text arg: " +actionsParamList);
		console.log("List arg: " + actionsDropDownList);
		console.log("selected " + $(this).val());
		
		$("#actionParameter").val("");
		$("#actionParameter").hide();
		$("#actionArgSelect").hide();
		
		if (actionsParamList.indexOf($(this).val(),0) >= 0) {
			$("#actionParameter").show();
		}
		if (actionsDropDownList.indexOf($(this).val(),0) >= 0) {
			$("#actionArgSelect").show();
		}
		
		
	});
	
	$("form").submit(function(){
		var actionString = $("#actionSelect").val();
		var actionTextParam = ""; 
		if($("#actionParameter").is(":visible")){
			actionTextParam =$("#actionParameter").val();
		}
		if($("#actionArgSelect").is(":visible")){
			actionTextParam = $("#actionArgSelect").val();
		}
		
		if(actionTextParam != ""){
			actionString += "(" + actionTextParam + ")";
		}
		$("#action").val(actionString);
	});
});
