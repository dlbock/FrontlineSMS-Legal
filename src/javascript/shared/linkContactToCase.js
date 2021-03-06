var frontlinesms = this.frontlinesms || {};

frontlinesms.buildContactsRow = function (row, involvement) {
    $('#contacts').append('<tr id=' + $(this).attr('id') + '><td>' + $(row).find('.contact-name').text() + '</td><td>' + $(row).find('.contact-number').text() + '</td><td>' + involvement + '</td><td class="unlink-contact-button">Unlink</td></tr>');
}
frontlinesms.linkContactToCase = function() {
    $('#linked-contact-ids').val("");
    $("#link-contact-dialog").dialog({
        autoOpen: false,
        modal: true,
        width: 'auto',
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

    $(".unlink-contact-button").live('click', function() {
        var contactId = $(this).parent().find('td span.id:hidden').text();
        $(this).parent().remove();
        frontlinesms.unlinkLinkedContactFromCaseHiddenField(contactId);
        return false;
    });

    $(".contactLink").click(function() {
        var contactId = $(this).parents("tr").attr('id');
        if (!frontlinesms.checkIfCaseHasContactLinked(contactId)) {
            frontlinesms.relationshipDialog(contactId, "Contact");
            return false;
        }
    });
};

frontlinesms.addLinkedContactToCaseHiddenField = function(contactId, relationship) {
    var linkedContacts = $.parseJSON($('#case-linked-contacts').val()) || {};
    linkedContacts[contactId] = relationship;
    $('#case-linked-contacts').val(JSON.stringify(linkedContacts));
};

frontlinesms.unlinkLinkedContactFromCaseHiddenField = function(contactId) {
    var linkedContacts = $.parseJSON($('#case-linked-contacts').val()) || {};
    delete linkedContacts[contactId];

    $('#case-linked-contacts').val(JSON.stringify(linkedContacts));

};

frontlinesms.checkIfCaseHasContactLinked = function(contactId) {
    var linkedContacts = $.parseJSON($('#case-linked-contacts').val()) || {};
    return (contactId in linkedContacts);
};

frontlinesms.addLinkedContactToCaseTable = function(contactId, relationship) {
    var row = $('#contactsTable #' + contactId);
    var rowToAdd = $('<tr>').append(
        '<td>' +
            $(row).find('.contact-name').html() +
            '<span class="id" style="display:none;">' + contactId + '</span>' +
            '</td>' +
            '<td>' + $(row).find('.contact-number').html() + '</td>' +
            '<td>' +
            frontlinesms.encodeHTML(relationship) +
            '</td>' +
            '<td class="unlink-contact-button">' +
            '<a href="">Unlink</a>' +
            '</td>'
    );
    $('#contacts').append(rowToAdd);
}

frontlinesms.encodeHTML = function(value) {
    return $('<div/>').text(value).html();
};