package Patterns.Decorator.Decorators;

import MVC.Model.TeaMakerMachine;
import Patterns.Decorator.Message;

public class WarningDecorator extends Message {
    protected WarningDecorator(TeaMakerMachine teaMakerMachine) {
        super(teaMakerMachine);
    }

    public String buildMsg() {
        return "";
    }
}
