<%@ page contentType="text/html;charset=UTF-8" %>
<html>
<head><title>Search page</title>
    <meta name="layout" content="main">
    <link rel="stylesheet" href="${resource(dir: 'css', file: 'forms.css')}"/>

    <g:javascript library="picnet.table.filter.min"/>
    <g:javascript library="caseSearch"/>
    <g:javascript library="confirmationDialog"/>
    <g:javascript library="caseDelete"/>

    <script type="text/javascript">
        $(function() {
            frontlinesms.caseSearchOnLoad();
            frontlinesms.initializeCaseDeletion();
        })
    </script>
</head>

<body>
<h1 class="form-header">Search for Case by Case ID</h1>

<g:form action="search" method="POST">
    <label>Enter the case ID to search for cases</label>
    <g:textField class="wide-text-box" name="caseId" id="caseId"/>
</g:form>

<g:if test="${foundCase}">
    <table class="search-results" id="SearchResults">
        <thead>
        <tr>
            <th>Case ID</th>
            <th>Status</th>
        </tr>
        </thead>
        <tbody>
    <table class="search-results" id="SearchResults">
        <thead>
        <tr>
            <th>Case ID</th>
            <th>Status</th>
        </tr>
        </thead>
        <tbody>
        <g:each in="${foundCase}" var="legalCase">
            <tr>
                <td>
                    <g:link controller="case" action="show" id="${legalCase.caseId}"><%=legalCase.caseId%></g:link>
                </td>
                <td>
                    <%=legalCase.active ? "active" : "inactive"%>
                </td>
                <td>
                    <g:form action="delete" id="${legalCase.caseId}" bla="connie">
                        <button class="deleteButtons" id="deleteButton${legalCase.caseId}">Delete</button>
                        <g:hiddenField name="caseId" value="${legalCase.caseId}"/>
                    </g:form>
                </td>

            </tr>
        </g:each>
        </tbody>
    </table>
</g:if>

<div id="caseDeleteDialog" title="Delete Case" style="display: none;">
    <p>Are you sure you want to delete this case?</p>
</div>

</body>
</html>