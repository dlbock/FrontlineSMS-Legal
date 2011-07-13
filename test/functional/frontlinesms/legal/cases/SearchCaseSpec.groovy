package frontlinesms.legal.cases

import frontlinesms.legal.functionaltests.FrontlinesmsLegalGebSpec
import frontlinesms.legal.functionaltests.pages.cases.SearchCasePage
import frontlinesms.legal.functionaltests.pages.cases.NewCasePage
import frontlinesms.legal.Case

class SearchCaseSpec extends FrontlinesmsLegalGebSpec {

    def "should be able to search case with caseId"() {
        given:
        to NewCasePage

        and:
        caseId = "9999"
        description = "whatever"

        and:
        save.click()

        and:
        to SearchCasePage

        when:
        caseId = "9999"

        and:
        search.click()

        then:
        at SearchCasePage
        SearchResults.collect { it -> it.caseId }.contains("9999")


    }


    def "should return all the cases stored in the database when an empty search is carried out"() {

        given:
        to NewCasePage

        and:
        caseId = "14"
        description = "someCase"

        and:
        save.click()

        and:
        to NewCasePage

        and:

        caseId = "5678"
        description = "anotherCase"

        and:
        save.click()

        and:
        to SearchCasePage

        when:
        caseId = ""

        and:
        search.click()

        then:
        SearchResults.collect { it -> it.caseId }.contains("5678")
        SearchResults.collect { it -> it.caseId }.contains("14")
    }

    def 'should display an error message if no cases are found matching a given search criteria'() {

        given:
        to SearchCasePage

        when:
        caseId = "anIncorrectId"

        and:
        search.click()

        then:

        errorText == "There were no results returned for your search. Please try again"


    }

    def 'should display an error message when the case Id specified to search is in the incorrect case'() {
        given:
        to NewCasePage

        and:
        caseId = "abc"

        and:
        save.click()

        when:
        to SearchCasePage

        and:
        caseId = "ABC"

        and:
        search.click()

        then:
        errorText == "There were no results returned for your search. Please try again"
    }

    def 'should return a list of case Ids that partially match the given search criteria'() {

        given:
        to NewCasePage

        and:
        caseId = "case-Id"

        and:
        save.click()

        when:
        to SearchCasePage

        and:
        caseId = "case"

        and:
        search.click()

        then:
        def results = SearchResults.collect { it -> it.caseId }
        results.size() == 1
        results.contains("case-Id")

    }

    def 'should return a list of all the cases in the database if the search box contains just spaces'(){
        given:
        to NewCasePage

        and:
        caseId = "0123"

        and:
        save.click()

        and:
        to NewCasePage

        and:
        caseId = "0123456"

        and:
        save.click()

        when:
        to SearchCasePage

        and:
        caseId = "  "

        and:
        search.click()

        then:
        def results = SearchResults.collect { it -> it.caseId }
        results.contains("0123")
        results.contains("0123456")





    }


}