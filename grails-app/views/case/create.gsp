<%@ page contentType="text/html;charset=UTF-8" %>
<html>
<head>
    <title>Create New Case</title>
    <meta name="layout" content="main">
    <link rel="stylesheet" href="${resource(dir: 'css', file: 'forms.css')}"/>

    <g:javascript library="case/create"/>
    <g:javascript library="shared/linkContactToCase"/>
    <g:javascript library="picnet.table.filter.min"/>
    <g:javascript library="legalContact/search"/>
    <g:javascript library="shared/enableOrDisableKeyStrokeOnField"/>
    <g:javascript library="shared/relationshipDialog"/>

    <script type="text/javascript">
        $(function() {
            frontlinesms.createNewCaseOnLoad();
            frontlinesms.linkContactToCase();
            frontlinesms.contactSearchOnLoad("#contactsTable", "#contact-name-search");
            var keyCodeForEnterKey = 13;
            frontlinesms.blockKeyInField(keyCodeForEnterKey, '#case-id');
            frontlinesms.blockKeyInField(keyCodeForEnterKey, '#case-title');
        })
    </script>
</head>

<body>

<h1 class="form-header">Create Case</h1>
<form action="save" method="POST" id="case-create-form">

    <label>Case Title</label>
    <g:textField name="caseTitle" id="case-title"  maxlength="300"/>

    <label>Case ID</label>
    <g:textField name="caseId" id="case-id" maxlength="100"/>

    <label>Description</label>
    <g:if test="${!params.description}">
        <g:textArea name="description" id="case-description" cols="100" rows="10"/>
    </g:if>

    <g:if test="${params.description}">
        <g:textArea name="description" id="case-description" cols="100" rows="10" value="${params.description}"/>
    </g:if>

    <div class="form-submit-area">
        <button id="link-contact-button">Link contacts</button>
    </div>

    <g:hiddenField name="caseLinkedContacts" id="case-linked-contacts" value="${caseLinkedContacts}"/>

    <table name="contacts" id="contacts">
        <tr>
            <th class="table-contact-name-column-on-case-create">Contact Name</th>
            <th class="table-contact-number-column-on-case-create">Phone</th>
            <th class="table-case-contact-relationship-column">Relationship</th>
        </tr>
        <g:if test="${contacts?.size > 0}">
        <g:each in="${contacts}">
        <tr name="contactRow" id="contact-row">
        <td><g:textField value="${it.name}" name="contactName"/></td>
        </tr>
        </g:each>
        </g:if>
    </table>


    <div class="form-submit-area">
        <button id="case-save">Save</button>
        <button id="case-cancel">Cancel</button>
    </div>

</form>

<g:render template="../shared/linkContacts"></g:render>

<div id="case-cancel-dialog" title="Cancel case creation?" style="display: none;">
    <p>Your changes have not been saved. Are you sure you want to cancel?</p>
</div>

<div id="blank-case-title-confirmation-dialog" title="Save without case title?" style="display: none;">
    <p>Are you sure you want to save a case without a title?</p>
</div>

<div id="case-contact-relationship-dialog" title="Relationship to case" style="display: none;">
    <input id="case-contact-relationship"/>
</div>
</body>
</html>