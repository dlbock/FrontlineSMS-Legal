var frontlinesms = this.frontlinesms || {};
var linkContactIds = "";
var unlinkContactIds = "";
var eventDate = "";
frontlinesms.eventDetails = function() {
    $("#view-event").hide();
    $("#view-event").dialog({
        autoOpen: false,
        modal: true,
        open: function() {
            $('#update-event').attr('disabled', true);
            frontlinesms.attachActionWithUnlink();
        },
        beforeClose: frontlinesms.yesNoDialogBox
    });

    $('#error-message').html("").fadeIn();
    $('#update-event').click(frontlinesms.updateEventDetails);

    $('input').keyup(function() {
        $('#update-event').attr('disabled', false)
    });
};

frontlinesms.updateEventDetails = function () {
    var errorMessage = ""
    if ($('#event-title').val() == "") {
        errorMessage = "title.";
    } else if ($('#event-date').val() == "") {
        errorMessage = "date.";
    } else if ($('#event-start-time').val() == "") {
        errorMessage = "start time.";
    } else if ($('#event-end-time').val() == "") {
        errorMessage = "end time.";
    }
    if (errorMessage != "") {
        $('#error-message').html("An event must have a title, date and time. Please enter a " + errorMessage).fadeIn();
    } else {
        $('#update-event').attr('disabled', true);
        $("#view-event").dialog("close");
        $.ajax({
            url :"../event/update",
            type: "POST",
            data:{"eventId": $('#event-id').val(), "eventTitle": $('#event-title').val(), "dateFieldSelected":$('#event-date').val(), "startTimeField":$('#event-start-time').val(), "endTimeField":$('#event-end-time').val(), "linkedContacts":linkContactIds, "unlinkedContacts": unlinkContactIds},
            error: function () {
                frontlinesms.log("Failed to update.");
            },
            success : function() {
                frontlinesms.log("update success");
            },
            cache:false
        });

        window.location.reload(true);
    }
}

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
        buttons: [
            {
                text: "Cancel",
                click: function() {
                    $(this).dialog("close");
                },
                id: "cancel-button"
            }
        ]
    });

    $('#view-event').dialog({
        autoOpen: false,
        modal: true,
        close: function() {
            $("#event-linked-contacts").val("");
        }
    });


    $(".contactLink").click(function() {
        var contactId = $(this).attr('id');
        if (!frontlinesms.checkIfEventHasContactLinked(contactId)) {
            frontlinesms.addLinkedContactIdToHiddenField(contactId);
            frontlinesms.addLinkedContactToTableOnPopup(contactId);
            $('#update-event').attr('disabled', false)
        }
        $(dialogSelector).dialog("close");
        return false;
    });

    frontlinesms.addLinkedContactToTableOnPopup = function(contactId) {
        var row = $('#contactsTable').find('#' + contactId);
        var rowToAdd = $('<tr class="event-contact" >').append(
            '<td>' +
                $(row).find('.contact-name').text() +
                '<span class="id" style="display:none;">' + contactId + '</span>' +
                '</td>' +
                '<td>' +
                $(row).find('.contact-number').text() +
                '</td>' +
                '<td>' +
                '<a href="#" class="unlink-contact" id = ' + contactId + '>Unlink</a>' +
                '</td>'
        );
        $('#event-contacts-table').append(rowToAdd);
        linkContactIds += contactId + ",";
    }

    frontlinesms.attachActionWithUnlink = function() {
        $(".unlink-contact").live('click', function() {
            var contactId = $(this).attr("id");
            $(this).parent().parent().remove();
            frontlinesms.unlinkLinkedContactIdFromHiddenField(contactId);
            if (linkContactIds.indexOf(contactId) > -1) {
                var contactsArray = linkContactIds.split(",");
                for(var i=0; i<contactsArray.length; i++) {
                    if(contactsArray[i] == contactId)
                        contactsArray.splice(i,1);
                }
                linkContactIds = contactsArray.toString();
            }
            else
                unlinkContactIds += contactId + ",";
            $('#update-event').attr('disabled', false)
            return false;
        });
    }
};
