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

    $(".link-case").click(function() {
        var caseId = $(this).attr('id');
        alert(caseId);
        if (!frontlinesms.checkIfEventHasCaseLinked(caseId)) {
            frontlinesms.addLinkedCaseIdToHiddenField(caseId);
            frontlinesms.addLinkedCaseToTable(caseId);
        }
        $("#link-case").dialog("close");
        return false;
    });

    frontlinesms.checkIfEventHasCaseLinked = function(caseId) {
        var caseIds = $("#event-linked-cases").val().split(",");
        return (caseIds.indexOf(caseId) > -1);
    };

    frontlinesms.addLinkedCaseIdToHiddenField = function(caseId) {
        var caseIds = $("#event-linked-cases").val().split(",");
        caseIds = (caseIds.length == 1 && caseIds[0] == "") ? [] : caseIds;
        caseIds.push(caseId);
        $("#event-linked-cases").val(caseIds.join(","));
    };

    frontlinesms.addLinkedCaseToTable = function(caseId) {
        var row = $('.search-results').find('#' + caseId);
        var rowToAdd = $('<tr>').append(
            '<td>' +
                $(row).find('.case-id').text() +
                caseId +
                '</td>' +
                '<td>' +
                $(row).find('.case-status').text() +
                '</td>'
        );
        $('#cases').append(rowToAdd);

    }
};
