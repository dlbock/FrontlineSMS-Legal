var frontlinesms = this.frontlinesms || {};

frontlinesms.initializeUnlinkContactFromEvent = function() {
/*
    var deleteCaseAction = function(button) {
        $(button).parent("form").submit();
    }*/



    frontlinesms.attachActionWithConfirmationToButton(".unlink-contact-link", "#contactUnlinkDialog", function(){ alert("Finally reached!!");})
}
