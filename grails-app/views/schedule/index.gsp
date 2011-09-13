<%@ page contentType="text/html;charset=UTF-8" %>
<html>
<head>
    <meta name="layout" content="main">
    <link rel="stylesheet" href="${resource(dir: 'css', file: 'fullcalendar.css')}"/>
    <link rel="stylesheet" href="${resource(dir: 'css', file: 'schedule.css')}"/>
    <link rel="stylesheet" href="${resource(dir: 'css', file: 'theme.css')}"/>
    <link rel="stylesheet" href="${resource(dir: 'css', file: 'fullcalendar.print.css')}" media="print"/>
    <g:javascript library="fullcalendar"/>
    <g:javascript library="shared/confirmationDialog"/>
    <g:javascript library="schedule/calendarInteractions"/>
    <g:javascript library="event/details"/>
    <g:javascript library="jquery.timeentry.min"/>
    <g:javascript library="event/interactions"/>
    <g:javascript library="picnet.table.filter.min"/>
    <g:javascript library="legalContact/search"/>
    <g:javascript library="case/search"/>
    <g:javascript library="shared/linkContactToEvent"/>
    <g:javascript library="shared/formValidation"/>
    <g:javascript library="shared/linkCaseToEvent"/>
    <g:javascript library="shared/enableOrDisableKeyStrokeOnField"/>

    <script type="text/javascript">
        $(function() {
            $('#view-event').hide();
            frontlinesms.calendarInteractions();
            frontlinesms.eventDetails();
            frontlinesms.attachActionWithLinkContactButton();
            frontlinesms.attachActionWithUnlink();
            frontlinesms.activateDatePicker();
            frontlinesms.activateTimePicker();
            frontlinesms.contactSearchOnLoad("#contactsTable", "#contact-name-search");
            frontlinesms.caseSearchOnLoad();
            frontlinesms.linkCaseToEvent();
            var keyCodeForTabKey = 9;
            frontlinesms.enableKeyInField(keyCodeForTabKey, '#event-date');
            frontlinesms.disablePasteOnField('#event-start-time');
            frontlinesms.disablePasteOnField('#event-date');
            frontlinesms.disablePasteOnField('#event-end-time');
            frontlinesms.linkContactToEvent();
            <g:if test="${year}">
            <g:if test="${month}">
            $('#schedule').fullCalendar('gotoDate', ${year}, ${month});
            </g:if>
            </g:if>
        });
    </script>
    <title>Schedule</title>
</head>

<body class="schedule">
<div id="schedule"></div>

<g:render template="eventDetails"/>

<div id="delete-event-dialog" title="Delete event?" style="display: none;">
    <p>Are you sure you want to delete this event? Yes or No.</p>
</div>

<div id="unlink-contact-dialog" title="Unlink Contact from Event" style="display: none;">
    <p>Are you sure you want to unlink this contact from the event?</p>
</div>

<g:render template="../shared/linkContacts"/>
<g:render template="../shared/linkCases"/>

</body>
</html>
