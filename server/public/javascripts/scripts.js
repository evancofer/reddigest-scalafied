
function validate(){

}

function js_inputButton(buttonname){
	console.log(buttonname);
	var button = $(buttonname);
	if ($("#username").val() && $('#password').val()){
		button.addClass("btn-success").removeClass("disabled");
	} else {
		button.addClass("disabled").removeClass("btn-success");
	}
}

function js_register(){
	$('#register-modal').modal();
	$('#register-modal').modal("show");
			
}