var frontlinesms = this.frontlinesms || {};

frontlinesms.showCaseOnLoad = function() {

    modified = false;
    var caseDescription = $("#case-description").val();
    var caseID = $("#case-id").val();

    $('input').change(function() {
        modified = true;
        $('#case-update').removeAttr('disabled');
    });

    $('textarea').change(function() {
        modified = true;
        $('#case-update').removeAttr('disabled');
    });

    frontlinesms.enableUpdateButtonOnDetailsChange("#case-description", "#case-update");
    frontlinesms.enableUpdateButtonOnDetailsChange("#case-id", "#case-update");

    $('tr.contactLink, td.unlink-contact-button').click(function() {
        modified = true;
        $('#case-update').removeAttr("disabled");
    });

    $("#case-update-cancel").click(function() {
        frontlinesms.caseUpdateCancelConfirmAction(modified);
        return false;
    });
};

frontlinesms.caseUpdateCancelConfirmAction = function(setter) {

    if (!setter) {
        frontlinesms.goHome();
    }
    else {

        $("#case-update-cancel-dialog").dialog({
            modal: true,
            buttons: [
                {
                    text: "Yes",
                    click: function() {
                        $(window.location).attr("href", "/");
                    },
                    id: "cancel-confirm-yes"
                },
                {
                    text: "No",
                    click: function() {
                        $(this).dialog("close");
                    },
                    id: "cancel-confirm-no"
                }
            ]
        });
    }
};

frontlinesms.goHome = function() {
    $(window.location).attr("href", "/");
};