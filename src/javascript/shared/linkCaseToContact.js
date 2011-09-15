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
            jQuery.trim($(row).find('.case-id').html()) +
            '<span class="id" style="display:none;">' + caseId + '</span>' +
            '</td>' +
            '<td>' +
            jQuery.trim($(row).find('.case-title').html()) +
            '</td>' +
            '<td>' +
            frontlinesms.encodeHTML(relationship) +
            '</td>' +
            '<td class="case-unlink">' +
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
