var frontlinesms = this.frontlinesms || {};

frontlinesms.linkCaseToContactOnCreateContact = function() {

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
        frontlinesms.log("caseLinkButton has been clicked");
        var caseId = $(this).parents("tr").attr('id').trim();
        var status = $(this).parents("tr").children(".case-status").html().trim();

        if (!frontlinesms.checkIfContactHasCaseLinked(caseId)) {
            $("#case-contact-relationship-dialog").dialog({
                open: function(event, ui){
                    $("#case-contact-relationship").val("");
                },
                modal: true,
                buttons: [
                    {
                        text: "OK",
                        click: function() {
                            var relationship = $("#case-contact-relationship").val();
                            frontlinesms.addLinkedCaseToHiddenField(caseId, relationship);
                            frontlinesms.addLinkedCaseToTable(caseId, status, relationship);
                            $('#contact-save').removeAttr("disabled");
                            $(this).dialog("close");
                            $("#link-case-dialog").dialog("close");
                            return true
                        },
                        id: "confirm-yes"
                    },
                    {
                        text: "Cancel",
                        click: function() {
                            $(this).dialog("close");
                            return false
                        },
                        id: "confirm-no"
                    }
                ]
            });
        }

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

frontlinesms.addLinkedCaseToTable = function(caseId, status, relationship) {
    var row = $('#SearchResults #' + caseId);   
    var rowToAdd = $('<tr>').append(
        '<td>' +
            $(row).find('.case-name').html().trim() +
            '<span class="id" style="display:none;">' + caseId + '</span>' +
            '</td>' +
            '<td>' +
            frontlinesms.encodeHTML(status) +
            '</td>' +
            '<td>' +
            frontlinesms.encodeHTML(relationship) +
            '</td>' +
            '<td>' +
            '<a class="unlink-case-button" href="">Unlink</a>' +
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
