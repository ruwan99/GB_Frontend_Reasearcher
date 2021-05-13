$(document).ready(function() 
{  
	if ($("#alertSuccess").text().trim() == "")  
	{   
		$("#alertSuccess").hide();  
	} 
	$("#alertError").hide(); 
}); 

//SAVE ============================================ 
$(document).on("click", "#btnSave", function(event) 
{  
	// Clear alerts---------------------  
	$("#alertSuccess").text("");  
	$("#alertSuccess").hide();  
	$("#alertError").text("");  
	$("#alertError").hide(); 

	// Form validation-------------------  
	var status = validateReasearcherForm();  
	if (status != true)  
	{   
		$("#alertError").text(status);   
		$("#alertError").show();   
		return;  
	} 

	// If valid------------------------  
	var t = ($("#hidReasearcherIDSave").val() == "") ? "POST" : "PUT";
	
	$.ajax(
	{
		url : "ReasearcherApi",
		type : t,
		data : $("#formReasearcher").serialize(),
		dataType : "text",
		complete : function(response, status)
		{
			onReasearcherSaveComplete(response.responseText, status);
		}
	});
}); 

function onReasearcherSaveComplete(response, status){
	if(status == "success")
	{
		var resultSet = JSON.parse(response);
			
		if(resultSet.status.trim() == "success")
		{
			$("#alertSuccess").text("Successfully Saved.");
			$("#alertSuccess").show();
					
			$("#divItemsGrid").html(resultSet.data);
	
		}else if(resultSet.status.trim() == "error"){
			$("#alertError").text(resultSet.data);
			$("#alertError").show();
		}
	}else if(status == "error"){
		$("#alertError").text("Error While Saving.");
		$("#slertError").show();
	}else{
		$("#alertError").text("Unknown Error while Saving.");
		$("#alertError").show();
	}
	$("#hidReasearcherIDSave").val("");
	$("#formReasearcher")[0].reset();
}

//UPDATE========================================== 
$(document).on("click", ".btnUpdate", function(event) 
		{     
	$("#hidReasearcherIDSave").val($(this).closest("tr").find('#hidReasearcherUpdate').val());     
	$("#ReasearcherName").val($(this).closest("tr").find('td:eq(0)').text());    
	$("#ReasearcherEmail").val($(this).closest("tr").find('td:eq(1)').text());     
	$("#ReasearcherType").val($(this).closest("tr").find('td:eq(2)').text());     
	$("#ReasearcherContact").val($(this).closest("tr").find('td:eq(3)').text()); 
	

});


//Remove Operation
$(document).on("click", ".btnRemove", function(event){
	$.ajax(
	{
		url : "ReasearcherApi",
		type : "DELETE",
		data : "ReasearcherID=" + $(this).data("reasearcherid"),
		dataType : "text",
		complete : function(response, status)
		{
			onReasearcherDeletedComplete(response.responseText, status);
		}
	});
});

function onReasearcherDeletedComplete(response, status)
{
	if(status == "success")
	{
		var resultSet = JSON.parse(response);
			
		if(resultSet.status.trim() == "success")
		{
			$("#alertSuccess").text("Successfully Deleted.");
			$("#alertSuccess").show();
					
			$("#divItemsGrid").html(resultSet.data);
	
		}else if(resultSet.status.trim() == "error"){
			$("#alertError").text(resultSet.data);
			$("#alertError").show();
		}
	}else if(status == "error"){
		$("#alertError").text("Error While Deleting.");
		$("#alertError").show();
	}else{
		$("#alertError").text("Unknown Error While Deleting.");
		$("#alertError").show();
	}
}

//CLIENTMODEL
function validateReasearcherForm() {  
	// NAME  
	if ($("#ReasearcherName").val().trim() == "")  {   
		return "Insert ReasearcherName.";  
		
	} 
	
	 // Email 
	if ($("#ReasearcherEmail").val().trim() == "")  {   
		return "Insert ReasearcherEmail.";  
	} 
	
	
	//Type
	if ($("#ReasearcherType").val().trim() == "")  {   
		return "Insert ReasearcherType."; 
		 
	}
	 
	 // is numerical value  
	var tmpMobile = $("#ReasearcherContact").val().trim();  
	if (!$.isNumeric(tmpMobile))  {   
		return "Insert a numerical value for Mobile Number.";  
		
	}
	 
	
		

	 
	 return true; 
	 
}
