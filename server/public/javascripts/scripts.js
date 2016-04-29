function js_inputButton(button){
	console.log(button);
	if ($("#username").val() && $('#password').val()){
		button.className = "btn btn-block btn-success"
	} else {
		button.className = "btn btn-block disabled"
	}
}

function js_register(){
	$('#register-modal').modal();
	$('#register-modal').modal("show");
			
}