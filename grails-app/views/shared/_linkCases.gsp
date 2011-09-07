<%@ page import="org.springframework.web.util.HtmlUtils" %>
<div id="link-case-dialog" title="Link Cases">
    <h3 class="form-header">Search for Case by Case ID</h3>
    <label>Enter the case ID to search for cases</label>
    <g:textField class="medium-text-box" name="caseId" id="caseId"/>

    <g:form action="search" method="POST">

        <g:if test="${allCases}">
            <g:hiddenField name="linkedCases" id="event-linked-cases" value="${params.linkedCases}"/>
            <table class="search-results" id="SearchResults">
                <thead>
                <tr>
                    <th>Case ID</th>
                    <th>Case Title</th>
                    <th>Status</th>
                </tr>
                </thead>
                <tbody>

                <g:each in="${allCases}" var="legalCase">
                    <tr class="caseLink" id="${legalCase.caseId}">
                        <td class="case-id">
                            <%=HtmlUtils.htmlEscape(legalCase.caseId)%>
                        </td>
                        <td class="case-title">
                            <%=HtmlUtils.htmlEscape(legalCase.caseTitle)%>
                        </td>

                        <td class="case-status">
                            <%=legalCase.active ? "active" : "inactive"%>
                        </td>
                        <td>
                            <a href="" class="link-case" id="${legalCase.caseId}">Link</a>
                        </td>
                    </tr>
                </g:each>
                </tbody>
            </table>
        </g:if>
    </g:form>

</div>