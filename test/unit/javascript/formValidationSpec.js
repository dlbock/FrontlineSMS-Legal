describe("Validate forms", function() {
    beforeEach(function() {
        var tempHTML =
            '<input id="contact-primary-mobile-legal-valid" type="text" value="123456789012345"/>' +
                '<input id="contact-primary-mobile-legal-characters" type="text"/>';

        $(tempHTML).appendTo("#fixtures");
        frontlinesms.validateContactNumber();
    });

    it("has a contact number field whose maximum acceptable length is 15", function(){

    });


//    it("has a legal character input", function() {
//        var element = $("#contact-primary-mobile-legal-characters");
//
//        var action = jQuery.Event("keypress");
//        action.which = 48;
//        element.trigger(action);
//
//        expect(element.val()).toEqual("0");
//    });

    afterEach(function() {
        $("#contact-primary-mobile-legal-characters").remove();
    });

});
