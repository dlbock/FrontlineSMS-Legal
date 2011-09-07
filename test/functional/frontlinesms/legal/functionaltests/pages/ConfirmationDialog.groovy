package frontlinesms.legal.functionaltests.pages

import geb.Module

class ConfirmationDialog extends Module {
    def messageId

    static base = { $(id: messageId).parent(".ui-dialog") }

    static content = {
        message { $(id: messageId) }
        yesButton { $(id: "confirm-yes") }
        noButton { $(id: "confirm-no") }
    }

    def confirm() {
        yesButton.click();
        waitFor { try { !this.isVisible() } catch(e) { true } }
    }
}
