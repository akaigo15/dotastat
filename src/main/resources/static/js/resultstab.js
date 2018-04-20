ResultsTab = {

    tableCount: 0,

    buildTable: function(data) {

        this.tableCount++;



        for(i = 0; i < data.length; i++) {
            data[i].playerHeroInfo.winRate = (data[i].playerHeroInfo.win / data[i].playerHeroInfo.games).toFixed(2);
            data[i].playerHeroInfo.winVsRate = (data[i].playerHeroInfo.against_win / data[i].playerHeroInfo.against_games).toFixed(2);
        }


        var table = document.createElement("table");
        table.id = "table_"+this.tableCount;


        var thead = document.createElement('thead');
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


        document.getElementById('resultsTableHolder').appendChild(table);

        $('#table_'+this.tableCount).DataTable({
            columns: [
            { data: "hero.name" },
            { data: "playerHeroInfo.games"},
            { data: 'playerHeroInfo.win'},
            { data: 'playerHeroInfo.winRate'},
            { data: 'playerHeroInfo.winVsRate'}
            ]
        });

        $('#table_'+this.tableCount).DataTable().rows.add(data).draw();



    }
}