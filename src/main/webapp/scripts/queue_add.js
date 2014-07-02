$(function() {
	
	$("form").submit(function(){

		String queuename = $("input[name='name']").val();
		alert(queuename);
		$("input[name='name']").val(queuename.replaceAll("\\s+",""));
	});
	
});