<%@ page import="java.text.SimpleDateFormat" contentType="text/html;charset=UTF-8" %>
<html>
<head>
    <title>ShowCasePage</title>
    <meta name="layout" content="main">
    <link rel="stylesheet" href="${resource(dir: 'css', file: 'forms.css')}"/>

    <g:javascript library="shared/linkContactToCase"/>
    <g:javascript library="shared/relationshipDialog"/>
    <g:javascript library="case/update"/>
    <g:javascript library="picnet.table.filter.min"/>
    <g:javascript library="legalContact/search"/>
    <g:javascript library="shared/deleteFromDetailsPage"/>
    <g:javascript library="shared/confirmationDialog"/>
    <g:javascript library="shared/enableUpdateButtonOnDetailsChange"/>
    <g:javascript library="shared/enableOrDisableKeyStrokeOnField"/>

    <script type="text/javascript">
        $(function() {
            frontlinesms.showCaseOnLoad();
            frontlinesms.linkContactToCase();
            frontlinesms.contactSearchOnLoad("#contactsTable", "#contact-name-search");
            frontlinesms.initializeCaseDeletion();
            var keyCodeForEnterKey = 13;
            frontlinesms.blockKeyInField(keyCodeForEnterKey, '#case-id');
            frontlinesms.blockKeyInField(keyCodeForEnterKey, '#case-title');

        })
    </script>
</head>

<body>
<h1 class="form-header">Case Details</h1>
<g:form action="update" id="save-case-form" name="save-case-form">

    <label>Case Title</label>
    <g:textField name="caseTitle" id="case-title" value="${caseToDisplay.caseTitle}" maxlength="300"/>

    <label>Case ID</label>
    <g:hiddenField name="currentId" id="current-id" value="${caseToDisplay.id}"/>
    <g:textField name="caseId" id="case-id" value="${caseToDisplay.caseId}" maxlength="100"/>

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

    <g:if test="${pastEvents}">
        <span>Past Event:</span>
        <table id="past-events" name="pastEvents">
            <thead>
            <tr>
                <th>Title</th>
                <th>Date</th>
                <th>Time (UTC)</th>
            </tr>
            </thead>
            <tbody>

            <g:each in="${pastEvents}" var="event">
                <tr>
                    <td>
                        <%=event.eventTitle%>
                    </td>
                    <td>
                        <%=new SimpleDateFormat("dd-MMM-yyyy").format(event.dateFieldSelected)%>
                    </td>
                    <td>
                        <%=new SimpleDateFormat("hh:mm a").format(event.startTimeField)%> - <%=new SimpleDateFormat("hh:mm a").format(event.endTimeField)%>
                    </td>
                </tr>
            </g:each>
            </tbody>
        </table>
    </g:if>
    <g:if test="${ongoingEvents}">
        <span>Ongoing Event:</span>
        <table id="current-events" name="currentEvents">
            <thead>
            <tr>
                <th>Title</th>
                <th>Date</th>
                <th>Time (UTC)</th>
            </tr>
            </thead>
            <tbody>

            <g:each in="${ongoingEvents}" var="event">
                <tr>
                    <td>
                        <%=event.eventTitle%>
                    </td>
                    <td>
                        <%=new SimpleDateFormat("dd-MMM-yyyy").format(event.dateFieldSelected)%>
                    </td>
                    <td>
                        <%=new SimpleDateFormat("hh:mm a").format(event.startTimeField)%> - <%=new SimpleDateFormat("hh:mm a").format(event.endTimeField)%>
                    </td>
                </tr>
            </g:each>
            </tbody>
        </table>
    </g:if>
    <g:if test="${futureEvents}">
        <span>Upcoming Event:</span>
        <table class="future-events" id="future-events" name="futureEvents">
            <thead>
            <tr>
                <th>Title</th>
                <th>Date</th>
                <th>Time (UTC)</th>
            </tr>
            </thead>
            <tbody>

            <g:each in="${futureEvents}" var="event">
                <tr>
                    <td>
                        <%=event.eventTitle%>
                    </td>
                    <td>
                        <%=new SimpleDateFormat("dd-MMM-yyyy").format(event.dateFieldSelected)%>
                    </td>
                    <td>
                        <%=new SimpleDateFormat("hh:mm a").format(event.startTimeField)%> - <%=new SimpleDateFormat("hh:mm a").format(event.endTimeField)%>
                    </td>
                </tr>
            </g:each>
            </tbody>
        </table>
    </g:if>

    <div id="show-case-update-form" class="form-submit-area">
        <g:actionSubmit id="case-update" value="Update" disabled="disabled"/>
    </div>
</g:form>
<div id="offset-button-container">
    <g:form action="delete" class="form-delete" id="${caseToDisplay.caseId}">
        <button id="delete-button" class="action-button">Delete</button>
    </g:form>

    <button id="case-update-cancel" class="action-button">Cancel</button>
</div>
<g:render template="../shared/linkContacts"></g:render>

<div id="case-update-cancel-dialog" title="Cancel case changes?" style="display: none;">
    <p>Your changes have not been saved. Are you sure you want to cancel?</p>
</div>

<div id="deleteDialog" title="Delete Case" style="display: none;">
    <p>Are you sure you want to delete this case?</p>
</div>

<div id="case-contact-relationship-dialog" title="Relationship to case" style="display: none;">
    <input id="case-contact-relationship"/>
</div>
</body>

</html>