package frontlinesms.legal

import grails.plugin.spock.ControllerSpec
import frontlinesms2.GroupMembership

class FrontlinesmsLegalControllerSpecBase extends ControllerSpec {

    def mockCoreClassesToAvoidIssuesWithContactEventHandlers() {
        registerMetaClass(LegalContact)
        LegalContact.metaClass.getOldContactNumber = { "old and frail" }
        LegalContact.metaClass.updateContactNames = { name, number -> }

        registerMetaClass(GroupMembership)
        GroupMembership.metaClass.static.executeUpdate = { query, args -> }
    }

}
