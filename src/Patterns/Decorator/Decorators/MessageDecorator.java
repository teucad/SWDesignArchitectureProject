package Patterns.Decorator.Decorators;

import MVC.Model.TeaMakerMachine;
import Patterns.Decorator.Message;

public abstract class MessageDecorator extends Message {


    protected MessageDecorator(TeaMakerMachine teaMakerMachine) {
        super(teaMakerMachine);
    }

    public String buildMsg() {
        return null;
    }
}
