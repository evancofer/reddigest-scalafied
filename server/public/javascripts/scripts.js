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