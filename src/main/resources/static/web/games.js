$(function() {

$.post("/app/login", { userName: "c.obrian@ctu.gov", password: "42" }).done(function() { console.log("logged in!");
    $.ajax({
            url:"/api/games",
            success: createListElements
        });

        $.ajax({
            url:"/api/players",
            success: createLeaderBoard
        });
 })

  var createListElements = function(data) {
    console.log(data);
    var list = data.games.map(function(game){

        var millis = game.date;
        var datum = new Date(millis);
        //map "loopt" über alle einträge im array/liste und ordnet den return value zu
        var emails = game.gameplayers.map(function(gamePlayer){
            return gamePlayer.player.email;
        })
        //console.log(emails);
        return $("<li>").text(datum.toLocaleString()).append(emails.join(", "));
    });

    $("ol#output").html(list);
  }

  var createLeaderBoard = function(data) {

    data.sort(function(a,b) {
    return b.totalScore - a.totalScore;
    })

    data.forEach(function(player,i) {
        var row = $('<tr>');
        $('<td>').text(player.username).appendTo(row);
        $('<td>').text(player.totalScore).appendTo(row);
        $('<td>').text(player.wins).appendTo(row);
        $('<td>').text(player.lost).appendTo(row);
        $('<td>').text(player.ties).appendTo(row);

        $("table#scores tbody").append(row);
    })
  }

  $("#login").click(function(){
  var email = $("#email").val();
  var password = $("#password").val();
  // Checking for blank fields.
  if( email =='' || password ==''){
  $('input[type="text"],input[type="password"]').css("border","2px solid red");
  $('input[type="text"],input[type="password"]').css("box-shadow","0 0 3px red");
  alert("Please fill all fields...!!!!!!");
  }else {
  $.post("login.php",{ email1: email, password1:password},
  function(data) {
  if(data=='Invalid Email.......') {
  $('input[type="text"]').css({"border":"2px solid red","box-shadow":"0 0 3px red"});
  $('input[type="password"]').css({"border":"2px solid #00F5FF","box-shadow":"0 0 5px #00F5FF"});
  alert(data);
  }else if(data=='Email or Password is wrong...!!!!'){
  $('input[type="text"],input[type="password"]').css({"border":"2px solid red","box-shadow":"0 0 3px red"});
  alert(data);
  } else if(data=='Successfully Logged in...'){
  $("form")[0].reset();
  $('input[type="text"],input[type="password"]').css({"border":"2px solid #00F5FF","box-shadow":"0 0 5px #00F5FF"});
  alert(data);
  } else{
  alert(data);
  }
  });
  }
  });



});