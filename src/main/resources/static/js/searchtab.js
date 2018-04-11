SearchTab = {

    startup: function() {
        //define callback
        this.bindSearchPress();

        //initially disable other steamid inputs

        var x = document.getElementsByClassName("t1");

        for(i = 0; i < x.length; i++) {
            x[i].disabled = true;
        }

        x = document.getElementsByClassName("t2")

        for(i = 0; i < x.length; i++) {
            x[i].disabled = true;
        }

        x = document.getElementsByClassName("t3")

        for(i = 0; i < x.length; i++) {
            x[i].disabled = true;
        }

        x = document.getElementsByClassName("t4")

        for(i = 0; i < x.length; i++) {
            x[i].disabled = true;
        }
        
    },

    bindSearchPress: function() {
         $('#submit').click(function() {
         SearchTab.onSubmit();
         });
    },

    getName: function(number) {
        var name = document.getElementById("name"+number).value;

        if(name.trim().length==0) {
            name = null;
            return name;
        }

        return name.trim();
    },

    getSteamId: function(number) {
        var id = document.getElementById("steam32Id"+number).value;

        if(id.trim().length==0) {
            id = null;
            return id;
        }

        return id.trim();
    },

    getMinGames: function() {
        var mingames = document.getElementById("gamesplayed").value.trim();

        if(mingames % 1 != 0) {
            mingames = mingames - (mingames % 1);
        }

        return mingames;
    },

    getMinWinrate: function() {
        var winrate = document.getElementById("winrate").value.trim();

        if(winrate < 0) {
            winrate = 0;
        }

        return winrate;
    },

    getPositions: function() {
        var positions = new Array();

        var x = document.getElementsByClassName("possearch");

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
        var patch = document.getElementById("patch").value;

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