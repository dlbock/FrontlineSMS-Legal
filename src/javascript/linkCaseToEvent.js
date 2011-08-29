var frontlinesms = this.frontlinesms || {};

frontlinesms.linkCaseToEvent = function() {

    $("#link-case-dialog").dialog({
        autoOpen: false,
        modal: true,
        width: 'auto',
        open: function() {
            $("#caseId").val("");
            $(".caseLink").removeAttr("filtermatch", true).show();
        },

        buttons: {
            "Cancel" : {
                text: "Cancel",
                id: "cancel-link-case",
                click: function() {
                    $("#caseId").val("");
                    $(".caseLink").removeAttr("filtermatch", true).show();
                    $(this).dialog("close");
                }
            }
        }
    });

    $("#link-case-button").click(function() {
        $("#link-case-dialog").dialog("open");
        return false;
    });

};
