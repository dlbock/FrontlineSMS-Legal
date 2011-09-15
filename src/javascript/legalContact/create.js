var frontlinesms = this.frontlinesms || {};

frontlinesms.createNewContactOnLoad = function() {

    $("#contact-save").click(function() {
        frontlinesms.contactCreateWithoutNameConfirmAction();
        return false;
    });
    $("#contact-create-cancel").click(function() {
        frontlinesms.contactCancelConfirmAction();
        return false;
    });
    frontlinesms.enableUpdateButtonOnDetailsChange("#contact-name", "#contact-save");
    frontlinesms.enableUpdateButtonOnDetailsChange("#contact-primary-mobile", "#contact-save");
    frontlinesms.enableUpdateButtonOnDetailsChange("#contact-notes", "#contact-save");

};

frontlinesms.contactCancelConfirmAction = function() {
    if (jQuery.trim($("#contact-name").val()) == "" && jQuery.trim($("#contact-primary-mobile").val()) == "" && jQuery.trim($("#contact-notes").val()) == "") {
        $(window.location).attr("href", "/");
    }
    else{
        $("#contact-create-cancel-dialog").dialog({
                modal: true,
                buttons: [
                    {
                        text: "Yes",
                        click: function(){
                            $(window.location).attr("href", "/");
                            $(this).dialog("close");
                        },
                        id: "contact-create-cancel-confirm"
                    },
                    {
                        text: "No",
                        click: function() {
                            $(this).dialog("close");
                        },
                        id: "contact-create-cancel-abort"
                    }
                ]
            });
    }
};

frontlinesms.contactCreateWithoutNameConfirmAction = function() {
    var ajaxDefaults = {
        dataType: 'json',
        cache: false
    };


    if (jQuery.trim($("#contact-name").val()) == "" && jQuery.trim($("#contact-primary-mobile").val()) != "") {
        $("#contact-save-no-name-dialog").dialog({
                    modal: true,
                    buttons: [
                        {
                            text: "Yes",
                            click: function() {
                                $(this).dialog("close");
                                $("#contact-save-form").submit();

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
    else {
        $("#contact-save-form").submit();
    }
};


