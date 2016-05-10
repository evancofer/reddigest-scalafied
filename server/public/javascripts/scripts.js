
function validate(){

}

function js_inputButton(buttonname){
	
	//console.log(document.getElementById("registerbutton").parent)
	//$('#registerbutton').parent().children().find('#username').val();
	
	
	//var registerButton = document.getElementById("registerButton");
	
	//var loginButton = document.getElementById("loginButton");
	
	
	//console.log(buttonname);
	//var button = $(buttonname).closest(".btn");

	
	if ( $('#registerbutton').parent().children().find('#username').val() && $('#registerbutton').parent().children().find('#password').val()){
		$('#registerbutton').addClass("btn-success").removeClass("disabled");
	} else {
		$('#registerbutton').addClass("disabled").removeClass("btn-success");
	}
	
	if ( $('#loginbutton').parent().children().find('#username').val() && $('#loginbutton').parent().children().find('#password').val()){
		$('#loginbutton').addClass("btn-success").removeClass("disabled");
	} else {
		$('#loginbutton').addClass("disabled").removeClass("btn-success");
	}
	
	
	
}

function js_register(){
	$('#register-modal').modal();
	$('#register-modal').modal("show");
			
}