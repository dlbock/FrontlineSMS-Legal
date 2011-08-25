describe("Validate forms", function() {
    beforeEach(function() {
        var tempHTML =
                '<input id="contact-primary-mobile" type="text"/>';

        $(tempHTML).appendTo("#fixtures");
        frontlinesms.validateContactNumber();
    });

//    it("has a legal character input", function() {
//        var element = $("#contact-primary-mobile");
//
//        var action = jQuery.Event("keypress");
//        action.which = 48;
//        action.charCode = 48;
//        element.trigger(action);
//       // expect(String.fromCharCode(element.charCode))=="0";
//      expect(element.value())=="0";
//    });

    afterEach(function() {
        $("#contact-primary-mobile").remove();
    });

});
