var frontlinesms = this.frontlinesms || {};

frontlinesms.initializeCaseDeletion = function() {

    var deleteCaseAction = function(button) {
        console.log('in delete case action');
        $(button).parent("form").submit();

    }

    frontlinesms.attachActionWithConfirmationToButton("#delete-button", "#caseDeleteDialog", deleteCaseAction)
}


