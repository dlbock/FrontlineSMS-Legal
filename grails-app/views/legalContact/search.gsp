<%@ page contentType="text/html;charset=UTF-8" %>
<html>
<head><title>Contact Search Page</title>
    <meta name="layout" content="main">
    <link rel="stylesheet" href="${resource(dir: 'css', file: 'forms.css')}"/>
    <g:javascript library="picnet.table.filter.min"/>
    <g:javascript library="contactSearchForSearchPage"/>
    <g:javascript library="confirmationDialog"/>
    <g:javascript library="deleteFromSearchPage"/>

    <script type="text/javascript">
        $(function() {
            frontlinesms.contactSearchOnLoad();
            frontlinesms.initializeCaseDeletion();
        });
    </script>
</head>

<body>

<h1 class="form-header">Search for Contact by Name/Mobile Number</h1>
<label>Enter the contact Name/Mobile Number to search for a contact</label>
<br>
<g:textField class="wide-text-box" name="contact-search-bar" id="contact-search-bar" value="" size="70"/>

<g:if test="${foundContact}">
    <table class="search-results" id="SearchResults">
        <thead>
        <tr>
            <th>Name</th>
            <th>Mobile Number</th>
        </tr>
        </thead>
        <tbody>

        <g:each in="${foundContact}" var="legalContact">
             <tr class="contactLink" id="<%=legalContact.id%>">
                <td>
                    <g:link controller="legalContact" action="show"
                            id="${legalContact.id}"><%=legalContact.name%></g:link>

                </td>
                <td>
                    <g:link controller="legalContact" action="show"
                            id="${legalContact.id}"><%=legalContact.primaryMobile%></g:link>

                </td>
                <td class="table-cases-delete-row">
                    <g:form action="delete" id="${legalContact.id}">
                        <button class="deleteButtons action-button"
                                id="deleteButton${legalContact.id}">Delete</button>
                        <g:hiddenField name="id" value="${legalContact.id}"/>
                    </g:form>
                </td>

            </tr>
        </g:each>
        </tbody>
    </table>
</g:if>


<div id="deleteDialog" title="Delete Contact" style="display: none;">
    <p>Are you sure you want to delete this contact?</p>
</div>

</body>

</html>