$(function() {
	$("#startDate").datepicker();
	$("#endDate").datepicker();
	$("#valueDate").datepicker();
	$("#issuanceDate").datepicker();
	$("#maturityDate").datepicker();
	$("#startTime").timepicker();
	$("#endTime").timepicker();
	$("input[name='interval']").change(function() {
		if (this.value == "interval") {
			selectInterval();
		} else {
			selectCurrentDate();
		}
	});
	
	$("input:radio[name=businessArea]").change(function() {
		switch ( $('input[name=businessArea]:checked').val()) {
		case "Funds Transfer":
			$(".specificDI").hide();
	        $(".specificDD").hide();
	        $(".specificFT").show();
	        $(".specificFTDD").show();
	        break;
		case "Debit Instruments":
			$(".specificDI").show();
	        $(".specificDD").hide();
	        $(".specificFT").hide();
	        $(".specificFTDD").hide();
	    	break;
	    case "Direct Debit":
	    	//TODO: add properly these classes in the reportsForm.jspx (note one obj can have multiple classes
	    	$(".specificDI").hide();
	        $(".specificDD").show();
	        $(".specificFT").hide();
	        $(".specificFTDD").show();
	    	break;
	    }
	});
	
	$("form").submit(function(e){
		if($("input[name='interval']:checked").val() == "interval"){
			if(!(valid = validateInterval())){
				e.preventDefault();
			}
		}
		
	});
	
	$("#viewHeadersButton").click(function(e){
		$("#toggle").toggle( { direction: "down" });
		$(this).hide();
	});
	
	$("#closeHeadersButton").click(function(e){
		$("#toggle").toggle( { direction: "down" });
		$("#viewHeadersButton").show();
	});
});

function selectCurrentDate(){
	$("#intervalPickerStartDate").find("input").each(function() {
		$(this).val("");
		$(this).text("");
		$(this).prop("disabled", true);
	});
	$("#intervalPickerStartDate").find("span").each(function() {
		$(this).addClass("disabled");
	});
	$("#intervalPickerEndDate").find("input").each(function() {
		$(this).val("");
		$(this).text("");
		$(this).prop("disabled", true);
	});
	$("#intervalPickerEndDate").find("span").each(function() {
		$(this).addClass("disabled");
	});
	
}

function selectInterval(){
	$("#intervalPickerStartDate").find("input").each(function() {
		$(this).prop("disabled", false);
	});
	$("#intervalPickerStartDate").find("span").each(function() {
		$(this).removeClass("disabled");
	});
	$("#intervalPickerEndDate").find("input").each(function() {
		$(this).prop("disabled", false);
	});
	$("#intervalPickerEndDate").find("span").each(function() {
		$(this).removeClass("disabled");
	});
}

function validateInterval() {
	var startDate = $("#startDate").val();
	var endDate = $("#endDate").val();
	var startTime = $("#startTime").val();
	var endTime = $("#endTime").val();
	if (startDate == "" || endDate == "" || startTime == "" || endTime == "") {
		alert("Start date, start time and end date, end time must be selected");
		return false;
	}
	//TODO: verify that startDate < endDate
	
	return true;
}
