describe('enableUpdateButtonOnDetailsChange', function () {
    beforeEach(function() {
        var tempHTML = '<div>' +
            '<textarea id="text-description" type="text" value=""/>' +
            '<button id="update" value="Update" disabled="disabled"/>' +
            '</div>';

        $(tempHTML).appendTo("#fixtures");

    });

    it('should be disabled when text box data is not changed', function () {
        frontlinesms.enableUpdateButtonOnDetailsChange("#text-description","#update");
        expect($("#update").attr("disabled")).toEqual("disabled");
    });


//    it('should be enabled when text box data is changed', function () {
//
//        frontlinesms.enableUpdateButtonOnDetailsChangeForKeyDown("#text-description","#update");
//        expect($("#update").attr("disabled")).toBeUndefined();
//    });


    afterEach(function(){
        $('body#fixtures > *').not(".jasmine_reporter").not('script').remove();
    });
});

//    frontlinesms.enableUpdateButtonOnDetailsChangeForKeyDown = function(textboxId, buttonId){
//
//        var press = jQuery.Event("keypress");
//        press.which = 40;
//        $(textboxId).trigger(press);
//        alert($(textboxId).val());


//        var keyup = $.Event('keypress');
//        alert($.ui.keyCode.Z);
//        var content = $(textboxId).val();
//        $(textboxId).focus();
//        $(textboxId).trigger(keyup);
//        alert($(textboxId).val());
//
//        $(textboxId).keypress(function(){
//            alert($(textboxId).val());
//
//            if ($(textboxId).val() != content){
//                $(buttonId).removeAttr("disabled");
//            }
//        });

//    }
//});

