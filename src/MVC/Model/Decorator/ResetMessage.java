package MVC.Model.Decorator;


public class ResetMessage extends Message {

    Message message;

    public ResetMessage(Message message) {
        this.message = message;
    }

    @Override
    public String getMessage() {
        return message.getMessage() +"\nReset complete. Machine is now empty.";
    }
}
