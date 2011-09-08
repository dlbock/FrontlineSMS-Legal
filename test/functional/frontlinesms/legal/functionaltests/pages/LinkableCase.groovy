package frontlinesms.legal.functionaltests.pages

import geb.Module

/**
 * Created by IntelliJ IDEA.
 * User: pratima
 * Date: 8/9/11
 * Time: 12:02 PM
 * To change this template use File | Settings | File Templates.
 */
class LinkableCase extends Module {
    static content = {
        cell { i -> $("td", i) }
        caseId { $(class: "case-name").text() }
        caseTitle { $(class: "case-title").text() }
        status { $(class: "case-status").text() }
        linkCase { $("a", class: "link-case") }
    }
}
