package frontlinesms.legal.functionaltests.pages.contact

import geb.Module

class CaseRow extends Module {
    static content = {
        cell { i -> $("td", i) }
        caseId { cell(0).text().present }
        caseTitle { cell(1).text() }
        status { cell(2).text() }
        linkCaseButton { $("a", class: "link-case") }
    }
}
