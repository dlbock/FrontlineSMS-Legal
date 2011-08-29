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
    <g:javascript library="eventInteractions"/>
    <g:javascript library="unlinkContactFromEvent"/>
    <g:javascript library="picnet.table.filter.min"/>
    <g:javascript library="contactSearch"/>
    <g:javascript library="linkContactToEvent"/>

    <script type="text/javascript">
        $(function() {
            $('#view-event').hide();
            frontlinesms.calendarInteractions();
            frontlinesms.eventDetails();
            frontlinesms.attachActionWithLinkContactButton();
            frontlinesms.activateDatePicker();
            frontlinesms.contactSearchOnLoad();
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
    <input type="text" name="eventTitle" id="event-title" value=""/><br/><br/>
    <label><b>Date</b></label><br/>
    <input type="text" name="eventDate" id="event-date" value=""/><br/><br/>
    <label><b>Start Time</b></label><br/>
    <input type="text" name="eventStartTime" id="event-start-time" value=""/><br/><br/>
    <label><b>End Time</b></label><br/>
    <input type="text" name="eventEndTime" id="event-end-time" value=""/><br/><br/>

    <g:hiddenField name="linkedContacts" id="event-linked-contacts" value="${params.linkedContacts}"/>
    <table id="event-contacts-table">
        <thead>
        <tr>
            <th>Contact name</th>
            <th>Phone number</th>
        </tr>
        </thead>
        <tbody>
        </tbody>
    </table>

    <div align="left"><input type="button" id="link-contact-button" value="Link Contact"/></div>

    <div align="right">
        <input type="button" id="update-event" value="Update"/>
        <input type="button" id="delete-event" value="Delete"/>
    </div>
    <g:hiddenField name="eventId" id="event-id"></g:hiddenField>
</div>

<div id="delete-event-dialog" title="Delete event?" style="display: none;">
    <p>Are you sure you want to delete this event? Yes or No.</p>
</div>

<div id="unlink-contact-dialog" title="Unlink Contact from Event" style="display: none;">
    <p>Are you sure you want to unlink this contact from the event?</p>
</div>

<div id="link-contact-dialog" title="Link Contact to an Event" style="display: none;">
    <g:form action="">
        <p>Enter the name or the mobile number to search for Contacts</p>
        <g:textField name="contactNameSearch" id="contact-name-search"/>
        <div id="link-contacts-inner-table-div" style="height:200px;overflow: scroll; width:250px">
            <table id="contactsTable">
                <thead>
                <tr>
                    <td>Name</td>
                    <td>Number</td>
                </tr>
                </thead>
                <tbody>
                <g:each in="${contactList}" var="contact">
                    <tr id="${contact.id}" class="contactRow">

                        <td class="contact-name">
                            <%=contact.name%>
                        </td>

                        <td class="contact-number">
                            <%=contact.primaryMobile%>
                        </td>
                        <td class="contactLink" id="${contact.id}"><a href="#">Link Contact</a></td>
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

</body>
</html>
