describe('linkCaseToContact', function () {
    beforeEach(function() {
        var tempHTML =
            '<input type="hidden" id="contact-linked-cases" value=""/>' +
                '<div id="link-case-dialog" title="Link Cases">' +
                '<input name="caseSearchField" id="case-search-field"/>' +
                '<table id="casesTable">' +
                '<thead></thead>' +
                '<tbody>' +
                '<tr class="caseRow" id="case1">' +
                '<td class="case-id">case1</td>' +
                '<td class="case-status">active</td>' +
                '<td>' +
                '<a href="#" class="link-case">Link Case</a>' +
                '</td>' +
                '</tr>' +
                '<tr class="caseRow" id="case2">' +
                ' <td class="case-id">case2</td>' +
                '<td class="case-status">active</td>' +
                '<td>' +
                '<a href="#" class="link-case">Link Case</a>' +
                '</td>' +
                '</tr>' +
                '<button id = "cancel-button">Cancel</button>' +
                '</tbody>' +
                '</table>' +
                '</div>' +

                '<table id="cases">' +
                '</table>' +

                '<input id="contact-linked-cases" value="" name="linkedCases"/>' +
                '<button id="link-case-button">Link cases</button>' +
                '<button id="contact-save" disabled = "disabled">Update</button>'+
                '<div id="case-contact-relationship-dialog" title="Relationship to case" style="display: none;">' +
                '<input id="case-contact-relationship"/>' +
                '</div>';

        $(tempHTML).appendTo("#fixtures");

        frontlinesms.linkCaseToContact();
    });

    it('link case dialog is closed', function () {
        expect($('#link-case-dialog:visible').size()).toEqual(0);
    });

    it('when link-contacts button is clicked contacts dialog is opened', function () {
        $("#link-case-button").click();
        expect($('#link-case-dialog:visible').size()).toEqual(1);
    });

    it('when the case dialog is opened and a search phrase is entered, and the cancel button is clicked, case dialog is reopened, all results are visible and the search bar is cleared', function () {
        $("#link-case-button").click();
        $("#case-search-field").val("cas");
        $(".ui-button-text").click();
        $("#link-case-button").click();
        expect($("#case-search-field").val()).toEqual("");
    });

    it('when link-cases button is clicked case search dialog is opened', function () {
        $("#link-case-button").click();
        expect($('#link-case-dialog:visible').size()).toEqual(1);
    });

    it('should keep Update button Disabled when case linking is canceled', function() {
        spyOn(window, 'prompt').andReturn('client');
        $("#link-case-button").click();
        $("#cancel-button").click();
        expect($("#contact-save").attr("disabled")).toBeTruthy();
    });

    it('should not show relationship dialog if case Id is clicked', function() {
        $("#link-case-button").click();
        $("#case1.case-search-field").click();
        expect ($("#case-contact-relationship-dialog:visible").size()).toEqual(0);
    });

    it('should not show relationship dialog if case Status is clicked', function() {
        $("#link-case-button").click();
        $("#case1.status").click();
        expect ($("#case-contact-relationship-dialog:visible").size()).toEqual(0);
    });

    afterEach(function() {
        $('body#fixtures > *').not(".jasmine_reporter").not('script').remove();
    });
});