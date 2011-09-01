var frontlinesms = this.frontlinesms || {};
frontlinesms.eventDetails = function() {
    $("#view-event").hide();
    $("#view-event").dialog({
        autoOpen: false,
        modal: true
    });

    $('#error-message').html("").fadeIn();
    $('#update-event').click(frontlinesms.updateEventDetails);
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
        $("#view-event").dialog("close");
        $.ajax({
            url :"../event/update",
            type: "POST",
            data:{"eventId": $('#event-id').val(), "eventTitle": $('#event-title').val(), "dateFieldSelected":$('#event-date').val(), "startTimeField":$('#event-start-time').val(), "endTimeField":$('#event-end-time').val()},
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
};
