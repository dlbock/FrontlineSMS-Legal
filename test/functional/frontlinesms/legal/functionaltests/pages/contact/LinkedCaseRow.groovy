package frontlinesms.legal.functionaltests.pages.contact

import geb.Module

class LinkedCaseRow extends Module {
    static content = {
        cell { i -> $("td", i) }
        caseId { cell(0).text() }
        relationship { cell(1).text() }
        unlink { $("a", class: "unlink-case") }
    }
}