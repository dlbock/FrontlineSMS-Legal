describe('linkContactToCase', function () {
    beforeEach(function() {
        var tempHTML =
            '<div id="text-div">' +
                '<input type="hidden" id="case-linked-contacts" value=""/>' +
                '<div id="link-contacts" title="Link Contacts">' +
                '<input name="contactNameSearch" id="contact-name-search"/>' +
                '<table id="contactsTable">' +
                '<tbody>' +

                '<tr class="contactLink" id="5">' +
                '<td  class="contact-name"> <a href="#" >fabio</a> </td>' +
                '<td class="contact-number"> <a href="#">99999</a> </td>' +
                '</tr>' +

                '<tr class="contactLink" id="6">' +
                '<td  class="contact-name"> <a href="#" >dahlia</a> </td>' +
                '<td class="contact-number"> <a href="#">88888</a> </td>' +
                '</tr>' +
                ' </tbody>' +
                '</table> ' +
                '</div>' +

                '<table name="contacts" id="contacts">' +
                '<tr>' +
                '<th>Contact Name</th>' +
                '<th>Phone</th>' +
                '<th>Involvement</th>' +
                '<th style="display: none;"></th>' +
                '</tr> </table>' +

                '<button id="link-contact-button">Link contacts</button>' +
                '</div>';


        $(tempHTML).appendTo("#fixtures");
        frontlinesms.linkContactToCase();
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

    it('when a contact is clicked on link contact dialog box it should appear on show case page table', function() {
        spyOn(window, 'prompt').andReturn('Client');
        $("#link-contact-button").click();
        $("#5").click();
        expect($('#contacts tr > td:contains(fabio) + td:contains(99999) + td:contains(Client)').length).toEqual(1);
    });

    it('when a contact is clicked in the link contacts  the Unique Id from database should be appended to caseLinkedContact', function() {
        spyOn(window, 'prompt').andReturn('Client');
        $('#5').click();
        expect($('#case-linked-contacts').val()).toEqual("{\"5\":\"Client\"}");
    });

    it('when a contact is removed from the linked contacts list the Unique Id from database should be removed from caseLinkedContact', function() {
        spyOn(window, 'prompt').andReturn('Client');
        $('#5').click();
        $('#6').click();
        var firstContactSelector = "table#contacts tr:nth-child(2)";
        $(firstContactSelector + " td.unlink-contact-button").click();
        expect($('#case-linked-contacts').val()).toEqual("{\"6\":\"Client\"}");
    });

    it('when a contact has already been added, and is clicked again on the popup , the contact shouldnt get added again', function() {
        spyOn(window, 'prompt').andReturn('Client');
        $('#5').click();
        var oldLinkedContacts = $('#linked-contact-ids').val();
        $('#5').click();
        var newLinkedContacts = $('#linked-contact-ids').val();
        expect(oldLinkedContacts).toEqual(newLinkedContacts);
    });

    it('when remove button is clicked the appropriate contact is removed', function() {
        spyOn(window, 'prompt').andReturn('Client');
        $("#link-contact-button").click();
        $("#5").click();
        var secondRowSelector = "table#contacts tr:nth-child(2)";
        $(secondRowSelector + " td.unlink-contact-button").click();
        expect($(secondRowSelector + ":contains('fabio')").size()).toEqual(0);
    });

    afterEach(function() {
        $('body#fixtures > *').not(".jasmine_reporter").not('script').remove()
    })


});