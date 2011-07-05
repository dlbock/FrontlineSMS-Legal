<%@ page contentType="text/html;charset=UTF-8" %>
<html>
<head>
    <meta name="layout" content="main">
    <link rel="stylesheet" href="${resource(dir: 'css', file: 'forms.css')}"/>
    <g:javascript library="caseCreate"/>
    <title>Create New Case</title>
</head>

<body>
<h1 class="form-header">Case Details</h1>

<form action="save" method="post">
    <label>Case Number</label>
    <g:textField name="caseId" id="case-id"/>
    <label>Description</label>
    <g:textArea name="description" id="case-description" cols="100" rows="10"/>
    <div class="form-submit-area">
        <input type="submit" id="case-save" value="Save"/>
        <button id="case-cancel" onclick="frontlinesms.caseCancelConfirmAction(); return false;">Cancel</button>
    </div>
</form>

<div id="case-cancel-dialog" title="Cancel case creation?" style="display: none;">
    <p>Are you sure you do not want to save this case?</p>
</div>

</body>
</html>