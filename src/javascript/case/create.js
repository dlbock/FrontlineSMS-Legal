var frontlinesms = this.frontlinesms || {};

frontlinesms.createNewCaseOnLoad = function() {


    $("#case-save").click(function() {
        frontlinesms.caseCreateWithoutTitleConfirmAction();
        return false;
    });

    $("#case-cancel").click(function() {
        frontlinesms.caseCancelConfirmAction();
        return false;
    });
};


frontlinesms.caseCancelConfirmAction = function() {

    if(jQuery.trim($("#case-id").val()) == "" && jQuery.trim($("#case-description").val()) == "") {
         $(window.location).attr("href" ,"/");
    }
    else {
    $("#case-cancel-dialog").dialog({
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
frontlinesms.caseCreateWithoutTitleConfirmAction = function() {

    if(jQuery.trim($("#case-title").val()) == "") {
        $("#blank-case-title-confirmation-dialog").dialog({
            modal: true,
            buttons: [
                {
                    text: "Yes",
                    click: function() {
                       $("#case-create-form").submit();
                    },
                    id: "save-confirm-yes"
                },
                {
                    text: "No",
                    click: function() {
                        $(this).dialog("close");
                    },
                    id: "save-confirm-no"
                }
            ]
        });
    }
    else
    {
        $("#case-create-form").submit();
    }
};

