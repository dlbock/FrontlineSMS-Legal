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
    <g:javascript library="unlinkContactFromEvent"/>

    <script type="text/javascript">
        $(function() {
            $('#view-event').hide();
            frontlinesms.calendarInteractions();
            frontlinesms.eventDetails();
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

<div id="view-event" title="Event Details" style="display: none;">
    <label><b>Title</b></label><br/>
    <input type="text" name="eventTitle" id="event-title" value="" /><br/><br/>
    <label><b>Date</b></label><br/>
    <input type="text" name="eventDate" id="event-date" value=""/><br/><br/>
    <label><b>Start Time</b></label><br/>
    <input type="text" name="eventStartTime" id="event-start-time" value=""/><br/><br/>
    <label><b>End Time</b></label><br/>
    <input type="text" name="eventEndTime" id="event-end-time" value=""/><br/><br/>
    <table id="event-contacts-table">
        <thead>
        <tr >
            <th>Contact name</th>
            <th>Phone number</th>
        </tr>
        </thead>
        <tbody>
        </tbody>
    </table>
    <div align="right">
        <input type="button" id="update-event" value="Update"/>
        <input type="button" id="delete-event" value="Delete"/>
    </div>
    <g:hiddenField name="eventId" id="event-id"></g:hiddenField>

</div>

<div id="event-cancel-dialog" title="Cancel event?" style="display: none;">
    <p>Are you sure you want to delete this event? Yes or No.</p>
</div>

<div id="contactUnlinkDialog" title="Unlink Contact from Event" style="display: none;">
    <p>Are you sure you want to unlink this contact from the event?</p>
</div>

</body>
</html>