var frontlinesms = this.frontlinesms || {};
var linkContactIds = "";
var unlinkContactIds = "";

frontlinesms.eventDetails = function() {
    $("#view-event").dialog({
        width: 500,
        autoOpen: false,
        modal: true,
        open: function() {
            $('#update-event').attr('disabled', true);
            frontlinesms.attachActionWithUnlink();
        },
        beforeClose: frontlinesms.yesNoDialogBox
    });

    $('#update-event').click(frontlinesms.updateEventDetails);
    $('#cancel-event').click(function(){
        $('#view-event').dialog('close');
    });

    frontlinesms.enableUpdateButtonOnKeyUpOf("#event-title");
    frontlinesms.enableUpdateButtonOnKeyUpOf("#event-date");
    frontlinesms.enableUpdateButtonOnKeyUpOf("#event-start-time");
    frontlinesms.enableUpdateButtonOnKeyUpOf("#event-end-time");
};

frontlinesms.enableUpdateButtonOnKeyUpOf = function(inputFieldId) {
    $(inputFieldId).keyup(function() {
        $('#update-event').attr('disabled', false)
    });
}

frontlinesms.yesNoDialogBox = function() {
    if ((!$("#update-event").attr("disabled") && !frontlinesms.deleteButtonHasBeenClicked()) || $('#error-message').text() != "") {
        $("#confirmation-dialog").dialog("destroy").empty();
        $("#schedule").append('<div class="confirmation-dialog" id="confirmation-dialog" title="Cancel edit event?" style="display: none;"><p>Are you sure you want to leave this page without saving? Your changes will not be saved.</p></div>');
        $("#confirmation-dialog").dialog({
            modal: true,
            buttons: [
                {
                    text: "Yes",
                    click: function() {
                        $(".confirmation-dialog").remove();
                        return true
                    },
                    id: "confirm-yes"
                },
                {
                    text: "No",
                    click: function() {
                        $(".confirmation-dialog").remove();
                        $("#view-event").dialog("open");
                        $('#update-event').attr('disabled', false);
                        return true
                    },
                    id: "confirm-no"
                }
            ]
        });
    }
}

frontlinesms.timeToDate = function (timeElement) {
    return $(timeElement).timeEntry('getTime');
}

frontlinesms.updateEventDetails = function () {
    var errorMessage = "";
    var errorMessageForTime = "";
    if ($('#event-title').val() == "") {
        errorMessage = "title.";
    } else if ($('#event-date').val() == "") {
        errorMessage = "date.";
    } else if ($('#event-start-time').val() == "") {
        errorMessage = "start time.";
    } else if ($('#event-end-time').val() == "") {
        errorMessage = "end time.";
    } else {
        if (errorMessage == "" && frontlinesms.timeToDate('#event-start-time') >= frontlinesms.timeToDate('#event-end-time')) {
            errorMessageForTime = "The end time cannot be earlier than the start time.";
        }
    }

    if (errorMessage != "" && errorMessageForTime != "") {
        errorMessage = "Please enter a " + errorMessage + errorMessageForTime;
    } else if (errorMessage != "") {
        errorMessage = "Please enter a " + errorMessage;
    } else if (errorMessageForTime != "") {
        errorMessage = errorMessageForTime;
    }

    if (errorMessage != "" || errorMessageForTime != "") {
        $('#error-message').html(errorMessage).fadeIn();
        $('#update-event').attr('disabled', true);
    } else {
        $('#error-message').html("").fadeIn();
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
                window.location.reload(true);
            },
            cache:false
        });
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
            frontlinesms.addDomainIdsToHiddenField(contactId, "event-linked-contacts");
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
        if (unlinkContactIds.indexOf(contactId) > -1) {
            var contactsArray = unlinkContactIds.split(",");
            for (var i = 0; i < contactsArray.length; i++) {
                if (contactsArray[i] == contactId)
                    contactsArray.splice(i, 1);
            }
            unlinkContactIds = contactsArray.toString();
        }
        linkContactIds += contactId + ",";
    }
};

frontlinesms.attachActionWithUnlink = function() {
    $(".unlink-contact").live('click', function() {
        var contactId = $(this).attr("id");
        $(this).parent().parent().remove();
        frontlinesms.unlinkLinkedContactIdFromHiddenField(contactId);
        if (linkContactIds.indexOf(contactId) > -1) {
            var contactsArray = linkContactIds.split(",");
            for (var i = 0; i < contactsArray.length; i++) {
                if (contactsArray[i] == contactId)
                    contactsArray.splice(i, 1);
            }
            linkContactIds = contactsArray.toString();
        }
        else
            unlinkContactIds += contactId + ",";
        $('#update-event').attr('disabled', false)
        return false;
    });
};

