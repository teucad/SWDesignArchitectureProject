package MVC.Model.Decorator;

public class BoilingWaterMessage extends Message {

    DefaultMessage message;

    public BoilingWaterMessage(DefaultMessage message) {
        this.message =  message;
    }

    @Override
    public String getMessage() {
        return message.getMessage() + "\nBUSY: Boiling Water";
    }
}
