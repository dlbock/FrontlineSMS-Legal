describe('Confirmation Dialog', function () {
    var actionWasInvoked;
    var clickedElement;

    beforeEach(function() {
        var tempHTML =
            '<div id="actionDialogBox" title="Delete Case" style="display: none;">' +
                '<p>Are you sure you want to delete this case?</p>' +
                '</div>' +
                '<button id="actionButton">Action</button>';
        $(tempHTML).appendTo("#fixtures");

        actionWasInvoked = false;
        var actionForYesButton = function(target) {
            actionWasInvoked = true;
            clickedElement = target;
        };

        frontlinesms.attachActionWithConfirmationToButton("#actionButton", "#actionDialogBox", actionForYesButton);
    });

    it("is not visible before the action button is clicked", function() {
        expect($('#actionDialogBox:visible').size()).toEqual(0);
    });

    it("pops up when the button is clicked", function() {
        $("#actionButton").click();
        expect($('#actionDialogBox:visible').size()).toEqual(1);
    });

    it("closes when clicking the NO button", function() {
        $("#actionButton").click();
        $("#confirm-no").click();
        expect($('#actionDialogBox:visible').size()).toEqual(0);
    });

    it("closes when clicking the YES button", function() {
        $("#actionButton").click();
        $("#confirm-yes").click();
        expect($('#actionDialogBox:visible').size()).toEqual(0);
    });

    it("performs an action when YES is clicked", function() {
        $("#actionButton").click();
        $("#confirm-yes").click();
        expect(actionWasInvoked).toBeTruthy();
    });

    it("performs no action when NO is clicked", function() {
        $("#actionButton").click();
        $("#confirm-no").click();
        expect(actionWasInvoked).toBeFalsy();
    });
    
    it("passes the clicked element to the action", function() {
        $("#actionButton").click();
        $("#confirm-yes").click();
        expect(clickedElement).toEqual($("#actionButton")[0]);
    });

    afterEach(function() {
        $('body#fixtures > *').not(".jasmine_reporter").not('script').remove()
    })

});
