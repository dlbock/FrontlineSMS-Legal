var frontlinesms = this.frontlinesms || {};

frontlinesms.validateContactNumber = function() {
    var validCharacters = ['0','1','2','3','4','5','6','7','8','9','#','+','-','*'];
    var keyCodesForAllowedKeys = [8, 9, 37, 38, 39, 40, 46];
    $("#contact-primary-mobile").keypress(function(key) {
            var characterPressed = String.fromCharCode(key.charCode);
            var i;
            for (i = 0; i < keyCodesForAllowedKeys.length; i++) {
                if(key.keyCode == keyCodesForAllowedKeys[i]) {
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
