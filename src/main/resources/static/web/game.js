
// function to get URL Parameter, copied from stackoverflow
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

    var shipPlacements = [];
    var currentShip = {
            coordinates : [],
            type : ""
    };

    var gameData;
    var gamePlayerId = getUrlParameter('gp');
    //getting the Json depending on gameplayerid
    $.getJSON("/api/game_view/" + gamePlayerId,function(data){

        gameData = data;
        console.log(gameData);
        buildOwnBoard();

        if(gameData.ships.length == 5) {
            buildTargetBoard();
        } else {
            startPlacingShips();
        }

    })

    $("#placeShips").on("click", function() {
        console.log("should place Ships now...");

        $.ajax({
            type: "POST",
            url: "/api/games/players/" + gamePlayerId + "/ships",
            processData: false,
            contentType: 'application/json',
            data: JSON.stringify(shipPlacements),
            success: function(data) {
                console.log(data);
                window.location.reload();
            }
        });

        $("#selectSalvoes").show();
        $("#placeShips")

    })

    function startPlacingShips() {

        $("#ships").show();
        $("#ships .ship").on("click", function() {
            $(this).toggleClass("horizontal");
        })

        $("#ships .ship").draggable({
            revert: true,
            cursor: "move",
            opacity: 0.7,
            helper: "clone"
        });

        $("#myBoard td").droppable({
            over: function( event, ui) {
                console.log(ui);
                $(ui.helper).css({"opacity" : 0.8})
                //sets the shiptype to send it to the api endpoint (includes coordinates)
                currentShip.type=($(ui.helper).data("shiptype"));

                var ghostShipPostion = getGridPositionForElement($(ui.helper))
                console.log(ghostShipPostion)

                $("#ghostShip").css({
                    top : ghostShipPostion.x,
                    left : ghostShipPostion.y,
                    width : $(ui.helper).css("width"),
                    height: $(ui.helper).css("height")
                }).toggleClass("horizontal", $(ui.helper).hasClass("horizontal")).show()


                // IS THIS POSITION VALID?

                // Get Start Cell
                var startCell = false
                $("table td").each(function(){
                    var tdPosition = getGridPositionForElement($(this))
                    if (tdPosition.x == ghostShipPostion.x && tdPosition.y == ghostShipPostion.y){
                        startCell = $(this);
                    }
                })

                // Calculate all affected cells
                var startCellId = $(startCell).attr("id")
                if (startCellId) {
                var startRowIndex = rows.indexOf(startCellId[0])
                var startColumn = $(startCell).attr("id")[1]

                //if startcellid is defined...
                if($(ui.helper).hasClass("horizontal")){
                    var shipWidth = $(ui.helper).height() / 50
                    var shipHeight = $(ui.helper).width() / 50
                } else {
                    var shipWidth = $(ui.helper).width() / 50
                    var shipHeight = $(ui.helper).height() / 50
                }
                var endColumn = parseInt(startColumn) + parseInt(shipWidth)

                currentShip.coordinates = []
                for (var i = 0; i < shipHeight; i++){
                    var cell = rows[startRowIndex + i]
                    for(var j = startColumn; j < endColumn; j++){
                        currentShip.coordinates.push(cell + j)
                    }
                }
                console.log(currentShip.coordinates) // shows now the ship placement
                }

                // check previous ship placements if there are any ships
                var validPlacement = true;
                $.each(shipPlacements, function(){
                    console.log("checking placement")
                    var placedShip = this;
                    console.log(placedShip) // on placed ship
                    $.each(placedShip.coordinates, function(){
                        var coordinate = this.toString()
                        if( currentShip.coordinates.indexOf(coordinate) !== -1) validPlacement = false;
                    });
                });

                console.log(currentShip.coordinates)


                if(validPlacement){
                    $("#ghostShip").addClass("valid").removeClass("invalid")
                } else {
                    $("#ghostShip").addClass("invalid").removeClass("valid")
                }

            },
                drop: function( event, ui ) {

                    if($("#ghostShip").hasClass("valid")){
                        console.log("can place ship here..")

                        $(ui.draggable).appendTo("#boardContainer").css({
                            top : $("#ghostShip").css("top"),
                            left : $("#ghostShip").css("left"),
                            position: "absolute"
                        })

                        shipPlacements.push({
                            locations : currentShip.coordinates,
                            shiptype : currentShip.type
                        })
                        console.log(shipPlacements)

                        if(shipPlacements.length == 5) {
                            $("#placeShips").show();
                        }

                        } else {
                            console.log("cannot place ship here..")
                            $("#ghostShip").hide();
                        }
                    }
                });
    }

    function buildOwnBoard(){

        $.each(rows,function(i, row){
            var tr = $("<tr>");
            $.each(cols,function(j, col){
                var coords = row + col
                if(i == 0 || j == 0){
                    var cell= $("<th>");
                    cell.html(coords);
                } else {
                    var cell = $("<td>");
                    cell.attr("class", getType(coords));
                    cell.attr("id", coords);
                }
                tr.append(cell);
            })
            $("#myBoard").append(tr);
        })

    }

    function getGridPositionForElement(elem){
        var xOffset = elem.offset().top - $("#myBoard").offset().top
        var yOffset = elem.offset().left - $("#myBoard").offset().left

        var xRounded = Math.floor(xOffset / 50 ) * 50;
        var yRounded = Math.floor(yOffset / 50 ) * 50;

        return { x : xRounded, y : yRounded }
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