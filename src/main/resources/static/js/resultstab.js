ResultsTab = {

    tableCount: 0,

    buildTable: function(data) {

        var name = data.name;
        var id = data.id;
        var dataArray = data.playerHeroStatsList



        this.tableCount++;



        for(i = 0; i < data.playerHeroStatsList.length; i++) {
            data.playerHeroStatsList[i].playerHeroInfo.winRate = (data.playerHeroStatsList[i].playerHeroInfo.win / data.playerHeroStatsList[i].playerHeroInfo.games).toFixed(2);
            data.playerHeroStatsList[i].playerHeroInfo.winVsRate = (data.playerHeroStatsList[i].playerHeroInfo.against_win / data.playerHeroStatsList[i].playerHeroInfo.against_games).toFixed(2);
        }


        var table = document.createElement("table");
        table.id = "table_"+this.tableCount;
        table.className = "resultlist  center-text";

        var div = document.createElement("div");

        var thead = document.createElement('thead');
        var h3 = document.createElement('h3');
        var tr = document.createElement('tr');

        var th1 = document.createElement('td');
        var th2 = document.createElement('td');
        var th3 = document.createElement('td');
        var th4 = document.createElement('td');
        var th5 = document.createElement('td');

        var text1 = document.createTextNode('Name');
        var text2 = document.createTextNode('Total Games');
        var text3 = document.createTextNode('Wins');
        var text4 = document.createTextNode('Win Rate');
        var text5 = document.createTextNode('Win Rate Against');

        th1.appendChild(text1);
        th2.appendChild(text2);
        th3.appendChild(text3);
        th4.appendChild(text4);
        th5.appendChild(text5);
        tr.appendChild(th1);
        tr.appendChild(th2);
        tr.appendChild(th3);
        tr.appendChild(th4);
        tr.appendChild(th5);

        thead.appendChild(tr);
        table.appendChild(thead);
        div.appendChild(table);


        var titleText = 'undefined';

        if(name.trim() != '') {
            titleText = document.createTextNode(name + " - " + id);
        } else {
            titleText = document.createTextNode(id);
        }


        h3.appendChild(titleText);
        div.prepend(h3);


        document.getElementById('resultsTableHolder').prepend(div);

        $('#table_'+this.tableCount).DataTable({
            data: dataArray,
            columns: [
                { data: "hero.name"},
                { data: "playerHeroInfo.games"},
                { data: 'playerHeroInfo.win'},
                { data: 'playerHeroInfo.winRate'},
                { data: 'playerHeroInfo.winVsRate'}
            ]
        });

        $('#table_'+this.tableCount).DataTable().rows.add(data).draw();



    }
}