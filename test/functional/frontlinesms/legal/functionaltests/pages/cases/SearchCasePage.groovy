package frontlinesms.legal.functionaltests.pages.cases

import geb.Page
import geb.Module
import frontlinesms.legal.functionaltests.pages.ConfirmationDialog

class SearchCasePage extends Page {
    static url = "case/search"

    static content = {
        id { $("input", id: "caseId") }
        search { $("input", id: "case-search") }
        errorText {$("div", id: "errorMessage").text() }
        searchResults {
            $("tbody tr").collect {module CaseRow, it}
        }
        deleteDialog { $("div", id: "deleteDialog") }
        deleteYes { $("button", id: "confirm-yes")}
        deleteNo { $("button", id: "confirm-no")}
    }
}

class CaseRow extends Module {
    static content = {
        cell { i -> $("td", i) }
        caseId { cell(0).text() }
        active { cell(1).text() }
        deleteButton { $("button", class: "deleteButtons")}
    }
}


