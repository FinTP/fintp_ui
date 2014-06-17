$(function() {

	var actionsNoParamList = new Array();
	$("#actionsNoParamList").find("li").each(function(){
		actionsNoParamList.push($(this).text());
	});
	var actionsParamList = new Array();
	$("#actionsParamList").find("li").each(function(){
		actionsParamList.push($(this).text());
	});
	
	$("#actionSelect").change(function() {
		if (actionsNoParamList.indexOf(this.value,0) >= 0) {
			$("#actionParameter").val("");
			$("#actionParameter").hide();
		}
		if (actionsParamList.indexOf(this.value,0) >= 0) {
			$("#actionParameter").show();
		}
	});
	
	$("form").submit(function(){
		var actionString = $("#actionSelect").val();
		var actionParam = $("#actionParameter").val();
		if(actionParam != ""){
			actionString += "(" + $("#actionParameter").val() + ")";
		}
		$("#action").val(actionString);
	});
});
