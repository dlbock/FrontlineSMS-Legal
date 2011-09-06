var frontlinesms = this.frontlinesms || {};

frontlinesms.attachActionWithConfirmationToButton = function(buttonSelector, dialogSelector, action) {

    $(buttonSelector).click(function() {
        frontlinesms.confirmAction(dialogSelector, action, this);
        return false;
    });
}

frontlinesms.yesNoDialogBox = function() {
    if (!$("#update-event").attr("disabled")) {
        $("#confirmation-dialog").dialog("destroy").empty();
        $("#schedule").append('<div class="confirmation-dialog" id="confirmation-dialog" title="Cancel edit event?" style="display: none;"><p>Are you sure you want to leave this page without saving? Your changes will not be saved.</p></div>');
        $("#confirmation-dialog").dialog({
            modal: true,
            buttons: [
                {
                    text: "Yes",
                    click: function() {
                        $(".confirmation-dialog").remove();
                        return true
                    },
                    id: "confirm-yes"
                },
                {
                    text: "No",
                    click: function() {
                        $(".confirmation-dialog").remove();
                        $("#view-event").dialog("open");
                        $('#update-event').attr('disabled', false);
                        return true
                    },
                    id: "confirm-no"
                }
            ]
        });
    }
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