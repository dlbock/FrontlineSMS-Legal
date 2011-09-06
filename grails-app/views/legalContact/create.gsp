<%@ page import="org.springframework.web.util.HtmlUtils" contentType="text/html;charset=UTF-8" %>
<html>
<head>
    <meta name="layout" content="main">
    <link rel="stylesheet" href="${resource(dir: 'css', file: 'forms.css')}"/>
    <g:javascript library="linkCaseToContactOnCreateContact"/>
    <g:javascript library="picnet.table.filter.min"/>
    <g:javascript library="contactCreate"/>
    <g:javascript library="caseSearch"/>
    <g:javascript library="enableUpdateButtonOnDetailsChange"/>
    <g:javascript library="formValidation"/>


    <title>Create New Contact</title>
    <script type="text/javascript">
        $(function() {
            frontlinesms.linkCaseToContactOnCreateContact();
            frontlinesms.createNewContactOnLoad();
            frontlinesms.caseSearchOnLoad();
            frontlinesms.enableUpdateButtonOnDetailsChange();
            frontlinesms.validateContactNumber();
        })
    </script>

</head>

<body>
<h1 class="form-header">Create Contact</h1>

<form action="save" method="POST" id="contact-save-form">
    <g:hiddenField name="linkedCases" id="contact-linked-cases" value="${contactLinkedCases}"/>
    <label>Name</label>
    <g:textField name="name" id="contact-name" value="${params.name}"/>
    <label>Number</label>
    <g:textField name="primaryMobile" id="contact-primary-mobile" value="${params.primaryMobile}" maxlength="25"/>
    <label>Notes(Maximum 1024 characters allowed)</label>
    <g:textArea name="notes" id="contact-notes" value="${params.notes}" cols="100" rows="10" maxlength="1024" />

    <div class="form-submit-area">
        <button id="link-case-button">Link Cases</button>
    </div>

    <table id="cases">
        <thead>
        <tr>
            <th>Case Id</th>
            <th>Relationship</th>
        </tr>
        </thead>
        <tbody>

        </tbody>
    </table>

    <div class="form-submit-area">
        <button id="contact-save">Save</button>
        <button id="contact-create-cancel">Cancel</button>
    </div>
</form>

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
                        <td class="case-status">
                            <%=legalCase.active ? "active" : "inactive"%>
                        </td>
                        <td>
                            <a href="#" class="caseLinkButton">Link Case</a>
                        </td>
                    </tr>
                </g:each>
                </tbody>
            </table>
        </g:if>
    </g:form>
</div>

<div id="contact-save-no-name-dialog" title="Save Contact without Contact Name?" style="display: none;">
    <p>Are you sure you would like to save contact without a name?</p>
</div>

<div id="contact-create-cancel-dialog" title="Cancel Contact Creation?" style="display: none;">
    <p>Your changes have not been saved. Are you sure you want to cancel?</p>
</div>

<div id="case-contact-relationship-dialog" title="Relationship to case" style="display: none;">
    <input id="case-contact-relationship"/>
</div>
</body>
</html>
