describe('Link Contact to existing Event', function () {
        beforeEach(function() {
            var tempHTML =
                '<div id="actionDialogBox" title="Link Contact" style="display: none;">' +
                    '<p>Link Contact</p>' +
                    '</div>' +
                    '<button id="actionButton">Action</button>';
            $(tempHTML).appendTo("#fixtures");

            frontlinesms.attachActionWithLinkContactButton("#actionButton", "#actionDialogBox");
        });

        it("is not visible before the link contact button is clicked", function() {
            expect($('#actionDialogBox:visible').size()).toEqual(0);
        });

        it("pops up when the button is clicked", function() {
            $("#actionButton").click();
            expect($('#actionDialogBox:visible').size()).toEqual(1);
        });

        afterEach(function() {
            $('body#fixtures > *').not(".jasmine_reporter").not('script').remove()
        })
    });
