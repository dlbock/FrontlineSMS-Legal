package frontlinesms.legal.contacts

import frontlinesms.legal.functionaltests.FrontlinesmsLegalGebSpec
import frontlinesms.legal.functionaltests.pages.HomePage
import frontlinesms.legal.functionaltests.pages.contact.CreateLegalContactPage
import frontlinesms.legal.functionaltests.pages.contact.ShowLegalContactPage
import frontlinesms.legal.Case

class CreateLegalContactSpec extends FrontlinesmsLegalGebSpec {

    def "should save contact and redirect to show page when user chooses to save contact without name"() {
        given:
        to HomePage
        createNewContact.click()

        when:
        at CreateLegalContactPage
        name = ""
        primaryMobile = "8675309"

        and:
        save.click()

        and:
        saveWithoutNameYes.click()

        then:
        assert at(ShowLegalContactPage)
        primaryMobile == "8675309"
    }

    def 'should go to home page when yes is clicked on cancel creation of contact when some fields are filled in'() {
        given:
        to CreateLegalContactPage

        when:
        name = "Bob"
        cancel.click()

        and:
        cancelYes.click()

        then:
        assert at(HomePage)
    }

    def "should see link case button on the create legal contact page"() {
        when:
        to CreateLegalContactPage

        then:
        oneLinkCaseButtonExists
    }

    def "should show link case search window with search box and list of cases with case title when link case button is clicked"() {
        given:
        new Case(caseId: "123", description: "test", caseTitle:"Case 1").save(flush: true)
        new Case(caseId: "321", description: "test2", caseTitle:"Case 2").save(flush: true)

        when:
        to CreateLegalContactPage
        linkCaseButton.click()

        then:
        linkCaseDialog.present
        linkCaseDialog.casesToLink.collect { it -> it.linkCase }.size() == 2
        linkCaseDialog.casesToLink[0].caseTitle == "Case 1"
        linkCaseDialog.casesToLink[1].caseTitle == "Case 2"
        linkCaseDialog.caseIdSearch.present
        linkCaseDialog.linkCaseCancelButton.displayed
    }

    def "should show relationship description dialog when link case button is clicked for a desired case"() {
        given:
        new Case(caseId: "123", description: "test", caseTitle: "Case 1").save(flush: true)

        when:
        to CreateLegalContactPage
        linkCaseButton.click()
        linkCaseDialog.link("Case 1")

        then:
        caseContactRelationshipDialog.present
        relationshipInput.present
        relationshipConfirmButton.present
        relationshipCancelButton.present

    }

    def "should return to create contact page with CASE ID, Case Title and the Relationship of the linked case along with an Unlink link for that case"() {
        given:
        new Case(caseId: "123", caseTitle: "Case Title 1", description: "test").save(flush: true)
        new Case(caseId: "321", caseTitle: "Case Title 2",description: "test2").save(flush: true)

        when:
        to CreateLegalContactPage
        linkCaseButton.click()
        linkCaseDialog.link("Case Title 1")
        relationshipInput << "Victim"
        relationshipConfirmButton.click()
        linkCaseButton.click()
        linkCaseDialog.link("Case Title 2")
        relationshipInput << "Victim"
        relationshipConfirmButton.click()

        then:
        at(CreateLegalContactPage)
        linkedCasesTable[0].caseId == "123"
        linkedCasesTable[0].relationship == "Victim"
        linkedCasesTable[0].caseTitle == "Case Title 1"
        linkedCasesTable[0].unlink.displayed
        linkedCasesTable[1].caseId == "321"
        linkedCasesTable[1].relationship == "Victim"
        linkedCasesTable[1].caseTitle == "Case Title 2"
        linkedCasesTable[1].unlink.displayed

    }

    def "should retain search results after cancelling the relationship dialog"() {
        given:
        new Case(caseId: "123", description: "test", caseTitle: "Case Title 1").save(flush: true)

        when:
        to CreateLegalContactPage
        linkCaseButton.click()
        linkCaseDialog.searchFor("123")
        linkCaseDialog.link("Case Title 1")
        relationshipCancelButton.click()

        then:
        linkCaseDialog.displayed
        linkCaseDialog.caseIdSearch.value() == "123"
        !caseContactRelationshipDialog.displayed
    }

    def "should go to Contacts details page and the linked cases be displayed, when the SAVE button is clicked from the Create Contact page"() {
        given:
        new Case(caseId: "123", description: "test", caseTitle: "Case Title 1").save(flush: true)
        to CreateLegalContactPage

        when:
        name << "Bob"
        primaryMobile << "8675309"
        notes << "Testing"
        linkCaseButton.click()
        linkCaseDialog.searchFor("123")
        linkCaseDialog.link("Case Title 1")
        relationshipInput << "Victim"
        relationshipConfirmButton.click()
        save.click()

        then:
        assert at(ShowLegalContactPage)
        name == "Bob"
        primaryMobile == "8675309"
        notes.text() == "Testing"
        linkedCasesTable[0].caseId == "123"
        linkedCasesTable[0].relationship == "Victim"
        linkedCasesTable[0].unlink.displayed
    }

    def "should not link a case that is already linked to the contact and should stay on the link case pop-up"(){
        given:
        new Case(caseId: "123", caseTitle: "Case Title 1", description: "test").save(flush: true)

        when:
        to CreateLegalContactPage
        name << "Bob"
        primaryMobile << "8675309"
        notes << "Testing"
        linkCaseButton.click()
        linkCaseDialog.link("Case Title 1")
        relationshipInput << "Victim"
        relationshipConfirmButton.click()
        linkCaseButton.click()
        linkCaseDialog.link("Case Title 1")

        then:
        linkCaseDialog.present
    }
}
