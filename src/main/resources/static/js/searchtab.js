SearchTab = {

    startup: function() {
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
            var text = $( "#steam32Id1" ).val();
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
            var text = $( "#steam32Id2" ).val();
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
            var text = $( "#steam32Id3" ).val();
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
            var text = $( "#steam32Id4" ).val();
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
        var name = $('#name'+number).val();

        if(name.trim().length==0) {
            name = null;
            return name;
        }

        return name.trim();
    },

    getSteamId: function(number) {
        var id = $('#steam32Id'+number).val();
        if(id.length == 0) {
            id = null;
            return id;
        }

        return id.trim();
    },

    getMinGames: function() {
        var mingames = $('#gamesplayed').val();

        if(mingames % 1 != 0) {
            mingames = mingames - (mingames % 1);
        }

        return mingames;
    },

    getMinWinrate: function() {
        var winrate = $('#winrate').val();

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
                positions.push(i+1);
            }
        }

        if(positions.length == 0) {
            positions = null
        }

        return positions;
    },

    getPatch: function() {
        var patch = $('#patch').val();

        return patch;
    },


    onSubmit: function() {

        var patch = this.getPatch();
        var minwinrate = this.getMinWinrate();
        var mingames = this.getMinGames();
        var poslist = this.getPositions();

        for(i = 1; i < 6; i++) {
            var toSend = {steam32Id: this.getSteamId(i),patch:patch,heroType:poslist,minimumGamesPlayed:mingames,minimumWinRate:minwinrate};
            console.log(toSend)
        }

    }
}