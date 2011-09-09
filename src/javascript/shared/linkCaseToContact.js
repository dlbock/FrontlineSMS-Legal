var frontlinesms = this.frontlinesms || {};

frontlinesms.linkCaseToContact = function() {

    $("#link-case-dialog").dialog({
        autoOpen: false,
        modal: true,
        width: 'auto',
        open: function() {
            $("#caseId").val("");
            $(".caseLink").removeAttr("filtermatch", true).show();

        },
        buttons: {
            "Cancel": function() {
                $("#caseId").val("");
                $(".caseLink").removeAttr("filtermatch", true).show();
                $(this).dialog("close");
            }
        }
    });

    $("#link-case-button").click(function() {
        $("#link-case-dialog").dialog("open");
        return false;
    });

    $(".caseLinkButton").click(function() {
        var caseId = $(this).parents("tr").attr('id');
        if (!frontlinesms.checkIfContactHasCaseLinked(caseId)) {
            frontlinesms.relationshipDialog(caseId, "Case");
            return false;
        }
    });

    $("a.unlink-case").live('click', function() {
        var caseId = $(this).parents("tr").find('td span.id:hidden').text();
        $(this).parents("tr").remove();
        $('#contact-save').removeAttr("disabled");
        frontlinesms.unlinkLinkedCaseFromHiddenField(caseId);
        return false;
    });
};

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
                        $(this).dialog("close");
                        $("#link-contacts").dialog("close");
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

frontlinesms.checkIfContactHasCaseLinked = function(caseId) {
    var linkedCases = $.parseJSON($('#contact-linked-cases').val()) || {};
    return (caseId in linkedCases);
};

frontlinesms.addLinkedCaseToHiddenField = function(caseId, relationship) {
    var linkedCases = $.parseJSON($('#contact-linked-cases').val()) || {};
    linkedCases[caseId] = relationship;
    $('#contact-linked-cases').val(JSON.stringify(linkedCases));
};

frontlinesms.addLinkedCaseToTable = function(caseId, relationship) {
    var row = $('#SearchResults #' + caseId);
    var rowToAdd = $('<tr>').append(
        '<td>' +
            $(row).find('.caseName').html().trim() +
            '<span class="id" style="display:none;">' + caseId + '</span>' +
            '</td>' +
            '<td>' +
            $(row).find('.caseTitle').html().trim() +
            '</td>' +
            '<td>' +
            frontlinesms.encodeHTML(relationship) +
            '</td>' +
            '<td>' +
            '<a class="unlink-case" href="">Unlink</a>' +
            '</td>'
    );
    $('#cases').append(rowToAdd);
    $('#contact-save').removeAttr("disabled");
};

frontlinesms.unlinkLinkedCaseFromHiddenField = function(caseId) {
    var linkedCases = $.parseJSON($('#contact-linked-cases').val()) || {};
    delete linkedCases[caseId];

    $('#contact-linked-cases').val(JSON.stringify(linkedCases));

};

frontlinesms.encodeHTML = function(value) {
    return $('<div/>').text(value).html();
};
