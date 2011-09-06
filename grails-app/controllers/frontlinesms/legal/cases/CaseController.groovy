package frontlinesms.legal.cases

import frontlinesms.legal.Case
import frontlinesms.legal.CaseContacts
import frontlinesms.legal.LegalContact
import grails.converters.JSON
import frontlinesms.legal.EventCase

class CaseController {

    def create = {
        [contactList: LegalContact.list()]
    }

    def save = {
        def newCase = new Case(params)

        if (newCase.save(flush: true)) {
            saveLinkedContacts(newCase)
            flash.message = "Case created"
            redirect(action: 'show', params: [id: newCase.caseId])
        }
        else if (params.caseId == null || params.caseId == "" || params.caseId.isAllWhitespace()) {
            flash.error = "Case ID is required"
            redirect(action: 'create', params: [description: params.description])
        }
        else {
            flash.error = "Case ID already exists. Please enter a unique Case ID."
            def enteredDescription = params.description
            redirect(action: 'create', params: [description: params.description])
        }
    }

    private def saveLinkedContacts(newCase) {
        def parsedJSON = params.caseLinkedContacts ? params.caseLinkedContacts : "{}"
        def contactList = new HashMap<Long, String>(JSON.parse(parsedJSON))

        CaseContacts.findAllByLegalCase(newCase)*.delete(flush: true)
        contactList.each { it ->
            CaseContacts.link(newCase, LegalContact.findById(it.key as Long), it.value)
        }
    }


    def show = {

        def tempCase = Case.findByCaseId(params.id)
        def linkedEvents = tempCase.linkedEvents
        def currentDate = new Date()
        def pastEventList = []
        def futureEventList = []
        def nearestPastEventTime = null
        def nearestFutureEventTime = null
        def ongoingEventList = []


        linkedEvents.each {eventContact ->
            def eventIterator = eventContact.event
            def linkedEventStartTime = new Date(eventIterator.dateFieldSelected.year, eventIterator.dateFieldSelected.month, eventIterator.dateFieldSelected.date, eventIterator.startTimeField.hours, eventIterator.startTimeField.minutes)
            def linkedEventEndTime = new Date(eventIterator.dateFieldSelected.year, eventIterator.dateFieldSelected.month, eventIterator.dateFieldSelected.date, eventIterator.endTimeField.hours, eventIterator.endTimeField.minutes)
            if (linkedEventStartTime.after(currentDate)) {
                if (nearestFutureEventTime == null || linkedEventStartTime.compareTo(nearestFutureEventTime) < 0) {
                    nearestFutureEventTime = linkedEventStartTime
                    futureEventList = [eventIterator]
                }
                else if (linkedEventStartTime.compareTo(nearestFutureEventTime) == 0) {
                    nearestFutureEventTime = linkedEventStartTime
                    futureEventList.add(eventIterator)
                }
            }
            else {
                if (linkedEventEndTime.compareTo(currentDate) > 0) {
                    ongoingEventList.add(eventIterator)
                }
                else if (nearestPastEventTime == null || linkedEventEndTime.compareTo(nearestPastEventTime) > 0) {
                    nearestPastEventTime = linkedEventEndTime
                    pastEventList = [eventIterator]
                }
                else if (linkedEventEndTime == nearestPastEventTime) {
                    nearestPastEventTime = linkedEventEndTime
                    pastEventList.add(eventIterator)
                }
            }
        }
        if (!ongoingEventList.isEmpty()) {
            futureEventList = null
        }
        def caseToDisplay = (params.description) ? Case.get(params.uniqueId) : Case.findByCaseId(params.id)
        def caseLinkedContacts = pairUpContactIdAndRelationship(caseToDisplay.linkedContacts) as JSON
        if (params.description) {
            caseToDisplay.description = params.description
            caseToDisplay.active = params.caseStatus
        }
        [caseToDisplay: caseToDisplay, caseLinkedContacts: caseLinkedContacts.toString() , contactList: LegalContact.list(), linkedContactRowData: CaseContacts.findContactsAndInvolvementByCase(caseToDisplay), pastEvents: pastEventList, ongoingEvents: ongoingEventList, futureEvents: futureEventList]
    }

    private def pairUpContactIdAndRelationship(caseContacts) {
        def returnList = new HashMap<Long, String>()
        caseContacts.each { it ->
            returnList[it.legalContact.id] = it.involvement
        }
        return returnList
    }

    def search = {

        if (params.caseId && !params.caseId.isAllWhitespace()) {
            def foundCases = Case.findAllByCaseIdLike("${params.caseId}%")
            if (foundCases.size() == 0) {
                flash.error = "There were no results returned for your search. Please try again"
                redirect(action: 'search')
            }
            else {
                [foundCase: foundCases]
            }
        }
        else {
            [foundCase: Case.getAll()]
        }
    }

    def update = {
        def fetchedCase = Case.get(params.currentId)
        def originalUniqueId = params.currentId
        def originalCaseId = fetchedCase.caseId
        fetchedCase.caseId = params.caseId
        fetchedCase.description = params.description
        if (params.caseStatus == null) {

            fetchedCase.active = ""
        }
        else {
            fetchedCase.active = "true"
        }
        if (fetchedCase.save(flush: true)) {
            saveLinkedContacts(fetchedCase)
            flash.message = "Case details updated"
            redirect(action: 'show', params: [id: fetchedCase.caseId])
        }
        else if (params.caseId == null || params.caseId == "" || params.caseId.isAllWhitespace()) {
            flash.error = "Case ID required"
            redirect(action: 'show', params: [id: originalCaseId, description: fetchedCase.description, uniqueId: originalUniqueId, caseStatus: params.caseStatus])
        }
        else {
            flash.error = "Case ID already exists. Please enter a unique Case ID."
            redirect(action: 'show', params: [id: originalCaseId, description: fetchedCase.description, uniqueId: originalUniqueId, caseStatus: params.caseStatus])
        }
    }

    def delete = {
        def deleteCase = Case.findByCaseId(params.id);
        if (deleteCase != null) {
            EventCase.unlink(deleteCase)
            sleep(500)
            deleteCase.delete();
            flash.message = "Case deleted."
        }
        else
            flash.warning = "Case not found."
        redirect(action: 'search')
    }

}
