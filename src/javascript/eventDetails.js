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
        $(dialogSelector).dialog("open");
        return false;
    });

    $(dialogSelector).dialog({
        autoOpen: false,
        modal: true,
        buttons: [{
            text: "Cancel",
            click: function() {
                $(this).dialog("close");
            },
            id: "cancel-button"
        }]
    });
};


