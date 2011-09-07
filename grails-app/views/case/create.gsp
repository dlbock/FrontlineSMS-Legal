<%@ page contentType="text/html;charset=UTF-8" %>
<html>
<head>
    <title>Create New Case</title>
    <meta name="layout" content="main">
    <link rel="stylesheet" href="${resource(dir: 'css', file: 'forms.css')}"/>

    <g:javascript library="caseCreate"/>
    <g:javascript library="linkContactToCase"/>
    <g:javascript library="picnet.table.filter.min"/>
    <g:javascript library="contactSearch"/>
    <g:javascript library="enableOrDisableKeyStrokeOnField"/>
    <g:javascript library="linkCaseToContact"/>

    <script type="text/javascript">
        $(function() {
            frontlinesms.createNewCaseOnLoad();
            frontlinesms.linkContactToCase();
            frontlinesms.linkCaseToContact();
            frontlinesms.contactSearchOnLoad();
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
    <g:textField name="caseTitle" id="case-title"/>

    <label>Case ID</label>
    <g:textField name="caseId" id="case-id"/>

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
            <th>Contact Name</th>
            <th>Phone</th>
            <th>Relationship</th>
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


<div id="link-contacts" title="Link Contacts">
    <g:textField name="contactNameSearch" id="contact-name-search"/>
    <g:form action="">
        <table id="link-contacts-outer-table">
            <thead>
            <tr><td>Name</td></tr>
            </thead>
            <tbody>
            <tr>
                <td>
                    <div id="link-contacts-inner-table-div" style="height:200px;overflow: scroll; width:200px">
                        <table id="contactsTable">
                            <thead>
                            </thead>
                            <tbody>
                            <g:each in="${contactList}" var="contact">
                                <tr class="contactLink" id="${contact.id}">
                                    <td class="contact-name">
                                        <a href="#"><%=contact.name%></a>
                                    </td>
                                    <td class="contact-number">
                                        <a href="#"><%=contact.primaryMobile%></a>
                                    </td>
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

<div id="case-cancel-dialog" title="Cancel case creation?" style="display: none;">
    <p>Your changes have not been saved. Are you sure you want to cancel?</p>
</div>

<div id="save-case-without-case-title-dialog" title="Save without case title?" style="display: none;">
    <p>Are you sure you want to save a case without a title?</p>
</div>

<div id="case-contact-relationship-dialog" title="Relationship to case" style="display: none;">
    <input id="case-contact-relationship"/>
</div>
</body>
</html>