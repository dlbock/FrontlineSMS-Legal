<%@ page import="java.text.SimpleDateFormat; grails.converters.JSON" contentType="text/html;charset=UTF-8" %>
<%@ page import="org.springframework.web.util.HtmlUtils" contentType="text/html;charset=UTF-8" %>
<html>
<head>
    <title>Show Contact Page</title>
    <meta name="layout" content="main">
    <link rel="stylesheet" href="${resource(dir: 'css', file: 'forms.css')}"/>
    <g:javascript library="linkCaseToContact"/>
    <g:javascript library="picnet.table.filter.min"/>
    <g:javascript library="caseSearch"/>
    <g:javascript library="contactCreate"/>
    <g:javascript library="deleteFromDetailsPage"/>
    <g:javascript library="confirmationDialog"/>
    <g:javascript library="enableUpdateButtonOnDetailsChange"/>
    <g:javascript library="formValidation"/>

    <script type="text/javascript">
        $(function() {
            frontlinesms.linkCaseToContact();
            frontlinesms.caseSearchOnLoad();
            frontlinesms.createNewContactOnLoad();
            frontlinesms.initializeCaseDeletion();
            frontlinesms.enableUpdateButtonOnDetailsChange();
            frontlinesms.validateContactNumber();
        })
    </script>
</head>

<body>
<h1 class="form-header">Contact Details</h1>

<form action="../update" id="contact-save-form" method="post">
    <g:hiddenField name="currentId" value="${contactToDisplay.id}"/>
    <g:hiddenField name="linkedCases" id="contact-linked-cases" value="${contactLinkedCases}"/>
    <label>Name</label>
    <g:textField name="name" id="contact-name" value="${contactToDisplay.name}"/>
    <label>Number</label>
    <g:textField name="primaryMobile" id="contact-primary-mobile" value="${contactToDisplay.primaryMobile}"
                 maxlength="15"/>
    <label>Notes</label>
    <g:textArea rows="10" cols="100" name="notes" id="contact-notes" value="${contactToDisplay.notes}"/>


    <div class="form-submit-area">
        <button id="link-case-button">Link Cases</button>
    </div>


    <table name="cases" id="cases">
        <thead>
        <tr>
            <th>Case ID</th>
            <th>Relationship</th>
        </tr>
        </thead>
        <tbody>
        <g:each in="${contactToDisplay.linkedCases}" var="legalCase">
            <tr>
                <td>
                    <span class="id" style="display:none;"><%=legalCase.legalCase.id%></span>
                    <%=HtmlUtils.htmlEscape(legalCase.legalCase.caseId)%>
                </td>
                <td>
                    <%=HtmlUtils.htmlEscape(legalCase.involvement)%>
                </td>
                <td class="unlink-case-button">
                    <a href="">Unlink</a>
                </td>
            </tr>
        </g:each>
        </tbody>
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
    <div id="show-contact-update-form" class="form-submit-area">
        <g:actionSubmit id="contact-save" value="Update" disabled="disabled"/>
    </div>
</form>

<g:form action="delete" class="form-delete" id="${contactToDisplay.id}">
    <button id="delete-button" class="action-button">Delete</button>
</g:form>

<button id="contact-create-cancel" class="action-button">Cancel</button>
</div>


<div id="link-case-dialog" title="Link Cases">
    <h3 class="form-header">Search for Case by Case ID</h3>
    <label>Enter the case ID to search for cases</label>
    <g:textField class="medium-text-box" name="caseId" id="caseId"/>

    <g:form action="search" method="POST">

        <g:if test="${allCases}">
            <table class="search-results" id="SearchResults">
                <thead>
                <tr>
                    <th>Case ID</th>
                    <th>Status</th>
                </tr>
                </thead>
                <tbody>

                <g:each in="${allCases}" var="legalCase">
                    <tr class="caseLink" id="${legalCase.id}">
                        <td class="case-name">
                            <%=HtmlUtils.htmlEscape(legalCase.caseId)%>
                        </td>
                        <td>
                            <%=legalCase.active ? "active" : "inactive"%>
                        </td>
                    </tr>
                </g:each>
                </tbody>
            </table>
        </g:if>
    </g:form>

</div>

<div id="contact-save-no-name-dialog" title="Save Contact without a Name?" style="display: none;">
    <p>Are you sure you would like to save contact without a name?</p>
</div>

<div id="contact-create-cancel-dialog" title="Cancel contact changes?" style="display: none;">
    <p>Your changes have not been saved. Are you sure you want to cancel?</p>
</div>

<div id="deleteDialog" title="Delete Contact" style="display: none;">
    <p>Are you sure you want to delete this contact?</p>
</div>

</body>
</html>