$(function() {

	$(".submitButton").click(function() {
		var queuename = $("#name").val();
		$("#name").val(queuename.replace(/ /g,''));
	});

});