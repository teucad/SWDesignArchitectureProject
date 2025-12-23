package MVC.Model.Decorator;

public class EmptyAlreadyMessage implements Message {

    private final Message message;
    public EmptyAlreadyMessage(Message message) {
        this.message = message;
    }

    @Override
    public String getMessage() {
        return message.getMessage() + "\nMachine is already empty.";
    }
}
