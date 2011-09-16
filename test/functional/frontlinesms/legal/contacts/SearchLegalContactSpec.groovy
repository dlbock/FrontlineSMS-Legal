package frontlinesms.legal.contacts

import frontlinesms.legal.functionaltests.FrontlinesmsLegalGebSpec
import frontlinesms.legal.functionaltests.pages.HomePage
import frontlinesms.legal.functionaltests.pages.contact.SearchLegalContactPage
import frontlinesms.legal.LegalContact

class SearchLegalContactSpec extends FrontlinesmsLegalGebSpec {

    def 'should navigate to search legal contact page and list of contacts is displayed when search contact link is clicked at the home page'() {
        given:
        def newContact = new LegalContact(name: 'Me', primaryMobile: '98765')
        newContact.save()

        and:
        to HomePage

        when:
        searchContactLink.click()

        then:
        at SearchLegalContactPage

        and:
        searchResults.collect {it->it.name}.contains('Me')
        searchResults.collect {it->it.primaryMobile}.contains('98765')
    }

     def "should display only the filtered contacts on pressing RETURN in the contact search bar"() {
        setup:
        new LegalContact(name: "fabio", primaryMobile: "22222").save(flush: true)
        new LegalContact(name: "dev", primaryMobile: "55555").save(flush: true)

        and:
        to HomePage

        when:
        searchContactLink.click()

        then:
        at SearchLegalContactPage

        and:
        contactNameSearch.value("fab\r")
        sleep(500)

        then:
        contactLinkNotVisible().size() == 1
    }

    def "should not filter by DELETE button value"() {
        setup:
        new LegalContact(name: "fabio", primaryMobile: "22222").save(flush: true)
        new LegalContact(name: "dev", primaryMobile: "55555").save(flush: true)

        when:
        to SearchLegalContactPage

        and:
        contactNameSearch.value("Delete")
        sleep(500)

        then:
        contactLinkNotVisible().size() == 2
    }
}