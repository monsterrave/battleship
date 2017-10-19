$(document).ready(function() {

var player;



$("#loginForm").on("submit", function(event) {

  event.preventDefault();

  // Get the data from the form
  var formData = {};
  $(this).serializeArray().map(function(item) {
    formData[item.name] = item.value;
    return item
  });

  // Validate if all is there



  // When everyhing is there, post to API
  $.post("/api/login", formData).done(function(data) {

    window.location.reload()


  }).fail(function(data) {
    if (data.status == 401) {
      $("#errorMessage").text("Userdata invalid.").show();
    } else {
      $("#errorMessage").text("Something went wrong...").show();
    }
  });



  return false;
});

$("#logoutButton").on("click", function(){
    $.post("/api/logout",function(){
        window.location.href = "/web/login.html";
    })
});
//rejoin game by clicking on tr.own-game
$(document).on("click","tr.own-game", function() {
    var gpid =$(this).data("gameplayer-id");
                                                //this means this table row
    window.location.href = "/web/game.html?gp=" + $(this).data("gameplayer-id");
});

$(document).on("click", ".joinBtn", function(){

    var gameID = $(this).closest("tr").data("gameid");

    $.post("/api/games/" + gameID + "/players").done(function(data) {
        console.log(data);
        window.location.href = "/web/game.html?gp=" + $(this).data("gameplayer-id");
    }).fail(function() {
        console.log("fail");
    })


})

$("#newGameButton").on("click", function() {

    $.post("/api/games").done( function(data) {
        window.location.href = "/web/game.html?gp=" + data.id
    }).fail(function(){
        alert("Something went wrong")
    })

})


function getGames(){


    $.get("/api/games").done(function(data){
        console.log(data)

        player = data.player;
        //reverse () to have latest game on top
        var games = data.games.reverse();

        var gameRows = []

        $.each(games, function (i,game) {
            //build table rows
            var tr = $("<tr>").attr("data-gameID", game.id);

            var gameplayerID = false;

            var date = moment(game.date).fromNow();

            tr.append($("<td>").text(date))
            tr.append($("<td>").text(game.gameplayers[0].player.email))

            if (game.gameplayers[0].player.id == player.id) {
                gameplayerID = game.gameplayers[0].id;
            }

            //checks first, if there is any second player and puts if
            if (game.gameplayers[1]) {
              tr.append($("<td>").text(game.gameplayers[1].player.email ))

              if (game.gameplayers[1].player.id == player.id) {
                              gameplayerID = game.gameplayers[1].id;
                          }
            // else.... no player yet, so there is a button
            } else {
                var btn = $("<button>").text("join").addClass("btn btn-outline-danger btn-sm joinBtn")
                if(gameplayerID) btn = "";
              tr.append($("<td>").append(btn))
            }

            tr.append($("<td>").text("-"))

            if(gameplayerID) {
                tr.addClass("own-game").attr("data-gameplayer-id", gameplayerID)
            }



            gameRows.push(tr);
            //push table row into gameRows
        });
        $("#gamesTable tbody").html(gameRows)
        // add gameRows to the table


        console.log(data)
        var list = data.games.map(function(game) {
            var millis = game.date;
            var date = new Date(millis);
            var emails = game.gameplayers.map(function(gamePlayer) {
                return gamePlayer.player.email;
            })
            return $("<li>").text(date.toLocaleString() + " – ").append(emails.join(", "))
        });
        $("ol#games").html(list);

        $(".username").html(data.player.email);

        getPlayers();

    }).fail(function(data){
        window.location.href = "/web/login.html"
    })


}

function getPlayers() {
    $.ajax({
  url: "/api/players",
  success: function(data) {
    console.log(data)

    data = data.sort(function(a, b) {
      return b.totalScore - a.totalScore;
    });

    var tablebody = $('#players tbody');

    data.forEach(function(player, i) {

      var row = $('<tr>');
      $('<td>').text(player.username).appendTo(row);
      $('<td>').text(player.totalScore).appendTo(row);
      $('<td>').text(player.wins).appendTo(row);
      $('<td>').text(player.lost).appendTo(row);
      $('<td>').text(player.ties).appendTo(row);

      tablebody.append(row);

    });




  }

})
}


getGames();

});