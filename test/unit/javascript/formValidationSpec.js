describe("Validate forms", function() {
    beforeEach(function() {
        var tempHTML =
                '<input id="contact-primary-mobile" type="text"/>';

        $(tempHTML).appendTo("#fixtures");
        frontlinesms.validateContactNumber();
    });

    afterEach(function() {
        $("#contact-primary-mobile").remove();
    });

});
