
/* 
 * Not worth doing this in scalajs and having to worry
 * about some poorly documented or unimplemented 
 * feature, esp. when if we start using OAuth.
 */
function logout(){
	$.ajax({
		url: "/logout",
		method: "POST",
		dataType: "json",
		success:function(data){
			location.reload();
		}
	});
}

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