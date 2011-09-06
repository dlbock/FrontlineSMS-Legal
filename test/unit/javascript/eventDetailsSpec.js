describe('Link Contact to existing Event', function () {
    beforeEach(function() {
        var tempHTML =
            '<div id="view-event" title="Event Details" style="display: none;">' +
                '<input type="button" id="update-event" value="Update" disabled="disabled"/>' +
                '<button id="link-contact-button">Link Contacts</button>' +
                '</div>' +
                '<input type="hidden" id="event-linked-contacts" value=""/>' +
                '<div id="link-contact-to-existing-event-dialog" title="Link Contacts to an Event">' +
                '<table id="contactsTable"> <tbody>' +
                '<tr class="contactRow" id="rupa">' +
                '<td  class="contact-name">Rupa</td>' +
                '<td class="contact-number">1234</td>' +
                '<td class="contactLink"> +' +
                '<a href="#">Link Contact</a> +' +
                '</td>' +
                '</tr>' +
                '<tr class="contactRow" id="neetu">' +
                '<td  class="contact-name">Neetu</td>' +
                '<td class="contact-number">3333</td>' +
                '<td class="contactLink"><a href="#">Link Contact</a></td>' +
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

    it("should enable update button when a contact has been linked to an existing event", function() {
        $("#link-contact-button").click();
        $("#rupa a").click();
        expect($("#update-event").attr("disabled")).toBeUndefined();

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
        $('body#fixtures > *').not(".jasmine_reporter").not('script').remove();
    });
});

describe('event detail pop up', function() {
    beforeEach(function() {
        var tempHTML = '<div id="view-event" title="Event Details" style="display: none;">' +
            '<button id="link-contact-button">Link Contacts</button>' +
            '</div>' +
            '<input type="hidden" id="event-linked-contacts" value=""/>' +
            '<table name="linkedContacts" id="event-contacts-table"> ' +
            '<thead>' +
            '<tr>' +
            '<th>Contact name</th>' +
            '<th> Phone number </th>' +
            '</tr>' +
            '</thead>' +
            '<tbody >' +
            '<tr class="event-contact">' +
            '<td>Abc</td>' +
            '<td>1234</td>' +
            '<td>' +
            '<a href="#" class="unlink-contact" id =1>Unlink</a>' +
            '</td>' +
            '</tr>' +
            '</tbody>' +
            '</table>' +
            '<input type="button" id="update-event" value="Update" disabled="disabled"/>'
            ;
        $(tempHTML).appendTo("#fixtures");
        frontlinesms.attachActionWithUnlink();
    });

    it("should enable Update button when UNLINK link next to a contact is clicked", function() {
        $('#1.unlink-contact').click();
        expect($('#update-event').attr("disabled")).toBeUndefined();
    })

    afterEach(function() {
        $('body#fixtures > *').not(".jasmine_reporter").not('script').remove();
    });

});
