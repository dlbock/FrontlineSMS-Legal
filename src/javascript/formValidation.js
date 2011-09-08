var frontlinesms = this.frontlinesms || {};

frontlinesms.validateContactNumber = function() {
    var validCharacters = ['0','1','2','3','4','5','6','7','8','9','#','+','-','*'];
    var keyCodesForAllowedKeys = [0, 8, 9, 118];
    var lengthOfContactNumber = 0;

    $("#contact-primary-mobile").keyup(function(){
        frontlinesms.filterOutUnwantedCharacters("#contact-primary-mobile", validCharacters, keyCodesForAllowedKeys);
    });

    $("#contact-primary-mobile").change(function(){
        frontlinesms.filterOutUnwantedCharacters("#contact-primary-mobile", validCharacters, keyCodesForAllowedKeys);
    });

    $("#contact-primary-mobile").keypress(function(key) {
            var keypressed = key? key.which : window.event.keyCode;
            var characterPressed = String.fromCharCode(keypressed);
            lengthOfContactNumber++;
            if(lengthOfContactNumber < 26 || keypressed!=8 || keypressed!=0 || keypressed!=9)
            {
            var i;
            for (i = 0; i < keyCodesForAllowedKeys.length; i++) {
                if(keypressed == keyCodesForAllowedKeys[i]) {
                    return true;
                }
            }
            for (i = 0; i < validCharacters.length; i++) {
                if (characterPressed == validCharacters[i]) {
                    return true;
                }
            }
            }
            return false;
        }
    );

};

frontlinesms.blockKeyPressInDateField = function() {
    var keyCodeForTabKey = 9;
    $("#event-date").keydown(function(key) {
            if (key.keyCode == keyCodeForTabKey) {
                return true;
            }
            return false;
        }
    );
};

frontlinesms.filterOutUnwantedCharacters = function(fieldId, validCharacters, keyCodesForAllowedKeys) {
     var validMobileNumber= "";
        var primaryMobileNumber = $(fieldId).val();
        for(var i=0; i < primaryMobileNumber.length ; i++)
           {
               var key = primaryMobileNumber[i].which;
               for(var j=0 ; j < validCharacters.length ; j++){
                   if(primaryMobileNumber[i] == validCharacters[j]){
                       validMobileNumber = validMobileNumber + primaryMobileNumber[i];
                   }
               }
               for(var k=0 ; k < keyCodesForAllowedKeys.length ; k++){
                   if(key == keyCodesForAllowedKeys[k]){
                       validMobileNumber = validMobileNumber + primaryMobileNumber[i];
                   }
               }
           }
        $(fieldId).val(validMobileNumber);
};

