<div id="link-contact-dialog" title="Link Contact to an Event" style="display: none;">
<g:textField name="contactNameSearch" id="contact-name-search"/>
<g:form action="">
        <p>Enter the name or the mobile number to search for Contacts</p>
        <div id="link-contacts-inner-table-div" style="height:200px;overflow: scroll; width:250px">
            <table id="contactsTable">
                <thead>
                <tr>
                    <td>Name</td>
                    <td>Number</td>
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
                        <td class="contactLink" id="${contact.id}"><a href="#">Link Contact</a></td>
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