package frontlinesms.legal

import grails.plugin.spock.ControllerSpec

class FrontlinesmsLegalControllerSpecBase extends ControllerSpec {

    def mockLegalContactToAvoidIssuesWithContactBeforeUpdateEvent() {
        registerMetaClass(LegalContact)
        LegalContact.metaClass.getOldContactNumber = { "old and frail" }
        LegalContact.metaClass.updateContactNames = { name, number -> }
    }

}
