package MVC.Model.Decorator;

public class BoilingWaterMessage implements Message {

    Message message;

    public BoilingWaterMessage(DefaultMessage message) {
        this.message =  message;
    }

    @Override
    public String getMessage() {
        return message.getMessage() + "\nBoiling Water..";
    }
}
