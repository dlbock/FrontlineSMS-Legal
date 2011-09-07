package frontlinesms.legal

class Case {

    static mapping = {
        table 'legal_case'
    }

    static constraints = {
        caseTitle(nullable: true)
        caseId(unique: true, blank: false)
        description(nullable: true)
    }

    static hasMany = [contacts: LegalContact, linkedContacts: CaseContacts, linkedEvents: EventCase]

    String caseId
    String caseTitle
    String description
    boolean active = true
}
