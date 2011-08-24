package frontlinesms.legal.functionaltests.pages.schedule

import geb.Page
import geb.Module

class SchedulePage extends Page {
    static at = { $("title").text() == "Schedule" }
    static url = "schedule"
    static content = {
        events {
            try {
                $("span[class='fc-event-title']")
            }
            catch (Exception e) {
                null
            }
        }
        testEvent { $("span[class='fc-event-title']", text: "test event") }
        eventContacts {
            try {
                $("tr[class='event-contact']")

            }
            catch (Exception e) {
                null
            }
        }
        eventListSize {
            try {
                $("span[class='fc-event-title']").size()

            }
            catch (Exception e) {
                0
            }
        }
        deleteEvent {
            $('#delete-event').click()
            $('#cancel-confirm-yes').click()
            true
        }
        
        contactsLinkedToEvent {
            $(".event-contact").collect {module ContactRow, it}
        }
        eventTitle { $('#event-title').text()}
        eventDate { $('#event-date').text()}
        eventStartTime { $('#event-start-time').text()}
        eventEndTime { $('#event-end-time').text()}
        atDate{$('span.fc-header-title').text()}
        contactUnlinkDialog { $("div", id: "contactUnlinkDialog") }
        contactUnlinkYes { $("button", id: "confirm-yes")}
        contactUnlinkNo { $("button", id: "confirm-no")}

    }
}

class ContactRow extends Module {
    static content = {
        cell { i -> $("td", i) }
        name { cell(0).text() }
        primaryMobileNumber { cell(1).text() }
        unlinkLink { $("a", class: "unlink-contact-link")}
    }
}
