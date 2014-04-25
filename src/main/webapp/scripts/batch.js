$(function(){
	console.log("batch script loaded");
	$(".batch").click(function(){
		var $accordionElement = $(this).parent().find("div");
		$accordionElement.find("h3").each(function(){
			//find out group key and div correspondent
			var groupKey = $(this).attr("aria-controls");
			var $groupDiv = $("#"+groupKey);
			
			var $checkbox = $(this).find("input[type='checkbox']");
			var timekey = $(this).find("input[name='timekey']").val();
			var queue = $("#queueName").text();
			var fields = new Array();
			var $listValues = $groupDiv.find("ul[class='groupValues']");
			$listValues.find("li").each(function(){
				fields.push($(this).text());
			});
			var msgtype = $groupDiv.find("input[name='messageType']").val();
			
			console.log("=-=-=-=-=-=-=-=");
			console.log("groupkey " + $(this).attr("aria-controls"));
			console.log("timekey " + timekey);
			console.log("queuename " + queue);
			console.log($checkbox.prop("checked"));
			console.log("fields " + fields);
			console.log("msgtype " + msgtype);
			console.log("=-=-=-=-=-=-=-=");
			
			if($checkbox.prop("checked")){
				$.ajax({
					data:{
						queuename : queue,
						groupkey : groupKey,
						timekey : timekey,
						fields : fields,
						msgtype : msgtype
					},
					method : 'POST',
					url : "../batchRequest",
					dataType : 'json',
					async : false,
					success : function(data) {
						console.log(data);
					}
				});
			}
			
			
		});
	});
});