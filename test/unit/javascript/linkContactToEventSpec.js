describe('linkContactToEvent', function () {
    beforeEach(function() {
        var tempHTML =
            '<div id="text-div">' +
                '<input type="hidden" id="event-linked-contacts" value=""/>' +
                '<div id="link-contacts" title="Link Contacts">' +
                '<input name="contactNameSearch" id="contact-name-search"/>' +
                '<table id="contactsTable"> <tbody>' +
                '<tr class="contactLink" id="fabio">' +
                '<td  class="contact-name"> <a href="#" >fabio</a> </td>' +
                '<td class="contact-number"> <a href="#">99999</a> </td>' +
                '</tr>' +
                '<tr class="contactLink" id="dahlia">' +
                '<td  class="contact-name"> <a href="#" >dahlia</a> </td>' +
                '<td class="contact-number"> <a href="#">33333</a> </td>' +
                '</tr>' +
                '</tbody></table> ' +
                '</div>' +

                '<table name="contacts" id="contacts">' +
                '<tr>' +
                '<th>Contact Name</th>' +
                '<th>Phone</th>' +
                '<th style="display: none;"></th>' +
                '</tr> </table>' +

                '<button id="link-contact-button">Link contacts</button>' +
                '</div>';

        $(tempHTML).appendTo("#fixtures");
        frontlinesms.linkContactToEvent();

    });

    it('link contacts dialog is closed', function () {
        expect($('#link-contacts:visible').size()).toEqual(0);
    });

    it('when link-contacts button is clicked contacts dialog is opened', function () {
        $("#link-contact-button").click();
        expect($('#link-contacts:visible').size()).toEqual(1);
    });

    it('when the contact dialog is opened and a search phrase is entered, and the cancel button is clicked, contact dialog is reopened, all results are visible and the search bar is cleared', function () {
        $("#link-contact-button").click();
        $("#contact-name-search").val("fab");
        $(".ui-button-text").click();
        $("#link-contact-button").click();
        expect($("#contact-name-search").val()).toEqual("");
    });

    it('when unlink button is clicked the appropriate contact is unlinked from event', function(){
        $("#link-contact-button").click();
        $("#fabio").click();
        var secondRowSelector = "table#contacts tr:nth-child(2)";
        $(secondRowSelector + " td.unlink-contact-button").click();
        expect($(secondRowSelector + ":contains('fabio')").size()).toEqual(0);
    });

    it('when unlink button is clicked the appropriate contact id is unlinked from hidden form field', function(){
        $("#link-contact-button").click();
        $("#fabio").click();
        $("#link-contact-button").click();
        $("#dahlia").click();
        var secondRowSelector = "table#contacts tr:nth-child(2)";
        $(secondRowSelector + " td.unlink-contact-button").click();
        expect($('#event-linked-contacts').val()).toEqual("dahlia");
    });

    it('when link-contacts button is clicked contacts dialog is opened', function () {
        $("#link-contact-button").click();
        expect($('#link-contacts:visible').size()).toEqual(1);
    });

    it('when a contact is clicked on link contact dialog box it should appear on show case page table', function() {
        $("#link-contact-button").click();
        $("#fabio").click();
        expect($('#contacts tr > td:contains(fabio) + td:contains(99999)').length).toEqual(1);
    });

    it('when a contact is clicked on link contact dialog box it\'s id should appear in the hidden field', function() {
        $("#link-contact-button").click();
        $("#fabio").click();
        expect($('#event-linked-contacts').val()).toEqual("fabio");
    });

    it('when two contacts are clicked on link contact dialog box their ids should appear in the hidden field', function() {
        $("#link-contact-button").click();
        $("#fabio").click();
        $("#link-contact-button").click();
        $("#dahlia").click();
        expect($('#event-linked-contacts').val()).toEqual("fabio,dahlia");
    });

    afterEach(function() {
        $('body#fixtures > *').not(".jasmine_reporter").not('script').remove()
    });
});