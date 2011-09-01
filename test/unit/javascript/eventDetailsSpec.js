describe('Link Contact to existing Event', function () {
    beforeEach(function() {
        var tempHTML =
            '<div id="view-event" title="Event Details" style="display: none;">' +
                '<button id="link-contact-button">Link Contacts</button>' +
                '</div>' +
                '<input type="hidden" id="event-linked-contacts" value=""/>' +
                '<div id="link-contact-to-existing-event-dialog" title="Link Contacts to an Event">' +
                '<table id="contactsTable"> <tbody>' +
                '<tr class="contactLink" id="rupa">' +
                '<td  class="contact-name">Rupa</td>' +
                '<td class="contact-number">1234</td>' +
                '<td class="link"><a href="#">Link Contact</a></td>' +
                '</tr>' +
                '<tr class="contactLink" id="neetu">' +
                '<td  class="contact-name">Neetu</td>' +
                '<td class="contact-number">3333</td>' +
                '<td class="link"><a href="#">Link Contact</a></td>' +
                '</tr>' +
                '</tbody></table> ' +

                '</div>'
            ;
        $(tempHTML).appendTo("#fixtures");

        frontlinesms.attachActionWithLinkContactButton("#link-contact-button", "#link-contact-to-existing-event-dialog");
    });

    it("is not visible before the link contact button is clicked", function() {
        expect($('#link-contact-to-existing-event-dialog:visible').size()).toEqual(0);
    });

    it("pops up when the button is clicked", function() {
        $("#link-contact-button").click();
        expect($('#link-contact-to-existing-event-dialog:visible').size()).toEqual(1);
    });

    afterEach(function() {
        $('body#fixtures > *').not(".jasmine_reporter").not('script').remove();
    });
});

describe('Hidden Field', function () {
    beforeEach(function() {
        var tempHTML = '<input type="hidden" id="event-linked-contacts" value=""/>';
        $(tempHTML).appendTo("#fixtures");

        var data = [
            {id: 'id1', name: 'john', primaryMobile: '1234', notes: 'aa'},
            {id: 'id2', name: 'bob', primaryMobile: '2345', notes: 'aa'},
            {id: 'id3', name: 'jane', primaryMobile: '3456', notes: 'aa'}
        ];
        frontlinesms.populateHiddenFieldOnClick(data);
    });

    it("populates the hidden field with linked contacts on clicking an event on the Schedule Page", function() {
        expect($('#event-linked-contacts').val()).toEqual("id1,id2,id3");
    });

    afterEach(function() {
        $('body#fixtures > *').not(".jasmine_reporter").not('script').remove();
    });
});

describe('event detail pop up', function() {
    beforeEach(function() {
        var tempHTML =
            "<input type=\"button\" name=\"li\" id=\"update-button\"/>" +
                "<div id=\"error-message\"/><br/>" +
                "<div id=\"view-event\" title=\"Event Details\">" +
                "<label>Title :</label>" +
                "<input type='text' name=\"eventTitle\" id=\"event-title\" value=''/><br/>" +
                "<label>Date :</label>" +
                "<input type='text' name=\"eventDate\" id=\"event-date\" value=''/><br/>" +
                "<label>Start Time :</label>" +
                "<input type='text' name=\"eventStartTime\" id=\"event-start-time\" value=''/><br/>" +
                "<label>End Time :</label>" +
                "<input type='text' name=\"eventEndTime\" id=\"event-end-time\" value=''/><br/>" +
                "<input type=\"button\" id=\"update-event\" value=\"Update\"/>" +
                "<input type=\"button\" id=\"delete-event\" value=\"Delete\"/>" +
                "<input type=\"button\" id=\"link-contact-to-existing-event-button\" value=\"Link Contact\"/>" +
                "<label name=\"eventId\" id=\"event-id\"></label>" +
                "</div>";
        $(tempHTML).appendTo("#fixtures");
        $('#update-button').click(function() {
            frontlinesms.updateEventDetails();
        });
    });

    it("show error message when title field is blank", function() {
        $('#event-title').val("");
        $("#update-button").click();
        expect($('#error-message').html()).toBe("An event must have a title, date and time. Please enter a title.");
    });

    it("show error message when date field is blank", function() {
        $('#event-title').val("test-title");
        $('#event-date').val("");
        $("#update-button").click();
        expect($('#error-message').html()).toBe("An event must have a title, date and time. Please enter a date.");
    });

    it("show error message when start time field is blank", function() {
        $('#event-title').val("test-title");
        $('#event-date').val("August 31,2011");
        $('#event-start-time').val("");
        $("#update-button").click();
        expect($('#error-message').html()).toBe("An event must have a title, date and time. Please enter a start time.");
    });

    it("show error message when end time field is blank", function() {
        $('#event-title').val("test-title");
        $('#event-date').val("August 31,2011");
        $('#event-start-time').val("05:20PM");
        $('#event-end-time').val("");
        $("#update-button").click();
        expect($('#error-message').html()).toBe("An event must have a title, date and time. Please enter a end time.");
    });

    afterEach(function() {
        $("#view-event, #test-dialog-button").remove();
    });
});
