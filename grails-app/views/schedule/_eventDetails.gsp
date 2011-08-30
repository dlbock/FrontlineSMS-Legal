<div id="view-event" title="Event Details" style="display: none;">
    <g:form controller="schedule" action="updateEvent" method="post">
        <g:hiddenField name="eventId" id="event-id" value="${params.id}"></g:hiddenField>
        <label><b>Title</b></label><br/>
        <input type="text" name="eventTitle" id="event-title" value="${params.eventTitle}" /><br/><br/>
        <label><b>Date</b></label><br/>
        <input type="text" name="eventDate" id="event-date" value="${params.eventDate}"/><br/><br/>
        <label><b>Start Time</b></label><br/>
        <input type="text" name="eventStartTime" id="event-start-time" value="${params.eventStartTime}"/><br/><br/>
        <label><b>End Time</b></label><br/>
        <input type="text" name="eventEndTime" id="event-end-time" value="${params.eventEndTime}"/><br/><br/>
        <g:hiddenField name="linkedContacts" id="event-linked-contacts"/>
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
        <div align="left">
            <input type="button" id="link-contact-button" value="Link Contact"/>
        </div>
        <div align="right" class="form-submit-area">
            <input type="submit" id="update-event" value="Update"/>
            <input type="button" id="delete-event" value="Delete"/>
        </div>
    </g:form>
</div>
