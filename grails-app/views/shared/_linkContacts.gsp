<div id="link-contact-dialog" title="Link Contact to an Event" style="display: none;">
<g:textField name="contactNameSearch" id="contact-name-search"/>
<g:form action="">
    <p>Search by name or mobile phone</p>

    <div id="link-contacts-inner-table-div" style="overflow: auto;">
        <table id="contactsTable">
            <thead>
            <tr>
                <th>Name</th>
                <th>Number</th>
            </tr>
            </thead>
            <tbody>
            <g:each in="${contactList}" var="contact">
                <tr id="${contact.id}" class="contactRow">

                    <td style="border: 1px black solid" class="contact-name">
                        <%=contact.name%>
                    </td>

                    <td style="border: 1px black solid" class="contact-number">
                        <%=contact.primaryMobile%>
                    </td>
                    <td style="text-decoration: underline" class="contactLink" id="${contact.id}">
                        <a href="#">Link</a>
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