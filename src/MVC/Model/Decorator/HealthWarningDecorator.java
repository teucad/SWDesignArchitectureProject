package MVC.Model.Decorator;

import MVC.Model.State.MachineState;

public class HealthWarningDecorator extends DefaultMessage {

    private final DefaultMessage defaultMessage;
    private final MachineState machineState;
    private final int todayTotalCups;

    public HealthWarningDecorator(DefaultMessage defaultMessage, int todayTotalCups, MachineState machineState) {
        this.defaultMessage = defaultMessage;
        this.machineState = machineState;
        this.todayTotalCups = todayTotalCups;
        super(machineState);
    }

    @Override
    public String getMessage() {
        return defaultMessage.getMessage() + "\nWARNING: The number of total cups today has reached to " +  todayTotalCups + ".";
    }
}
