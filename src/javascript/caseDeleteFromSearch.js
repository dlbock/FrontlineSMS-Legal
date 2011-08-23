var frontlinesms = this.frontlinesms || {};

frontlinesms.initializeCaseDeletion = function() {

    var deleteCaseAction = function(button) {
        $(button).parent("form").submit();
    }

    frontlinesms.attachActionWithConfirmationToButton(".deleteButtons", "#caseDeleteDialog", deleteCaseAction)
}

