<%@ page import="org.springframework.web.util.HtmlUtils" contentType="text/html;charset=UTF-8" %>
<html>
<head>
    <meta name="layout" content="main">
    <link rel="stylesheet" href="${resource(dir: 'css', file: 'forms.css')}"/>
    <g:javascript library="shared/linkCaseToContact"/>
    <g:javascript library="shared/relationshipDialog"/>
    <g:javascript library="picnet.table.filter.min"/>
    <g:javascript library="legalContact/create"/>
    <g:javascript library="case/search"/>
    <g:javascript library="shared/enableUpdateButtonOnDetailsChange"/>
    <g:javascript library="shared/formValidation"/>
    <g:javascript library="shared/enableOrDisableKeyStrokeOnField"/>

    <title>Create New Contact</title>
    <script type="text/javascript">
        $(function() {
            frontlinesms.linkCaseToContact();
            frontlinesms.createNewContactOnLoad();
            frontlinesms.caseSearchOnLoad();
            frontlinesms.enableUpdateButtonOnDetailsChange();
            frontlinesms.validateContactNumber();
            var keyCodeForEnterKey = 13;
            frontlinesms.blockKeyInField(keyCodeForEnterKey, '#contact-name');
        })
    </script>
</head>

<body>
<h1 class="form-header">Create Contact</h1>
<form action="save" method="POST" id="contact-save-form">

    <label>Name</label>
    <g:textField name="name" id="contact-name" value="${params.name}" maxlength="250"/>
    <label>Number</label>
    <g:textField name="primaryMobile" id="contact-primary-mobile" value="${params.primaryMobile}" maxlength="25"/>
    <label>Notes (Max 1024 characters)</label>
    <g:textArea name="notes" id="contact-notes" value="${params.notes}" cols="100" rows="10" maxlength="1024"/>

    <div class="form-submit-area">
       <button id="link-case-button">Link Cases</button>
    </div>

    <g:hiddenField name="linkedCases" id="contact-linked-cases" value="${contactLinkedCases}"/>

    <table name="cases" id="cases">
        <thead>
        <tr>
            <th class="table-case-id-column">Case Id</th>
            <th class="table-case-title-column">Case Title</th>
            <th class="table-case-contact-relationship-column">Relationship</th>
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

<g:render template="../shared/linkCases"/>

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
