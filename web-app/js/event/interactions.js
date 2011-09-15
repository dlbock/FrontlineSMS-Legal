var frontlinesms = this.frontlinesms || {};

frontlinesms.activateDatePicker = function() {
    var initialDate;
    $('#event-date').datepicker({
        beforeShow:function(input, inst) {
            initialDate = $(input).val();
        },
        changeMonth: true,
        changeYear: true,
        dateFormat: 'MM d,yy',
        onSelect: function(dateText, inst) {
            if (initialDate != dateText) {
                $('#update-event').attr('disabled', false);
            }
        },
        onClose: function(dateText, inst) {
            $('#event-date').blur();
            if ($('#createEventForm').size() == 0) {
                var newContent = $("#event-date").val();
                $('#update-event').attr('disabled', !(newContent != originalDate));
            }
        }
    })
    $('#event-date').click(function() {
        $(this).datepicker('show');
    });
}

frontlinesms.activateTimePicker = function() {
    $('#event-start-time').timeEntry({
        spinnerImage: '',
        defaultTime: "00:00AM"
    });

    $('#event-end-time').timeEntry({
        spinnerImage: '',
        defaultTime: "00:00AM"
    });

    $("#event-start-time").change(function() {
        var date = $("#event-start-time").timeEntry('getTime');
        $("#event-end-time").timeEntry("setTime", new Date(0, 0, 0, date.getHours() + 1, date.getMinutes(), 0));
    });
}

frontlinesms.redirectToHomePage = function() {
    return  "/"
}

frontlinesms.eventCancelConfirmAction = function() {
    if (frontlinesms.isPageEmpty()) {
        $(window.location).attr("href", frontlinesms.redirectToHomePage());
    }
    else {
        $("#event-cancel-dialog").dialog({
            modal: true,
            buttons: [
                {
                    text: "Yes",
                    click: function() {
                        $(window.location).attr("href", "/");
                    },
                    id: "cancel-confirm-yes"
                },
                {
                    text: "No",
                    click: function() {
                        $(this).dialog("close")
                    },
                    id: "cancel-confirm-no"
                }
            ]
        });
        $("#event-cancel-dialog").dialog('open');
    }
}

frontlinesms.isPageEmpty = function() {
    if (frontlinesms.elementIsEmpty($("#event-title")) && frontlinesms.elementIsEmpty($("#event-date")) &&
        frontlinesms.elementIsEmpty($("#event-start-time")) && frontlinesms.elementIsEmpty($("#event-end-time"))) {
        return true;
    }
    else {
        return false;
    }
}

frontlinesms.elementIsEmpty = function(element) {
    if ($(element).val() == "") {
        return true;
    }
    else {
        return false;
    }

}
