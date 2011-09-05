var frontlinesms = this.frontlinesms || {};

frontlinesms.validateContactNumber = function() {
    var validCharacters = ['0','1','2','3','4','5','6','7','8','9','#','+','-','*'];
    var keyCodesForAllowedKeys = [0, 8, 9];
    $("#contact-primary-mobile").keypress(function(key) {
            frontlinesms.log(key);
            var characterPressed = String.fromCharCode(key.which);
            var i;
            for (i = 0; i < keyCodesForAllowedKeys.length; i++) {
                if(key.which == keyCodesForAllowedKeys[i]) {
                    return true;
                }
            }

            for (i = 0; i < validCharacters.length; i++) {
                if (characterPressed == validCharacters[i]) {
                    return true;
                }
            }
            return false;
        }
    );
};

frontlinesms.blockKeyPressInDateField = function() {

    var keyCodeForTabKey = 9;
    $("#event-date").keypress(function(key) {
            /*if(key.keyCode == keyCodeForTabKey)
            {
                return true;
            }*/
            return false;
        }
    );
};
