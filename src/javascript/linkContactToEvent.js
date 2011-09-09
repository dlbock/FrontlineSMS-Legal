var frontlinesms = this.frontlinesms || {};

frontlinesms.linkContactToEvent = function() {
    $("#link-contact-dialog").dialog({
        width: 450,
        autoOpen: false,
        modal: true,
        open: function() {
            $("#contact-name-search").val("");
            $(".contactRow").removeAttr("filtermatch", true).show();
        },
        buttons: {
            "Cancel" : {
                text: "Cancel",
                id: "cancel-button",
                click: function() {
                    $("#contact-name-search").val("");
                    $(".contactRow").removeAttr("filtermatch", true).show();
                    $(this).dialog("close");
                }
            }
        }
    });

    $("#link-contact-button").click(function() {
        $("#link-contact-dialog").dialog("open");
        return false;
    });

    $(".contactLink").click(function() {
        var contactId = $(this).attr('id');
        if (!frontlinesms.checkIfEventHasContactLinked(contactId)) {
            frontlinesms.addDomainIdsToHiddenField(contactId, "event-linked-contacts");
            frontlinesms.addLinkedContactToTable(contactId);
        }
        $("#link-contact-dialog").dialog("close");
        return false;
    });

    $("td.unlink-contact-button").live('click', function() {
        var contactId = $(this).parent().find('td span.id:hidden').text();
        $(this).parent().remove();
        frontlinesms.unlinkLinkedContactIdFromHiddenField(contactId);
        return false;
    });
};

frontlinesms.unlinkLinkedContactIdFromHiddenField = function(contactId) {
    var contactIds = $("#event-linked-contacts").val().split(",");
    if (contactIds.indexOf(contactId) > -1)
        contactIds.splice(contactIds.indexOf(contactId), 1);
    $("#event-linked-contacts").val(contactIds.join(","));
}

frontlinesms.addLinkedContactToTable = function(contactId) {
    var row = $('#contactsTable').find('#' + contactId);
    var rowToAdd = $('<tr>').append(
        '<td>' +
            $(row).find('.contact-name').text() +
            '<span class="id" style="display:none;">' + contactId + '</span>' +
            '</td>' +
            '<td>' +
            $(row).find('.contact-number').text() +
            '</td>' +
            '<td class="unlink-contact-button">' +
            '<a href="">Unlink</a>' +
            '</td>'
    );
    $('#contacts').append(rowToAdd);
}

frontlinesms.checkIfEventHasContactLinked = function(contactId) {
    var contactIds = $("#event-linked-contacts").val().split(",");
    return (contactIds.indexOf(contactId) > -1);
};
