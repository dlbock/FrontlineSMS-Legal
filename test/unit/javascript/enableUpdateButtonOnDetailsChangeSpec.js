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

    afterEach(function(){
        $('body#fixtures > *').not(".jasmine_reporter").not('script').remove();
    });
});

