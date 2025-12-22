package MVC.Model.Decorator;

public class BoilingWaterMessage extends Message {

    DefaultMessage message;

    public BoilingWaterMessage(DefaultMessage message) {
        this.message =  message;
    }

    @Override
    public String getMessage() {
        message.extendMessage("\nBUSY: Boiling Water.");
        return message.getMessage();
    }
}
