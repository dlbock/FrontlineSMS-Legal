var frontlinesms = this.frontlinesms || {};

frontlinesms.validateContactNumber = function() {
    frontlinesms.log("validate contact number");
    var validCharacters = ['0','1','2','3','4','5','6','7','8','9','#','+','-','*'];
    $("#contact-primary-mobile").keypress(function(key) {
            frontlinesms.log("Received keypress with charCode: " + key.charCode);
            var characterPressed = String.fromCharCode(key.charCode);

            frontlinesms.log("character="+characterPressed);

            var i;
            for (i = 0; i < validCharacters.length; i++) {
                if (characterPressed == validCharacters[i]) {
                    frontlinesms.log("valid character");
                    return true;
                }
            }
            return false;
        }
    );
};
