package MVC.Model.Decorator;


import MVC.Model.State.MachineState;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

public class DefaultMessage implements Message {

    private final MachineState state;
    private final String message;

    public DefaultMessage(MachineState state) {
        LocalDate date = LocalDate.now();
        String day = computeDayOfWeek(date);
        this.state = state;
        String dateAsStr = date.format(DateTimeFormatter.ISO_LOCAL_DATE);
        this.message =  "Day: " + day + " | Date: " + dateAsStr + " | State:" + state.toString();
    }

    @Override
    public String getMessage() {
        return message;
    }

    public static String computeDayOfWeek(LocalDate date) {
        return date.getDayOfWeek().getDisplayName(java.time.format.TextStyle.FULL, Locale.ENGLISH);
    }


}
