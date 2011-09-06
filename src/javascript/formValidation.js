var frontlinesms = this.frontlinesms || {};

frontlinesms.validateContactNumber = function() {
    var validCharacters = ['0','1','2','3','4','5','6','7','8','9','#','+','-','*'];
    var keyCodesForAllowedKeys = [0, 8, 9, 118];
    var lengthOfContactNumber = 0;

    $("#contact-primary-mobile").keyup(function(){
        var validMobileNumber= "";
        var primaryMobileNumber = $("#contact-primary-mobile").val();
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
        $("#contact-primary-mobile").val(validMobileNumber);
    });

    $("#contact-primary-mobile").change(function(){
        var validMobileNumber= "";
        var primaryMobileNumber = $("#contact-primary-mobile").val();
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
        $("#contact-primary-mobile").val(validMobileNumber);
    });

    $("#contact-primary-mobile").keypress(function(key) {
            frontlinesms.log(key);
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