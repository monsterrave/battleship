$(document).ready(function() {



$("#loginForm").on("submit", function(event) {

  event.preventDefault();

  // Get the data from the form
  var formData = {};
  $(this).serializeArray().map(function(item) {
    formData[item.name] = item.value;
    return item
  });

  // Validate if all is there
  if(formData.username == ""){
    $("#errorMessage").text("Username is required.").show();
    return false;
  }
  if(formData.password == ""){
    $("#errorMessage").text("Password is required.").show();
    return false;
  }



  // When everyhing is there, post to API
  $.post("/api/login", formData).done(function(data) {
    window.location.href = "/web/games.html";
  }).fail(function(data) {
    if (data.status == 401) {
      $("#errorMessage").text("Userdata invalid.").show();
    } else {
      $("#errorMessage").text("Something went wrong...").show();
    }
  });
  return false;
});


});