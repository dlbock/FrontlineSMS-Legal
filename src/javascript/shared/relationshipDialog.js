frontlinesms.relationshipDialog = function (Id, type) {

    $("#case-contact-relationship-dialog").dialog({
        open: function(event, ui) {
            $("#case-contact-relationship").val("");
        },
        modal: true,
        buttons: [
            {
                text: "OK",
                click: function() {
                    var relationship = $("#case-contact-relationship").val();
                    if (type == "Case") {
                        frontlinesms.addLinkedCaseToHiddenField(Id, relationship);
                        frontlinesms.addLinkedCaseToTable(Id, relationship);
                        $('#contact-save').removeAttr("disabled");
                        $(this).dialog("close");
                        $("#link-case-dialog").dialog("close");
                    }
                    else {
                        frontlinesms.addLinkedContactToCaseHiddenField(Id, relationship);
                        frontlinesms.addLinkedContactToCaseTable(Id, relationship);
                        $('#case-save').removeAttr("disabled");
                        $('#case-update').removeAttr("disabled");
                        $(this).dialog("close");
                        $("#link-contact-dialog").dialog("close");
                    }
                    return true
                },
                id: "confirm-relationship"
            },
            {
                text: "Cancel",
                click: function() {
                    $(this).dialog("close");
                    return false
                },
                id: "cancel-relationship"
            }
        ]
    });
};
