<%@ page contentType="text/html;charset=UTF-8" %>
<html>
<head>
    <meta name="layout" content="main">
    <link rel="stylesheet" href="${resource(dir: 'css', file: 'forms.css')}"/>
    <g:javascript library="linkCaseToContact"/>
    <g:javascript library="picnet.table.filter.min"/>
    <g:javascript library="caseSearch"/>
    <g:javascript library="contactCreate"/>
    <g:javascript library="enableUpdateButtonOnDetailsChange"/>
    <g:javascript library="formValidation"/>


    <title>Create New Contact</title>
    <script type="text/javascript">
        $(function() {
            frontlinesms.linkCaseToContact();
            frontlinesms.caseSearchOnLoad();
            frontlinesms.createNewContactOnLoad();
            frontlinesms.enableUpdateButtonOnDetailsChange();
            frontlinesms.validateContactNumber();

        })
    </script>

</head>

<body>
<h1 class="form-header">Create Contact</h1>

<form action="save" method="POST" id="contact-save-form">
    <label>Name</label>
    <g:textField name="name" id="contact-name" value="${params.name}"/>
    <label>Number</label>
    <g:textField name="primaryMobile" id="contact-primary-mobile" value="${params.primaryMobile}" maxlength="15"/>
    <label>Notes</label>
    <g:textArea name="notes" id="contact-notes" value="${params.notes}" cols="100" rows="10"/>

    <div class="form-submit-area">
        <button id="contact-save">Save</button>
        <button id="contact-create-cancel">Cancel</button>
        <button id="link-case-button">Link Case</button>
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

<div id="contact-save-no-name-dialog" title="Save Contact without Contact Name?" style="display: none;">
    <p>Are you sure you would like to save contact without a name?</p>
</div>

<div id="contact-create-cancel-dialog" title="Cancel Contact Creation?" style="display: none;">
    <p>Do you want to cancel creation of this contact? Your changes will not be saved.</p>
</div>

</body>
</html>
