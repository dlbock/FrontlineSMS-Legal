package frontlinesms.legal.functionaltests

import geb.spock.GebSpec
import geb.Browser

class FrontlinesmsLegalGebSpec extends GebSpec {
    Browser createBrowser() {
        Browser browser = new Browser()
        browser.baseUrl = getBaseUrl()
        browser
    }

    protected GString getBaseUrl() {
        return "http://localhost:${getPort()}/"
    }

    String getPort() {
        System.getProperty("server.port", "8080")
    }

    protected Boolean elementInFocusIs(element) {
        browser.driver.switchTo().activeElement() == element.firstElement()
    }
}

