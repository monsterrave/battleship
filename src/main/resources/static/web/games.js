$(function() {

  var createListElements = function(data) {
    //console.log(data);
    var list = data.map(function(game){

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

  // load and display JSON sent by server for /api/games


    $.ajax({
        url:"/api/games",
        success: createListElements
    });

    $.ajax({
        url:"/api/players",
        success: createLeaderBoard
    });
});