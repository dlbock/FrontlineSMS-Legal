var frontlinesms = this.frontlinesms || {};

function validateContactNumberLength(contactNumberLength) {
    if (contactNumberLength >= 15) {
        $("#contact-primary-mobile").attr("disabled", "disabled");
        return false;
    }
    else {
        $("#contact-primary-mobile").removeAttr("disabled");
        return true;
    }
}

//function validateContactNumberCharacter(contactNumberCharacter) {
//    var
//    if(contactNumberCharacter)
//}

frontlinesms.validateContactNumber = function() {
//    $("#contact-primary-mobile").keypress() = function(){
//        var contactNumberLength= $("#contact-primary-mobile").val().length;
//        validateContactNumberLength(contactNumberLength);
//    }
};
