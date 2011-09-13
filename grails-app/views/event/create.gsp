<%@ page import="org.springframework.web.util.HtmlUtils" contentType="text/html;charset=UTF-8" %>
<%@ page contentType="text/html;charset=UTF-8" %>
<html>
<head>
    <title>Create New Event</title>
    <meta name="layout" content="main">
    <link rel="stylesheet" href="${resource(dir: 'css', file: 'forms.css')}"/>
    <g:javascript library="picnet.table.filter.min"/>
    <g:javascript library="jquery.timeentry.min"/>
    <g:javascript library="schedule/calendarInteractions"/>
    <g:javascript library="event/interactions"/>
    <g:javascript library="shared/linkContactToEvent"/>
    <g:javascript library="shared/linkCaseToEvent"/>
    <g:javascript library="legalContact/search"/>
    <g:javascript library="case/search"/>
    <g:javascript library="shared/enableOrDisableKeyStrokeOnField"/>

    <script type="text/javascript">
        $(function() {
            frontlinesms.linkContactToEvent();
            frontlinesms.linkCaseToEvent();
            frontlinesms.contactSearchOnLoad("#contactsTable", "#contact-name-search");
            frontlinesms.caseSearchOnLoad();
            var keyCodeForEnterKey = 13;
            frontlinesms.blockKeyInField(keyCodeForEnterKey, '#event-title');
            var keyCodeForTabKey = 9;
            frontlinesms.enableKeyInField(keyCodeForTabKey, '#event-date');
            frontlinesms.disablePasteOnField('#event-start-time');
            frontlinesms.disablePasteOnField('#event-date');
            frontlinesms.disablePasteOnField('#event-end-time');
        })
    </script>

</head>

<body>
<h1>Create Event</h1>

<g:form action="save" name="createEventForm" method="post">

    <label>Title</label>
    <g:textField id="event-title" name="eventTitle" value="${params.eventTitle}"/><br><br>
    <label>Date</label>
    <g:textField name="dateFieldSelected" id="event-date" value="${params.dateFieldSelected}"/><br><br>
    <label>Start time (UTC)</label>
    <g:textField name="startTimeField" id="event-start-time" value="${params.startTimeField}"/><br><br>
    <label>End time (UTC)</label>
    <g:textField name="endTimeField" id="event-end-time" value="${params.endTimeField}"/><br><br>


    <div class="form-submit-area">
        <button id="link-contact-button">Link contact</button>
    </div>


    <g:hiddenField name="linkedContacts" id="event-linked-contacts" value="${params.linkedContacts}"/>
    <table name="contacts" id="contacts">
        <tr>
            <th>Contact Name</th>
            <th>Phone</th>
        </tr>

    </table>

    <div class="form-submit-area">
        <button id="link-case-button">Link case</button>
    </div>

    <g:hiddenField name="linkedCases" id="event-linked-cases" value="${params.linkedCases}"/>
    <table name="cases" id="cases">
        <thead>
        <tr>
            <th>Case ID</th>
            <th>Case Title</th>
            <th>Status</th>
        </tr>
        </thead>
        <tbody>
        </tbody>
    </table>
    <br>

    <div class="form-submit-area">
        <input type="submit" id="event-save" value="Save"/>
        <button id="event-cancel" onclick="frontlinesms.eventCancelConfirmAction();
        return false;">Cancel</button>
    </div>

</g:form>

<g:render template="../shared/linkContacts"/>
<g:render template="../shared/linkCases" />

<div id="event-cancel-dialog" title="Cancel event creation?" style="display: none;">
    <p>Your changes have not been saved. Are you sure you want to cancel?</p>
</div>
<script type="text/javascript">
    $(function() {
        frontlinesms.activateDatePicker();
        frontlinesms.activateTimePicker();
    });
</script>
</body>
</html>