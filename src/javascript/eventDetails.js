var frontlinesms = this.frontlinesms || {};

frontlinesms.eventDetails = function() {
    $("#view-event").hide();
    $("#view-event").dialog({
                autoOpen: false,
                modal: true
        
            });
};

frontlinesms.attachActionWithLinkContactButton = function(buttonSelector, dialogSelector) {

    $(buttonSelector).click(function() {
        $(dialogSelector).dialog("open");
        return false;
    });

    $(dialogSelector).dialog({
        autoOpen: false,
        modal: true,
        open: function() {
            $("#contact-name-search").val("");
            $(".contactRow").removeAttr("filtermatch", true).show();
        },
        buttons: [{
            text: "Cancel",
            click: function() {
                $(this).dialog("close");
            },
            id: "cancel-button"
        }]
    });

    $('#view-event').dialog({
        autoOpen: false,
        modal: true,
        close: function(){
            $("#event-linked-contacts").val("");
        }
    });


    $(".contactLink").click(function() {
        var contactId = $(this).attr('id');
        if (!frontlinesms.checkIfEventHasContactLinked(contactId) && !frontlinesms.checkForExistingContacts(contactId)) {
            frontlinesms.addLinkedContactIdToHiddenField(contactId);
            frontlinesms.addLinkedContactToTableOnPopup(contactId);
        }
        $(dialogSelector).dialog("close");
        return false;
    });

    frontlinesms.addLinkedContactToTableOnPopup = function(contactId) {
        var row = $('#contactsTable').find('#' + contactId);
        var rowToAdd = $('<tr>').append(
            '<td>' +
                $(row).find('.contact-name').text() +
                '<span class="id" style="display:none;">' + contactId + '</span>' +
                '</td>' +
                '<td>' +
                $(row).find('.contact-number').text() +
                '</td>'
        );
    $('#event-contacts-table').append(rowToAdd);
    }

    frontlinesms.checkForExistingContacts = function(contactId) {
        //$(".event-contact").collect
    }
};




