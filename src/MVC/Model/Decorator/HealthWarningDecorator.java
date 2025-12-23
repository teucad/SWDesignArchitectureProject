package MVC.Model.Decorator;

import MVC.Model.State.MachineState;

public class HealthWarningDecorator implements Message {

    private final Message message;
    private final MachineState machineState;
    private final int todayTotalCups;

    public HealthWarningDecorator(Message message, int todayTotalCups, MachineState machineState) {
        this.message = message;
        this.machineState = machineState;
        this.todayTotalCups = todayTotalCups;
    }

    @Override
    public String getMessage() {
        return message.getMessage() + "\nWARNING: The number of total cups today has reached to " + todayTotalCups + ".";
    }

}
