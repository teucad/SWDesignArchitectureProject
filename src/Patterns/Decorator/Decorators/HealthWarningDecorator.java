package Patterns.Decorator.Decorators;

import MVC.Model.TeaMakerMachine;
import Patterns.Decorator.BasicMessage;
import Patterns.Decorator.Message;

public class HealthWarningDecorator extends Message {

    public HealthWarningDecorator(TeaMakerMachine machine) {
        super(machine);
    }

    public String buildMsg() {
        return "";
    }
}
