function fillBoardWithDataAndGetInfo() {
    $.ajax({
        type: 'GET',
        url: '/api/v1/jkalaha/info',
        dataType: "json",
        contentType: "application/json",
        success: function (data) {
            for (i = 0; i < data.cups.length; i++) {
                $("#" + i).text(data.cups[i]);
            }
            $("#takeTurnPlayer").text(data.takeTurnPlayer);
            if (data.winner !== null) {
                $("#winner").text(data.winner);
            }
            if (data.started && !data.over) {
                disableStartGameButton($("#startGame"))
            }

            if($("#user").val() === 'marcoS') {
                $('.row.player-one .pit').css({cursor:"pointer"});
                $('.row.player-two .pit').css({cursor:"default"});
            } else {
                $('.row.player-one .pit').css({cursor:"default"});
                $('.row.player-two .pit').css({cursor:"pointer"});
            }

            if (data.over) {
                alert("Game is over! Restart game if you want to play again!")
            }

        },
        error: function (data) {
            alert(data.error);
        }
    });
}
function restartGame() {
    $.ajax({
        type: 'POST',
        url: '/api/v1/jkalaha/restart',
        success: function (data) {
            for (i = 0; i < data.cups.length; i++) {
                $("#" + i).text(data.cups[i]);
            }
            $("#takeTurnPlayer").text(data.takeTurnPlayer);

            if (data.winner !== null) {
                $("#winner").text(data.winner);
            }
        },
        error: function (data) {
            alert(data.error);
        }
    });
}

$(document).ready(function(){
    $("#startGame").click(function () {

        var $winner = $("#winner");

        if ($winner.text() !== "-") {
            restartGame();
            $winner.text("-")
        } else {
            for (i = 0; i < 14; i++) {
                if (i !== 6 && i !== 13) {
                    $("#" + i).text(6);
                } else {
                    $("#" + i).text(0);
                }
            }
        }
         disableStartGameButton($(this));
    });

    $("#showHallOfFame").click(function () {
        $.ajax({
            type: 'GET',
            url: '/api/v1/jkalaha/hallOfFame',
            dataType: "json",
            contentType: "application/json",
            success: function (data) {
                var trHTML = '';
                $.each(data.hallOfFameList, function (i, item) {
                    trHTML += '<tr><td>' + item.winnerName + '</td><td>' + formatDate(item.dateOfVictory)+ '</td><td>';
                });
                $('#hallOfFameTableId').append(trHTML);
                $('#dialogHallOfFameId').show();
            },
            error: function (data) {
                alert(data.error);
            }
        });
    });

    $("#restartGame").click(function () {
        restartGame();
        enableStartGameButton($("#startGame"))
    });

    $(".pit").click(function () {
        var userName = $("#user").val();
        var activePlayer = $("#takeTurnPlayer");
        if (userName === activePlayer.text()) {
            choosenCup = $(this).attr('id');
            var userCupIndexes = getCupIndex(userName);
            if ($.inArray(choosenCup, userCupIndexes) !== -1) {
                var choosenCupInt = parseInt(choosenCup);
                $.ajax({
                    type: 'POST',
                    url: '/api/v1/jkalaha/makeTurn',
                    data: JSON.stringify({
                        'takeTurnPlayer': $("#takeTurnPlayer").text(),
                        'currentCup': choosenCupInt
                    }),
                    dataType: 'json',
                    contentType: 'application/json',
                    success: function (data) {
                        for (i = 0; i < data.cups.length; i++) {
                            $("#" + i).text(data.cups[i]);
                        }
                        $("#takeTurnPlayer").text(data.takeTurnPlayer);
                        if (data.winner !== null) {
                            $("#winner").text(data.winner);
                        }
                    },
                    error: function (data) {
                        alert("error : " + data.error);
                    }
                });
            } else {
                alert("You have chosen " + choosenCup + " house, which you don't own. Try to move again.");
            }
        } else {
            alert("Now is " + activePlayer.text() + "'s turn! Please wait for your turn.");
        }
    });
});

function disableStartGameButton(button) {
    button.prop("disabled", true);
    button.text("Game has been started!");
}

function enableStartGameButton(button) {
    button.prop("disabled", false);
    button.text("Start game!");
}

var choosenCup;
var user2Cups = {};
user2Cups["marcoS"] = ["0", "1", "2", "3", "4", "5"];
user2Cups["brandoN"] = ["7", "8", "9", "10", "11", "12"];

function getCupIndex(k) {
    return user2Cups[k];
}

function formatDate(date) {
    var d = new Date(date),
        month = '' + (d.getMonth() + 1),
        day = '' + d.getDate(),
        year = d.getFullYear();

    if (month.length < 2)
        month = '0' + month;
    if (day.length < 2)
        day = '0' + day;

    return [year, month, day].join('-');
}











