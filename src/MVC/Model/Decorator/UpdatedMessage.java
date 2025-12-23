package MVC.Model.Decorator;

public class UpdatedMessage implements Message {

    private final Message message;
    private final int cups;

    public UpdatedMessage(Message message, int cups) {
        this.message = message;
        this.cups = cups;
    }

    @Override
    public String getMessage() {
        return message.getMessage() + "\nAmount of cups was updated to: "+ cups + " Cups.";
    }
}
