package frontlinesms.legal.functionaltests.pages

import geb.Module

class LinkCaseDialog extends Module {
    static base = { $(id: "link-case-dialog") }
    static content = {
        casesToLink { $("#casesTable tbody tr").collect {module LinkCaseRow, it} }
        caseIdSearch { $("input", id: "case-search-field") }
        linkCaseCancelButton { page.$("#cancel-link-case") }
        casesNotInSearchResults { $("tr", class: "caseRow", filtermatch: "false").collect {module LinkCaseRow, it} }

        searchFor { query ->
            caseIdSearch.value(query)
            sleep(500)
            true
        }

        link { title ->
            casesToLink.find { it.caseTitle == title }.link()
            true
        }
    }
}

class LinkCaseRow extends Module {
    static content = {
        cell { i -> $("td", i) }
        caseId { cell(0).text().present }
        caseTitle { cell(1).text() }
        status { cell(2).text() }
        linkCase { $("a", class: "link-case") }
        link {
            $("a").find { it.text() == "Link" }.click()
            true
        }
    }
}

