package MVC.Model.Decorator;

import MVC.Model.TeaMakerMachine;

public class JobDoneMessage implements Message {

    private final TeaMakerMachine machine;
    private final Message message;
    public JobDoneMessage(Message message, TeaMakerMachine machine) {
        this.message = message;
        this.machine = machine;
    }


    @Override
    public String getMessage() {
        return message.getMessage() + "\nJob: " + machine.getMode().getOperationName() + " has been completed.";
    }
}
