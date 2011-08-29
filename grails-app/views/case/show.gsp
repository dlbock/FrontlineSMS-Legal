<%@ page contentType="text/html;charset=UTF-8" %>
<html>
<head>
    <title>ShowCasePage</title>
    <meta name="layout" content="main">
    <link rel="stylesheet" href="${resource(dir: 'css', file: 'forms.css')}"/>

    <g:javascript library="linkContactToCase"/>
    <g:javascript library="caseUpdate"/>
    <g:javascript library="picnet.table.filter.min"/>
    <g:javascript library="contactSearch"/>
    <g:javascript library="deleteFromDetailsPage"/>
    <g:javascript library="confirmationDialog"/>
    <g:javascript library="enableUpdateButtonOnDetailsChange"/>

    <script type="text/javascript">
        $(function() {
            frontlinesms.showCaseOnLoad();
            frontlinesms.linkContactToCase();
            frontlinesms.contactSearchOnLoad();
            frontlinesms.initializeCaseDeletion();
        })
    </script>
</head>

<body>
<h1 class="form-header">Case Details</h1>
<g:form action="update" id="save-case-form" name="save-case-form">

    <label>Case ID</label>
    <g:hiddenField name="currentId" id="current-id" value="${caseToDisplay.id}"/>
    <g:textField name="caseId" id="case-id" value="${caseToDisplay.caseId}"/>
    <label>Description</label>

    <g:textArea name="description" id="case-description" cols="100" rows="10" value="${caseToDisplay.description}"/>
    <p><g:checkBox name="caseStatus" id="case-status" checked="${caseToDisplay.active}"/>Case active</p>

    <div class="form-submit-area">
        <button class="link-button" id="link-contact-button">Link contacts</button>
    </div>
    <g:hiddenField name="caseLinkedContacts" id="case-linked-contacts" value="${caseLinkedContacts}"/>

    <table name="contacts" id="contacts">
        <tr>
            <th>Contact Name</th>
            <th>Phone</th>
            <th>Relationship</th>
        </tr>
        <g:if test="${linkedContactRowData?.size() > 0}">
            <g:each in="${linkedContactRowData}">
                <tr name="contactRow">
                    <td value="${it.contactName}">
                        ${it.contactName}
                        <span class="id" style="display:none">${it.id}</span>
                    </td>
                    <td value="${it.contactNumber}">${it.contactNumber}</td>
                    <td value="${it.contactInvolvement}">${it.contactInvolvement}</td>
                    <td class="unlink-contact-button">Unlink</td>
                </tr>
            </g:each>
        </g:if>
    </table>

    <div id="show-case-update-form" class="form-submit-area">
        <g:actionSubmit id="case-update" value="Update" disabled="disabled"/>
    </div>
</g:form>

<g:form action="delete" class="form-delete" id="${caseToDisplay.caseId}">
    <button id="delete-button" class="action-button">Delete</button>

</g:form>
<button id="case-update-cancel" class="action-button">Cancel</button>


<div id="link-contacts" title="Link Contacts">
    <g:textField name="contactNameSearch" id="contact-name-search"/>
    <g:form action="">
        <table id="link-contacts-outer-table">
            <thead>
            <tr><td>Name</td></tr>
            </thead>
            <tbody>
            <tr>
                <td>
                    <div id="link-contacts-inner-table-div" style="height:200px;overflow: scroll; width:200px">
                        <table id="contactsTable">
                            <thead>
                            </thead>
                            <tbody>
                            <g:each in="${contactList}" var="contact">
                                <tr class="contactLink" id="<%=contact.id%>">

                                    <td class="contact-name">
                                        <a href="#"><%=contact.name%></a>
                                    </td>

                                    <td class="contact-number">
                                        <a href="#"><%=contact.primaryMobile%></a>
                                    </td>

                                </tr>
                            </g:each>
                            </tbody>
                        </table>
                    </div>
                </td>
            </tr>
            </tbody>
        </table>

    </g:form>
</div>

<div id="case-update-cancel-dialog" title="Discard Changes?" style="display: none;">
    <p>Do you want to discard changes to this case? Your changes will not be saved.</p>
</div>

<div id="deleteDialog" title="Delete Case" style="display: none;">
    <p>Are you sure you want to delete this case?</p>
</div>
</body>

</html>