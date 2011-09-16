<%@ page import="org.springframework.web.util.HtmlUtils" %>
<div id="link-case-dialog" title="Link Case" style="display: none;">
    <h3 class="form-header">Search for Case</h3>
    <br/>
    <g:textField class="medium-text-box" name="caseId" id="case-search-field"/>

    <g:form action="search" method="POST">

        <g:if test="${allCases}">
            <g:hiddenField name="linkedCases" id="event-linked-cases" value="${params.linkedCases}"/>
            <table id="casesTable">
                <thead>
                <tr>
                    <th class="table-case-id-column">Case ID</th>
                    <th class="table-case-title-column">Case Title</th>
                    <th class="table-case-status-column">Status</th>
                </tr>
                </thead>
                <tbody>

                <g:each in="${allCases}" var="legalCase">
                    <tr class="caseRow" id="${legalCase.caseId}">
                        <td class="case-id">
                            <%=HtmlUtils.htmlEscape(legalCase.caseId)%>
                        </td>
                        <td class="case-title">
                            <%=HtmlUtils.htmlEscape(legalCase.caseTitle)%>
                        </td>

                        <td class="case-status">
                            <%=legalCase.active ? "active" : "inactive"%>
                        </td>
                        <td class="case-link">
                            <a href="" class="link-case" id="${legalCase.caseId}">Link</a>
                        </td>
                    </tr>
                </g:each>
                </tbody>
            </table>
        </g:if>
    </g:form>

</div>