package frontlinesms.legal.functionaltests.pages

import geb.Module

class ConfirmationDialog extends Module {
    def messageId
    def buttonIdPrefix = ""

    static base = { $(class: "ui-dialog") }

    static content = {
        message { $("div", id: "caseDeleteDialog") }
        yesButton { $("button", id: buttonIdPrefix + "confirm-yes") }
        noButton { $("button", id: buttonIdPrefix + "confirm-no") }
    }

    def confirm() {
        yesButton.click();
        waitFor { try { !this.isVisible() } catch(e) { true } }
    }
}