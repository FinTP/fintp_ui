var refreshSeconds = 30;
var timeOut = refreshSeconds * 1000;

$(function(){
	completeBatchesTable(false);
	setInterval(function() {
		completeBatchesTable(false);
	}, timeOut);
});


function completeBatchesTable(onlyBarInfo){
		
		$.ajax({
			data:{
				userFilter: onlyBarInfo
			},
			//todo : investigate if this shold be true or false
			async: false,
			url : "http://localhost:8080/fintp_ui/batchRequest",
			method : 'GET',
			success: function(data, textStatus, xhr){
				var countTotal = 0;
				var countSuccess = 0;
				var countFailed = 0;
				var countInProgress = 0;
				if(!onlyBarInfo){
					$("#batches").find("tbody").empty();
				}
				$.each(data.groupkeys, function(index, value){
					$.ajax({
						async: false,
						url : "http://localhost:8080/fintp_ui/batchRequest",
						method : 'GET',
						data : {
							groupkey: value
						},
						success: function(response, textStatus, xhr){
							countTotal+= response.nb_batches;
							if(response.progress == 0){
								return;
							}else{
								$.each(response.batches, function(index, batch){
									if(batch.status == "SUCCESS"){
										countSuccess++;
									}else if(batch.status == "FAILED"){
										countFailed++;
									}else{
										countInProgress++;
									}
									var $tr = $("<tr>");
									$tr.append($("<td>").append(batch.id));
									$tr.append($("<td>").append(batch.status));
									$progressBar = $("<div>").progressbar({
									      value: batch.progress 
								    });
									$tr.append($("<td>").append($progressBar));
									if(!onlyBarInfo){
										$("#batches").find("tbody").append($tr);
									}
								});
							}
						}
					});
				});
				var countWaiting = countTotal - (countSuccess + countFailed + countInProgress);
				$("#nbBatches").text(countTotal);
				$("#nbSuccess").text(countSuccess);
				$("#nbFailed").text(countFailed);
				$("#nbInProgress").text(countInProgress);
				$("#nbWaiting").text(countWaiting);
			},
			error: function (xhr, textStatus, error){
				switch (xhr.status) {
				case 403:
					alert("You don't have necessary authorities to perform this action.");
					break;
				case 406:
					var json = $.parseJSON(xhr.responseText);
					alert(json.message);
					break;
				default:
					break;
				}
			}
		});
}