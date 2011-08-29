package frontlinesms.legal.functionaltests.pages.contact

import geb.Page
import geb.Module

class SearchLegalContactPage extends Page {
    static url = "legalContact/search"

    static content = {
      searchResults {
            $("tbody tr").collect {module LegalContactRow, it}
        }
        deleteDialog { $("div", id: "deleteDialog") }
        deleteYes { $("button", id: "confirm-yes")}
        deleteNo { $("button", id: "confirm-no")}
        contactNameSearch { $("input", id: "contact-search-bar") }
        contactLinkNotVisible { $("tr", class: "contactLink", filtermatch: "false").collect {module LegalContactRow, it} }
    }


}



class LegalContactRow extends Module {
    static content = {
        cell { i -> $("td", i) }
        name { cell(0).text() }
        primaryMobile { cell(1).text() }
        deleteButton { $("button", class: "deleteButtons")}
    }

}