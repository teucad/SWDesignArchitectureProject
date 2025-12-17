package Patterns.Decorator;

import MVC.Model.TeaMakerMachine;

import java.time.LocalDate;

public class BasicMessage extends Message {

    public BasicMessage(TeaMakerMachine machine) {
        super(machine);
    }
    public String buildMsg() {
        return "Day: " + LocalDate.now().getDayOfWeek().toString() + " Date: " + LocalDate.now() + " State:" + this.machine.getState().toString();
    }
}
