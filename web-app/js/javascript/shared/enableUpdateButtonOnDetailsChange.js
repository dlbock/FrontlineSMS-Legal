var frontlinesms = this.frontlinesms || {};

frontlinesms.enableUpdateButtonOnDetailsChange = function (detailTextBoxId, updateButtonId){
    var content = $(detailTextBoxId).val();
    $(detailTextBoxId).keyup(function(){

        if ($(detailTextBoxId).val() != content){
            $(updateButtonId).removeAttr("disabled");
        }
    });

};

