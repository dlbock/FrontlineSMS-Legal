package frontlinesms.legal.functionaltests.pages.cases

import geb.Page

class NewCasePage extends Page {
    static at = { $("title").text() == "Create New Case" }
    static url = "case/create"
    static content = {
        caseId { $("input", id: "case-id") }
        description { $("input", id: "case-description") }
        save { $("button", id: "case-save") }
        cancel { $("button", id: "case-cancel") }
        errorMessage { $("div", id: "errorMessage").text() }
        caseCancelDialog { $("div", id: "case-cancel-dialog")}
        cancelYes { $("button", id: "cancel-confirm-yes")}
        cancelNo { $("button", id: "cancel-confirm-no")}
        linkContact {$("#link-contact-button")}
        linkContactDialog { $("div", id: "link-contacts") }
        contactNameSearch { $("input", id: "contact-name-search") }
        contactLinkNotVisible { $("tr", class: "contactLink", filtermatch: "false").collect {module ContactRow, it} }
        linkContactDialogCancelButton { $(".ui-button-text").value("Cancel") }
        caseTitle {$("input", id:"case-title")}
        saveCaseWithoutCaseTitleDialog{ $("div", id:"save-case-without-case-title-dialog")}
        saveWithoutCaseTitleYes{ $("button", id:"save-confirm-yes")}
        saveWithoutCaseTitleNo{ $("button", id:"save-confirm-no")}
        contactListInPopUp{$(".contact-name")}
        noLinkedContacts {$("#contacts tbody tr").size() == 1}
        relationshipConfirmButton { $("button", id: "confirm-relationship")}
        relationshipCancelButton { $("button", id: "cancel-relationship")}
    }
}