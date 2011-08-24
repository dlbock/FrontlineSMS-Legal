var frontlinesms = this.frontlinesms || {};

frontlinesms.eventDetails = function() {
    $("#view-event").hide();
    $("#view-event").dialog({
                autoOpen: false,
                modal: true
        
            });
};

frontlinesms.attachActionWithLinkContactButton = function(buttonSelector, dialogSelector) {

    $(buttonSelector).click(function() {
        frontlinesms.displayPopup(dialogSelector);
        return false;
    });
};

frontlinesms.displayPopup = function(dialogSelector) {

    $(dialogSelector).dialog({

    });
};


