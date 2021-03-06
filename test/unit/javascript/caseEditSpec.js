describe('caseEditCancelDialog', function () {
    beforeEach(function() {
        var tempHTML =
            '<div id="test-div">' +
                '<input id="case-id" type="text" value="" change/>' +
                '<textarea id="case-description" type="text" value=""/>' +
                '<button id="case-update-cancel">Cancel</button>' +
                '<div id="case-update-cancel-dialog" title="Cancel Changes?" style="display: none;">' +
                '<p>Are you sure you do not want to save this case?</p>' +
                '</div>' +
                '</div>';
        $(tempHTML).appendTo("#fixtures");
        frontlinesms.showCaseOnLoad();
        spyOn(frontlinesms, "goHome").andReturn("#");
    });


    it('case cancel dialog should not appear if no changes have been made and cancel button is clicked', function () {
        $("#case-update-cancel").click();
        expect($('#case-update-cancel-dialog:visible').size()).toEqual(0);
    });

    it('case cancel dialog should appear if changes are made and cancel button is clicked', function() {
        $('#case-id').change();
        $('#case-update-cancel').click();
        expect($('#case-update-cancel-dialog:visible').size()).toEqual(1);
    })

    afterEach(function() {
        $('body#fixtures > *').not(".jasmine_reporter").not('script').remove()
    })


});

describe('submit button', function() {
    beforeEach(function() {
        var tempHTML =
            '<div id="test-div">' +
                '<input id="case-id" type="text" value="" />' +
                '<table id="contactsTable">' +
                '<tbody>' +

                '<tr class="contactRow" id="5">' +
                '<td  class="contact-name"> <a href="#" >fabio</a> </td>' +
                '<td class="contact-number"> <a href="#">99999</a> </td>' +
                '<td class="contactLink" id="5">' +
                '<a href="#">Link</a>' +
                '</td>' +
                '</tr>' +
                ' </tbody>' +
                '</table> ' +

                '<table name="contacts" id="contacts">' +
                '<td class="unlink-contact-button">' +
                '</td>' +
                '</table>' +

                '<input id="case-update" disabled="disabled">Update</input>' +
                '</div>';
        $(tempHTML).appendTo("#fixtures");
        frontlinesms.showCaseOnLoad();
    });

    it('should be enabled if changes have been to the id', function() {
        $('#case-id').change();
        expect($('#case-update').attr('disabled')).toBeUndefined();
    })

    it('should be enabled if contacts have been linked', function() {
        $('.contactLink').click();
        expect($('#case-update').attr('disabled')).toBeUndefined();
    })

    it('should be enabled if contacts have been removed', function() {
        $('.unlink-contact-button').click();
        expect($('#case-update').attr('disabled')).toBeUndefined();
    })

    afterEach(function() {
        $('body#fixtures > *').not(".jasmine_reporter").not('script').remove()
    })

});