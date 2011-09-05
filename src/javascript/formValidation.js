var frontlinesms = this.frontlinesms || {};

frontlinesms.validateContactNumber = function() {
    var validCharacters = ['0','1','2','3','4','5','6','7','8','9','#','+','-','*'];
    var keyCodesForAllowedKeys = [0, 8, 9];


    $("#contact-primary-mobile").change(function(){
        var validMobileNumber= "";
        var primaryMobileNumber = $(this).val();
           for(var i=0; i < primaryMobileNumber.length ; i++)
           {
               for(var j=0 ; j < validCharacters.length ; j++){
                   if(primaryMobileNumber[i] == validCharacters[j]){
                       validMobileNumber = validMobileNumber + primaryMobileNumber[i];
                   }
               }
               for(var k=0 ; k < keyCodesForAllowedKeys.length ; k++){
                   if(primaryMobileNumber[i] == keyCodesForAllowedKeys[k]){
                       validMobileNumber = validMobileNumber + primaryMobileNumber[i];
                   }
               }
           }
        $("#contact-primary-mobile").val(validMobileNumber);
    });

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
           return false;
    }
    );
};
