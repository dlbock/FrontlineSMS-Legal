package frontlinesms.legal.functionaltests.pages

import geb.Module

class LinkContactDialog extends Module {
    static base = { $(id: "link-contact-dialog") }
    static content = {
        searchbox { $(id: "contact-name-search") }
        contacts { $("#contactsTable tbody tr").collect {module LinkContactRow, it} }
        contactsNotInSearchResults { $("tr", class: "contactRow", filtermatch: "false").collect {module LinkContactRow, it} }

        searchFor { query ->
            searchbox.value(query)
            sleep(500)
            true
        }

        link { name ->
            contacts.find{ it.contactName == name }.link()
            true
        }

        cancel {
            page.$("#cancel-button").click()
            true
        }
    }
}

class LinkContactRow extends Module {
    static content = {
        cell { i -> $("td", i) }
        contactName { cell(0).text() }
        contactNumber { cell(1).text() }

        link {
            $("a").find { it.text() == "Link" }.click()
            true
        }
    }
}
