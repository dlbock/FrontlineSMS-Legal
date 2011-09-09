package frontlinesms.legal.functionaltests.pages

import geb.Module
import org.openqa.selenium.Keys

class DateField extends Module {
    static content = {
        datePicker { page.$("#ui-datepicker-div") }

        openDatePicker {
            $().jquery.focus()
            $().click()
            waitFor { datePicker.displayed }
        }

        setDate { dayOfMonth ->
            openDatePicker
            datePicker.$(".ui-state-default").find{ it.text() == dayOfMonth as String }.click()
            true
        }

        setValue { value ->
            $().value(value)
            true
        }

        selectCurrentDate {
            $() << Keys.RETURN
            waitFor { !datePicker.displayed }
        }

        clear {
            $().value("")
            $() << Keys.DELETE
            true
        }

        value {
            $().value()
        }
    }
}
