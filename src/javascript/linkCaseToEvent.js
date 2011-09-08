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
        if (!frontlinesms.checkIfEventHasCaseLinked(caseId)) {
            frontlinesms.addDomainIdsToHiddenField(caseId, "event-linked-cases");
            frontlinesms.addLinkedCaseToTable(caseId);
        }
        $("#link-case-dialog").dialog("close");
        return false;
    });

    frontlinesms.checkIfEventHasCaseLinked = function(caseId) {
        var caseIds = $("#event-linked-cases").val().split(",");
        return (caseIds.indexOf(caseId) > -1);
    };

    frontlinesms.addLinkedCaseToTable = function(caseId) {
        var row = $('.search-results').find('#' + caseId);
        var rowToAdd = $('<tr>').append(
            '<td>' +
                $(row).find('.case-id').text() +
                '</td>' +
                '<td>' +
                $(row).find('.case-title').text() +
                '</td>' +
                '<td>' +
                $(row).find('.case-status').text() +
                '</td>'+
                '<td>' +
                '<a class="unlink-case" href="">Unlink</a>' +
                '</td>'
        );
        $('#cases').append(rowToAdd);

    }
};
