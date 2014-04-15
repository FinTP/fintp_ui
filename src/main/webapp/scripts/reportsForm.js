$(function() {
	//$("#startDate").datepicker();
	//$("#endDate").datepicker();
	$("#valueDate").datepicker();
	$("input[name='interval']").change(function() {
		if (this.value == "interval") {
			selectInterval();
		} else {
			selectCurrentDate();
		}
	});
	$("form").submit(function(e){
		if($("input[name='interval']:checked").val() == "interval"){
			if(!(valid = validateInterval())){
				e.preventDefault();
			}
		}
		
	});
});

function selectCurrentDate(){
	$("#intervalPicker").find("input").each(function() {
		$(this).val("");
		$(this).text("");
		$(this).prop("disabled", true);
	});
	$("#intervalPicker").find("span").each(function() {
		$(this).addClass("disabled");
	});
}

function selectInterval(){
	$("#intervalPicker").find("input").each(function() {
		$(this).prop("disabled", false);
	});
	$("#intervalPicker").find("span").each(function() {
		$(this).removeClass("disabled");
	});
}

function validateInterval() {
	var startDate = $("#startDate").val();
	var endDate = $("#endDate").val();
	if (startDate == "" || endDate == "") {
		alert("Start date and end date must be selected");
		return false;
	}
	//TODO: verify that startDate < endDate
	
	return true;
}
