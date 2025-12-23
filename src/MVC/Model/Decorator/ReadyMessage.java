package MVC.Model.Decorator;

public class ReadyMessage implements Message {

    private final Message message;
    private final int cups;

    public ReadyMessage(Message message, int cups) {
        this.message = message;
        this.cups = cups;
    }

    @Override
    public String getMessage() {
        return message.getMessage() + "\nThe machine is ready to make " + cups + " cups of tea.";
    }
}
