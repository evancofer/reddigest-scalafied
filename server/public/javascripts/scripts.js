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