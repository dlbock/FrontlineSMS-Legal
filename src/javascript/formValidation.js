var frontlinesms = this.frontlinesms || {};

frontlinesms.validateContactNumber = function() {
    var validCharacters = ['0','1','2','3','4','5','6','7','8','9','#','+','-','*'];
    $("#contact-primary-mobile").keypress(function(key) {
            var characterPressed = String.fromCharCode(key.charCode);
            var mockInput = $("#contact-primary-mobile-mock");

            var i;
            var isValidCharacter = false;
            for (i = 0; i < 14; i++) {
                if (characterPressed == validCharacters[i]) {
                    isValidCharacter = true;
                    mockInput.attr("value", mockInput.attr("value") + characterPressed);
                    break;
                }
            }

            if (!isValidCharacter) {
                return false;
            }
        }
    )

    $("#contact-primary-mobile").keyup(function(key) {
            if (key.keyCode == 8) {
                var mockInput = $("#contact-primary-mobile-mock");

                mockInput.attr("value",mockInput.attr("value").slice(0,mockInput.attr("value").length-1));
                console.log("delete");
            }
        }
    )
};
