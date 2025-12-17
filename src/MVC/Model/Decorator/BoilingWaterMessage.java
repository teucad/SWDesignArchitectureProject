package MVC.Model.Decorator;

public class BoilingWaterMessage extends Message {

    Message message;

    public BoilingWaterMessage(Message message) {
        this.message =  message;
    }

    @Override
    public String getMessage() {
        return message.getMessage() + "\nBUSY: Boiling Water";
    }
}
