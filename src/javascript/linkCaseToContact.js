var frontlinesms = this.frontlinesms || {};

frontlinesms.linkCaseToContact = function() {

    $("#link-case-dialog").dialog({
        autoOpen: false,
        modal: true,
        width: 'auto',
        open: function() {
            frontlinesms.log("inside open function in link case dialog");
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
        frontlinesms.log("clicked link case button");
        $("#link-case-dialog").dialog("open");
        return false;
    });

    $(".caseLink").click(function() {
        var caseId = $(this).attr('id');

        if (!frontlinesms.checkIfContactHasCaseLinked(caseId)) {
            var relationship = prompt("Relationship to case:") || "";
            frontlinesms.addLinkedCaseToHiddenField(caseId, relationship);
            frontlinesms.addLinkedCaseToTable(caseId, relationship);
             $('#contact-save').removeAttr("disabled");
        }
        $("#link-case-dialog").dialog("close");
        return false;
    });

    $("td.unlink-case-button").live('click', function() {
        var caseId = $(this).parent().find('td span.id:hidden').text();
        $(this).parent().remove();
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
            $(row).find('.case-name').html() +
            '<span class="id" style="display:none;">' + caseId + '</span>' +
            '</td>' +
            '<td>' +
            frontlinesms.encodeHTML(relationship) +
            '</td>' +
            '<td class="unlink-case-button">' +
            '<a href="">Unlink</a>' +
            '</td>'
    );
    $('#cases').append(rowToAdd);
}

frontlinesms.unlinkLinkedCaseFromHiddenField = function(caseId) {
    var linkedCases = $.parseJSON($('#contact-linked-cases').val()) || {};
    delete linkedCases[caseId];

    $('#contact-linked-cases').val(JSON.stringify(linkedCases));

};

frontlinesms.encodeHTML = function(value) {
    return $('<div/>').text(value).html();
};