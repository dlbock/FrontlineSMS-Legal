<div id="link-contact-dialog" title="Link Contact" style="display: none;">
<p id="message">Search by name or phone number</p>
<br>
<g:textField name="contactNameSearch" id="contact-name-search"/>
<g:form action="">
        <table id="contactsTable">
            <thead>
            <tr>
                <th class="table-contact-name-column">Name</th>
                <th class="table-contact-number-column">Number</th>
            </tr>
            </thead>
            <tbody>
            <g:each in="${contactList}" var="contact">
                <tr id="${contact.id}" class="contactRow">

                    <td class="contact-name">
                        <%=contact.name%>
                    </td>

                    <td class="contact-number">
                        <%=contact.primaryMobile%>
                    </td>
                    <td class="contactLink" id="${contact.id}">
                        <a href="#">Link</a>
                    </td>
                </tr>
            </g:each>
            </tbody>
        </table>
    </td>
        </tr>
        </tbody>
    </table>

</g:form>
</div>