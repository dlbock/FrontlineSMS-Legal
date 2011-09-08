<%@ page contentType="text/html;charset=UTF-8" %>
<html>
<head>
    <meta name="layout" content="main">
    <link rel="stylesheet" href="${resource(dir: 'css', file: 'fullcalendar.css')}"/>
    <link rel="stylesheet" href="${resource(dir: 'css', file: 'schedule.css')}"/>
    <link rel="stylesheet" href="${resource(dir: 'css', file: 'theme.css')}"/>
    <link rel="stylesheet" href="${resource(dir: 'css', file: 'fullcalendar.print.css')}" media="print"/>
    <g:javascript library="fullcalendar"/>
    <g:javascript library="confirmationDialog"/>
    <g:javascript library="calendarInteractions"/>
    <g:javascript library="eventDetails"/>
    <g:javascript library="jquery.timeentry.min"/>
    <g:javascript library="eventInteractions"/>
    <g:javascript library="picnet.table.filter.min"/>
    <g:javascript library="contactSearch"/>
    <g:javascript library="caseSearch"/>
    <g:javascript library="linkContactToEvent"/>
    <g:javascript library="formValidation"/>
    <g:javascript library="linkCaseToEvent"/>

    <script type="text/javascript">
        $(function() {
            $('#view-event').hide();
            frontlinesms.calendarInteractions();
            frontlinesms.eventDetails();
            frontlinesms.attachActionWithLinkContactButton();
            frontlinesms.activateDatePicker();
            frontlinesms.activateTimePicker();
            frontlinesms.contactSearchOnLoad("#contactsTable", "#contact-name-search");
            frontlinesms.caseSearchOnLoad();
            frontlinesms.linkCaseToEvent();
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

<g:render template="linkContacts"/>
<g:render template="../shared/linkCases"/>

</body>
</html>
