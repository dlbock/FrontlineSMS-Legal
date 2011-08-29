describe('linkCaseToContact', function () {
    beforeEach(function() {
        var tempHTML =
            '<input type="hidden" id="contact-linked-cases" value=""/>' +
                '<div id="link-case-dialog" title="Link Cases">' +
                '<input name="caseIdSearch" id="caseId"/>' +
                '<table id="SearchResults">' +
                '<thead></thead>' +
                '<tbody>' +
                '<tr class="caseLink" id="case1">' +
                '<td class="case-name">case1</td>' +
                '<td>active</td>' +
                '</tr>' +
                '<tr class="caseLink" id="case2">' +
                ' <td class="case-name">case2</td>' +
                '<td>active</td>' +
                '</tr>' +
                '<button id = "cancel-button">Cancel</button>' +
                '</tbody>' +
                '</table>' +
                '</div>' +

                '<table id="cases">' +
                '</table>' +

                '<button id="link-case-button">Link cases</button>' +
                '<button id="contact-save" disabled = "disabled">Update</button>';
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
        $("#caseId").val("cas");
        $(".ui-button-text").click();
        $("#link-case-button").click();
        expect($("#caseId").val()).toEqual("");
    });

    it('when unlink button is clicked the appropriate case is unlinked from contact', function() {
        spyOn(window, 'prompt').andReturn('client');
        $("#link-case-button").click();
        $("#case1").click();
        var secondRowSelector = "table#linked-cases tr:first";
        $(secondRowSelector + " td.unlink-case-button").click();
        expect($(secondRowSelector + ":contains('case1')").size()).toEqual(0);
    });

    it('when unlink button is clicked the appropriate case is unlinked from hidden form field', function() {
        spyOn(window, 'prompt').andReturn('client');
        $("#link-case-button").click();
        $("#case1").click();
        $("#link-case-button").click();
        $("#case2").click();
        var secondRowSelector = "table#cases tr:nth-child(2)";
        $(secondRowSelector + " td.unlink-case-button").click();
        expect($('#contact-linked-cases').val()).toEqual("{\"case1\":\"client\"}");
    });

    it('when link-cases button is clicked case search dialog is opened', function () {
        spyOn(window, 'prompt').andReturn('client');
        $("#link-case-button").click();
        expect($('#link-case-dialog:visible').size()).toEqual(1);
    });

    it('should enable the Update button when case is unlinked', function() {
        spyOn(window, 'prompt').andReturn('client');
        $("#link-case-button").click();
        $("#case1").click();
        var secondRowSelector = "table#linked-cases tr:first";
        $(secondRowSelector + " td.unlink-case-button").click();
        expect($("#contact-save").attr("disabled")).toBeUndefined();
    });

    it('should enable the Update button when case is linked', function() {
        spyOn(window, 'prompt').andReturn('client');
        $("#link-case-button").click();
        $("#case1").click();
        expect($("#contact-save").attr("disabled")).toBeUndefined();
    });

    it('should keep Update button Disabled when case linking is canceled', function() {
        spyOn(window, 'prompt').andReturn('client');
        $("#link-case-button").click();
        $("#cancel-button").click();
        expect($("#contact-save").attr("disabled")).toBeTruthy();
    });
    afterEach(function() {
        $('body#fixtures > *').not(".jasmine_reporter").not('script').remove();
    });
});