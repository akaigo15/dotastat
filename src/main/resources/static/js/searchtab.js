SearchTab = {

    startup: function() {
        //define callback
        this.bindSearchPress();
    },

    bindSearchPress: function() {
         $('#submit').click(function() {
         SearchTab.onSubmit();
         });
    },



    onSubmit: function() {
        console.log('Submit!');
    }
}