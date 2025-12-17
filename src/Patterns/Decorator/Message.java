package Patterns.Decorator;

import MVC.Model.TeaMakerMachine;

public abstract class Message {

    public TeaMakerMachine machine;
    String msg = "";

    protected Message(TeaMakerMachine machine) {
        this.machine = machine;
    }

    public String getMsg() {
        return msg;
    }



}
