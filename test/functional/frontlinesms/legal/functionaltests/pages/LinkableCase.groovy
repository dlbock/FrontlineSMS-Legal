package frontlinesms.legal.functionaltests.pages

import geb.Module

class LinkableCase extends Module {
    static content = {
        cell { i -> $("td", i) }
        caseId { $(class: "case-name").text() }
        caseTitle { $(class: "case-title").text() }
        status { $(class: "case-status").text() }
        linkCase { $("a", class: "link-case") }
    }
}