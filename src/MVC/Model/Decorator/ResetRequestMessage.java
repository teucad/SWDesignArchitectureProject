package MVC.Model.Decorator;

public class ResetRequestMessage implements Message {

    Message message;
    public ResetRequestMessage(Message message) {
        this.message = message;
    }


    @Override
    public String getMessage() {
        return message.getMessage() + "\nPlease reset the machine first.";
    }
}
