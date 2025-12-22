package MVC.Model.Decorator;


public class ResetMessage extends Message {

    DefaultMessage message;

    public ResetMessage(DefaultMessage message) {
        this.message = message;
    }

    @Override
    public String getMessage() {
        return message.getMessage() +"\nReset complete. Machine is now empty.";
    }
}
