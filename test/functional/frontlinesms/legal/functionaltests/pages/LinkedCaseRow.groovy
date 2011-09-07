package frontlinesms.legal.functionaltests.pages

import geb.Module

class LinkedCaseRow extends Module {
    static content = {
        cell { i -> $("td", i) }
        caseId { cell(0).text() }
        caseTitle {cell(1).text()}
        relationship { cell(2).text() }
        unlink { $("a", class: "unlink-case") }
    }
}