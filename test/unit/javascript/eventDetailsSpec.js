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
