
// function to get URL Parameter copied from stackoverflow
var getUrlParameter = function getUrlParameter(sParam) {
        var sPageURL = decodeURIComponent(window.location.search.substring(1)),
            sURLVariables = sPageURL.split('&'),
            sParameterName,
            i;

        for (i = 0; i < sURLVariables.length; i++) {
            sParameterName = sURLVariables[i].split('=');

            if (sParameterName[0] === sParam) {
                return sParameterName[1] === undefined ? true : sParameterName[1];
            }
        }
    };

$(document).ready(function(){

    console.log("game.js loaded");

    var rows = ["","A","B","C","D","E","F","G","H","I","J"];
    var cols = ["",1,2,3,4,5,6,7,8,9,10];

    var gameData;
    var gamePlayerId = getUrlParameter('gp');
    //getting the Json depending on gameplayerid
    $.getJSON("/api/game_view/" + gamePlayerId,function(data){

        gameData = data;
        console.log(gameData);
        buildOwnBoard();
        buildTargetBoard();
    })

    function buildOwnBoard(){

        $.each(rows,function(i, row){

            var tr = $("<tr>");

            $.each(cols,function(j, col){

            var coords = row + col
            if(i == 0 || j == 0){
            var cell= $("<th>");
            cell.html(coords);
            }
            else {
            var cell = $("<td>");

            cell.attr("class", getType(coords));
            cell.attr("id", coords);
            }

            tr.append(cell);

            })
            $("#myBoard").append(tr);
        })
    }

    function buildTargetBoard(){
        $.each(rows,function(i, row){

                    var tr = $("<tr>");

                    $.each(cols,function(j, col){
                    var coords = row + col;

                    if(i == 0 || j == 0){
                    var cell= $("<th>");
                    cell.html(coords);
                    }
                    else {
                    var cell = $("<td>");

                    cell.attr("class", isSalvo(coords));
                    cell.attr("id", coords);
                    }

                    tr.append(cell);

                    })
                    $("#targetBoard").append(tr);
                })
    }

    function getType(coordinates){
       //is it water?
       type = "water";
       //is it ship?
       $.each(gameData.ships, function(i, ship){
           if($.inArray(coordinates, ship.locations) !== -1){
               type = "ship";
           }
       })
       //is it hit? -> if ye, than add "salvo" to the type
        $.each(gameData.salvoes, function(gpID, salvoes){
            if (gpID != gamePlayerId){
                $.each(salvoes, function(i,salvo) {
                    console.log(salvo.salvolocations);
                    if($.inArray(coordinates, salvo.salvolocations) !== -1){
                        type = type + " salvo";
                    }
                });
            }
        })


       return type;
    }

    function isSalvo(coordinates) {
        type = "water";
        console.log("checking for salvos");
        console.log(coordinates);

        $.each(gameData.salvoes[gamePlayerId], function(i,salvo) {
            console.log(salvo.salvolocations);
            if($.inArray(coordinates, salvo.salvolocations) !== -1){
                           type = type + "salvo"
                       }

        });
        return type
    }

});