describe("form validation", function() {
    beforeEach(function() {
        var tempHTML =
                '<input id="contact-primary-mobile" type="text" value="8675309FFF+!*-%$#"/>';
        $(tempHTML).appendTo("#fixtures");
        frontlinesms.validateContactNumber();
    });

    it("accept only number & some special character if the user pastes any string containing alphanumeric character ", function() {
        $("#contact-primary-mobile").val("hello123+*-#$/")
        $("#contact-primary-mobile").keyup();
        expect($("#contact-primary-mobile").val()).toEqual("123+*-#");
    });

    afterEach(function() {
        $("#contact-primary-mobile").remove();
    });

});

