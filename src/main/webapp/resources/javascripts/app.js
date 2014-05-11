setInterval(generateData, 1000);
			
function generateData(){
	if (sessionStorage.generate == "true") { 
		$.ajax({
			url: "/console-ui/generate",
			type: "GET",
			dataType: "json",
			success: function() { console.log("data generated"); }
		});			
	}
}			

function toggleGenerate(){
	if (sessionStorage.generate == "true") {
		sessionStorage.generate = "false";
	} else {
		sessionStorage.generate = "true";
	}
}