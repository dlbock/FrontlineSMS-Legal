var frontlinesms = this.frontlinesms || {};

frontlinesms.attachActionWithConfirmationToButton = function(buttonSelector, dialogSelector, action) {

    $(buttonSelector).click(function() {
        frontlinesms.confirmAction(dialogSelector, action, this);
        return false;
    });
}

frontlinesms.confirmAction = function(dialogSelector, action, triggeringElement) {

    $(dialogSelector).dialog({
        modal: true,
        buttons: [
            {
                text: "Yes",
                click: function() {
                    action(triggeringElement);
                    $(this).dialog("close");
                    return true
                },
                id: "confirm-yes"
            },
            {
                text: "No",
                click: function() {
                    $(this).dialog("close");
                    return false
                },
                id: "confirm-no"
            }
        ]
    });
};