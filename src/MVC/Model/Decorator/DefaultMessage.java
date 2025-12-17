package MVC.Model.Decorator;


import MVC.Model.State.TeaMakerState;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

public class DefaultMessage extends Message {

    private final String day;
    private final LocalDate date;
    private final TeaMakerState state;

    public DefaultMessage(LocalDate date, TeaMakerState state) {
        this.day = computeDayOfWeek(date);
        this.date = date;
        this.state = state;
    }

    @Override
    public String getMessage() {
        String dateAsStr = date.format(DateTimeFormatter.ISO_LOCAL_DATE);
        return "Day: " + day + " | Date: " + dateAsStr + " | State:" + state.toString();
    }

    public static String computeDayOfWeek(LocalDate date) {
        return date.getDayOfWeek().getDisplayName(java.time.format.TextStyle.FULL, Locale.ENGLISH);
    }

}
