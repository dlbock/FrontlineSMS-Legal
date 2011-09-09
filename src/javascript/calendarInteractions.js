var frontlinesms = this.frontlinesms || {};
var deleteButtonClicked;

frontlinesms.calculateScheduleHeight = function (windowHeight) {
    var headerHeight = $("#header").outerHeight();
    var schedulePadding = parseInt($("#schedule").css('padding-top')) + parseInt($("#schedule").css('padding-bottom'));
    return windowHeight - headerHeight - schedulePadding;
};

frontlinesms.populateHiddenFieldWithLinkedContacts = function(data) {
    for (var i = 0; i < data.length; i++) {
        var contactId = data[i]["id"];
        frontlinesms.addDomainIdsToHiddenField(contactId, "event-linked-contacts");
    }
};

frontlinesms.populateHiddenFieldWithLinkedCases = function(data) {
    for (var i = 0; i < data.length; i++) {
        var caseId = data[i]["id"];
        frontlinesms.addDomainIdsToHiddenField(caseId, "event-linked-cases");
    }
};

frontlinesms.addDomainIdsToHiddenField = function(domainId, hiddenFieldId) {
    var domainIds = $("#" + hiddenFieldId).val().split(",");
    domainIds = (domainIds.length == 1 && domainIds[0] == "") ? [] : domainIds;
    domainIds.push(domainId);
    $("#" + hiddenFieldId).val(domainIds.join(","));
};

frontlinesms.displayEventDetails = function(calEvent) {
    $('#event-title').val(calEvent.title);
    $('#event-start-time').val(frontlinesms.getFormattedTimeString(calEvent.start.getHours(), calEvent.start.getMinutes()));
    if (calEvent.end != null)
        $('#event-end-time').val(frontlinesms.getFormattedTimeString(calEvent.end.getHours(), calEvent.end.getMinutes()));
    else
        $('#event-end-time').val($('#event-start-time').text());
    $("#event-date").val($.datepicker.formatDate("MM d,yy", calEvent.start));
    $('#event-id').val(calEvent.id);
    $.ajax({
        url: "fetchEventContacts/" + calEvent.id,
        type: "POST",
        dataType: 'json',
        error: function (data) {
            frontlinesms.log("Failed to get linked contacts for event.");
        },
        success : function(data) {

            frontlinesms.log("Success" + data.toString() + "  " + calEvent.id);
            frontlinesms.populateHiddenFieldWithLinkedContacts(data);
            frontlinesms.constructContactsTable(data, calEvent.id);
        },
        cache:false
    });

    $.ajax({
        url: "fetchEventCases/" + calEvent.id,
        type: "POST",

        dataType: 'json',
        error: function (data) {
            frontlinesms.log("Failed to get linked cases for event.");
        },
        success : function(data) {
            frontlinesms.log("SuccessCases:");
            frontlinesms.log(data);
            frontlinesms.populateHiddenFieldWithLinkedCases(data);
            frontlinesms.constructCasesTable(data);
        },
        cache:false
    });

    $("#view-event").dialog("open");
    $('#error-message').html("").fadeIn();
};

frontlinesms.constructContactsTable = function(data, eventId) {

    $('#event-contacts-table tbody *').remove();
    for (var i = 0; i < data.length; i++) {
        var contactId = data[i]["id"];
        var newRow =
            '<tr class="event-contact">' +
                '<td>' + data[i]["name"] + '</td>' +
                '<td>' + data[i]["primaryMobile"] + '</td>' +
                '<td>' +
                '<a href="#" class="unlink-contact" id = ' + contactId + '>Unlink</a>' +
                '</td>' +
                '</tr>';
        $('#event-contacts-table tbody').append(newRow);
    }
};

frontlinesms.constructCasesTable = function(data) {

    $('#cases tbody *').remove();
    for (var i = 0; i < data.length; i++) {
        var status;
        if (data[i]["status"]) {
            status = "active";
        }
        else {
            status = "inactive";
        }
        var newRow =
            '<tr class="event-cases">' +
                '<td>' + data[i]["id"] + '</td>' +
                '<td>' + data[i]["caseTitle"] + '</td>' +
                '<td>' + status + '</td>' +
                '</tr>';
        $('#cases tbody').append(newRow);
    }
};


frontlinesms.getFormattedTimeString = function(hr, min) {
    formattedString = "";
    var isAM;
    if (hr > 12) {
        isAM = false;
        hr -= 12;
    }
    else if (hr == 0) {
        hr = 12;
        isAM = true;
    }
    else if (hr == 12) {
        isAM = false;
    }
    else {
        isAM = true;
    }

    if (hr < 10) {
        formattedString += "0";
    }
    formattedString += hr + ":";
    if (min < 10) {
        formattedString += "0";
    }
    formattedString += min;
    formattedString += isAM ? "AM" : "PM";
    return formattedString;
}

frontlinesms.calendarInteractions = function() {
    $('#schedule').fullCalendar({
        theme: true,
        height: frontlinesms.calculateScheduleHeight($(window).height()),
        events: {
            url:'fetchEvents',
            type: 'POST',
            dataType: 'json'
        },
        header: {
            left: 'prev,next today',
            center: 'title',
            right: 'month,agendaWeek,agendaDay'
        },
        defaultView: 'month',
        timeFormat: 'hh:mm tt',
        allDayDefault: false,
        eventColor: "rgb(0,162,232)",
        eventClick: function(calEvent, jsEvent, view) {
            deleteButtonClicked = false;
            frontlinesms.displayEventDetails(calEvent);
        },
        windowResize: function () {
            $("#schedule").fullCalendar("option", "height", frontlinesms.calculateScheduleHeight($(window).height()))
        }
    })


    var ajaxDefaults = {
        dataType: 'json',
        cache: false
    };

    frontlinesms.attachActionWithConfirmationToButton("#delete-event", "#delete-event-dialog", function () {
        deleteButtonClicked = true;
        $("#view-event").dialog("close");
        $.ajax("deleteEvent/" + $('#event-id').val(), ajaxDefaults);
        $('#schedule').fullCalendar('removeEvents', $('#event-id').val())
    });

    frontlinesms.attachActionWithLinkContactButton('#link-contact-button', '#link-contact-dialog');
}

frontlinesms.deleteButtonTracker = function() {
    return deleteButtonClicked;
}