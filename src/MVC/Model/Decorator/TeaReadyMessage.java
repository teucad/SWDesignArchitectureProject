package MVC.Model.Decorator;

public class TeaReadyMessage implements Message {

    private final Message message;

    public TeaReadyMessage(Message message) {
        this.message = message;
    }

    @Override
    public String getMessage() {
        return message.getMessage() + "\nYour tea is ready! Enjoy.";
    }
}
