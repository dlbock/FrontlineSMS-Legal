describe("Validate forms", function() {
    beforeEach(function() {
        var tempHTML =
            '<input id="contact-primary-mobile-valid" type="text" value="12-34+34#09*1"/>' +
                '<input id="contact-primary-mobile-too-long" type="text" value="1222222222222222-34+34#09*1"/>' +
                '<input id="contact-primary-mobile-fifteen-characters" type="text" value="123456789012345"/>'

                $(tempHTML).appendTo("#fixtures");
        frontlinesms.validateContactNumber();
    });

    it("has a contact number text field which is enabled when it has less than 15 characters", function() {
        var contactNumberLength = $("#contact-primary-mobile-valid").val().length;
        expect(validateContactNumberLength(contactNumberLength)).toBeTruthy();
    });

    it("has a contact number text field which is disabled when it has more than 15 characters", function() {
        var contactNumberLength = $("#contact-primary-mobile-too-long").val().length;
        expect(validateContactNumberLength(contactNumberLength)).toBeFalsy();
    });

    it("has a contact number text field which is disabled when it has exactly 15 characters", function() {
        var contactNumberLength = $("#contact-primary-mobile-fifteen-characters").val().length;
        expect(validateContactNumberLength(contactNumberLength)).toBeFalsy();
    });

    afterEach(function() {
        $("#contact-primary-mobile-valid, #contact-primary-mobile-too-long, #contact-primary-mobile-fifteen-characters").remove();
    });

});
