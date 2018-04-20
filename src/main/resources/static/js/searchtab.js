SearchTab = {

    baseURL: '',

    startup: function(generatedURL) {

        this.baseURL = generatedURL;

        //define callback
        this.bindSearchPress();
        this.bindSteamIds();

        //initially disable other steamid inputs

        $('.t1').each(function (i) {
            this.disabled = true;
        });

        $('.t2').each(function (i) {
            this.disabled = true;
        });

        $('.t3').each(function (i) {
            this.disabled = true;
        });

        $('.t4').each(function (i) {
            this.disabled = true;
        });


    },

    bindSearchPress: function() {
         $('#submit').click(function() {
         SearchTab.onSubmit();
         });
    },

    bindSteamIds: function() {
       $( '#steam32Id1' ).change( function( event, ui ) {
            var text = $( "#steam32Id1" ).val().trim();
            if(text.length > 0) {
                $('.t1').each(function (i) {
                    this.disabled = false;
                });
            }
            else{
                $('.t1').each(function (i) {
                    this.disabled = true;
                });
            }
          });

       $( '#steam32Id2' ).change( function( event, ui ) {
            var text = $( "#steam32Id2" ).val().trim();
            if(text.length > 0) {
                $('.t2').each(function (i) {
                    this.disabled = false;
                });
            }
            else{
                $('.t2').each(function (i) {
                    this.disabled = true;
                });
            }
          });

       $( '#steam32Id3' ).change( function( event, ui ) {
            var text = $( "#steam32Id3" ).val().trim();
            if(text.length > 0) {
                $('.t3').each(function (i) {
                    this.disabled = false;
                });
            }
            else{
                $('.t3').each(function (i) {
                    this.disabled = true;
                });
            }
          });

       $( '#steam32Id4' ).change( function( event, ui ) {
            var text = $( "#steam32Id4" ).val().trim();
            if(text.length > 0) {
                $('.t4').each(function (i) {
                    this.disabled = false;
                });
            }
            else{
                $('.t4').each(function (i) {
                    this.disabled = true;
                });
            }
          });

    },



    getName: function(number) {
        var name = $('#name'+number).val().trim();

        if(name.trim().length==0) {
            name = null;
            return name;
        }

        return name.trim();
    },

    getSteamId: function(number) {
        var id = $('#steam32Id'+number).val().trim();
        if(id.length == 0) {
            id = null;
        } else {
            id = Number(id);
        }

        return id;
    },

    getMinGames: function() {
        var mingames = Number($('#gamesplayed').val().trim());

        if(mingames % 1 != 0) {
            mingames = mingames - (mingames % 1);
        }

        if(mingames == '') {
            mingames = 0
        }

        return mingames;
    },

    getMinWinrate: function() {
        var winrate = Number($('#winrate').val().trim());

        if(winrate < 0) {
            winrate = 0;
        }

        return winrate;
    },

    getPositions: function() {
        var positions = new Array();

        var x = $('.possearch');

        for(i = 0; i < x.length; i++) {

            if(x[i].checked == true) {
                positions.push(x.getAttribute('data-enum'));
            }
        }


        if(positions.length == 0) {
            positions = []
        }

        return positions;
    },

    getPatch: function() {
        var patch = $('#patch').val().trim();

        if(patch == "null") {
            patch = null;
        } else {
            patch = Number(patch);
        }

        return patch;
    },


    onSubmit: function() {

        var patch = this.getPatch();
        var minwinrate = this.getMinWinrate();
        var mingames = this.getMinGames();
        var poslist = this.getPositions();


        for(i = 1; i < 6; i++) {

            if(this.getSteamId(i) != null ) {
                var id = $('#steam32Id'+i).val().trim();
                var name = $('#name'+i).val().trim();

                console.log("steam32Id"+i+" passed");
                var toSend = {"steam32Id": this.getSteamId(i),"patch":patch,"heroType":poslist,"minimumGamesPlayed":mingames,"minimumWinRate":minwinrate};
                toSend = JSON.stringify(toSend);

                console.log(toSend);
                urltest = this.baseURL + "/dotastat/playerstat"
                console.log(urltest);
                $.ajax({
                  type: "post",
                  url: urltest,

                  data: toSend,
                  contentType: "application/json",
                  success: function(data, status, jqXHR) {
                    console.log("name: " + name + " id: " + id + " data: " + JSON.stringify(data));
                    ResultsTab.buildTable(data);
                  },
                  error: function(jqXHR, textStatus, errorThrown) {
                    console.log("text status: " + textStatus);
                    console.log("errorThrown: " + errorThrown);
                    console.log(jqXHR);
                  }

                });
            }
        }

        $("#tabs").tabs({
            active: 1
        });

    }
}