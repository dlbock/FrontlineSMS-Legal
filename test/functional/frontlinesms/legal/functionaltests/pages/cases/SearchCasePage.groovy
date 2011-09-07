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
        NumberOfCases {
            $("#SearchResults tbody tr")
            true
        }
        caseLinkNotVisible { $("tr", class: "caseLink", filtermatch: "false").collect {module CaseRow, it} }
        caseLinkVisible { $("tr", class: "caseLink", filtermatch: "true").collect {module CaseRow, it} }
        goToSearchPage{
            $("a", name: "searchByCaseId")
        }
        goToContactSearchPage{
            $("a", name: "contactSearch")
        }

    }
}

class CaseRow extends Module {
    static content = {
        cell { i -> $("td", i) }
        caseId { cell(0).text() }
        caseTitle {cell(1).text()}
        active { cell(2).text() }
        deleteButton { $("button", class: "deleteButtons")}
    }
}


