package frontlinesms.legal.functionaltests.pages.events

import geb.Page
import geb.Module

class NewEventPage extends Page {
    static at = { $("title").text() == "Create New Event" }
    static url = "event/create"
    static content = {
        eventTitle { $("input", id: "event-title") }
        dateFieldSelected {
            $("input", id: "event-date").click()
            waitFor(3) { datePicker.present }
        }
        datePicker { $("div", id: "ui-datepicker-div")}
        save { $("input", id: "event-save") }
        cancel { $("button", id: "event-cancel") }
        errorMessage { $("div", id: "errorMessage").text() }
        eventCancelDialog { $("div", id: "event-cancel-dialog")}
        cancelYes { $("button", id: "cancel-confirm-yes")}
        cancelNo { $("button", id: "cancel-confirm-no")}
        startTimeField { $("input", id: "event-start-time")}
        endTimeField { $("input", id: "event-end-time")}
        clickLinkContact {$("#link-contact-button")}
        contactsToLink {
            try {
                $("tr[class='contactLink']")
            }
            catch (Exception e) {
                null
            }
        }
        linkContactDialog { $("div", id: "link-contacts") }
        contactNameSearch { $("input", id: "contact-name-search") }
        contactLinkNotVisible { $("tr", class: "contactLink", filtermatch: "false").collect {module ContactRow, it} }

    }
}

class ContactRow extends Module {
    static content = {
        cell { i -> $("td", i) }
        name { cell(0).text() }
        primaryMobile { cell(1).text() }
    }
}
