package frontlinesms.legal.caching

import frontlinesms.legal.functionaltests.FrontlinesmsLegalGebSpec
import frontlinesms.legal.functionaltests.pages.cases.SearchCasePage
import org.apache.http.client.methods.HttpGet
import org.apache.http.impl.client.DefaultHttpClient

class CacheSpec extends FrontlinesmsLegalGebSpec {

    def "controller responses are uncacheable"() {
        given:
        to SearchCasePage
        def httpClient = new DefaultHttpClient()

        when:
        def httpResponse = httpClient.execute(new HttpGet("http://localhost:8080/case/search"))

        then:
        httpResponse.getHeaders("Cache-Control").collect{ it.value } == ["no-cache", "no-store"]
        httpResponse.getHeaders("Expires").collect { it.value } == ["Thu, 01 Jan 1970 00:00:00 GMT"]
    }
}
