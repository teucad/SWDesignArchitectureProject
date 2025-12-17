package MVC.Model.Decorator;

public class HealthWarningDecorator extends Message {

    private final DefaultMessage defaultMessage;
    private final int todayTotalCups;

    public HealthWarningDecorator(DefaultMessage defaultMessage, int todayTotalCups) {
        this.defaultMessage = defaultMessage;
        this.todayTotalCups = todayTotalCups;
    }

    @Override
    public String getMessage() {
        return defaultMessage.getMessage() + "\nWARNING: The number of total cups today has reached to " +  todayTotalCups + ".";
    }
}
