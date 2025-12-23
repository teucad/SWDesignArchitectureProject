package MVC.Model.Decorator;

public class initBoilWaterMessage implements Message{


    private final Message message;

    public initBoilWaterMessage(Message message) {
        this.message = message;
    }


    @Override
    public String getMessage() {
        return message.getMessage() + "\nThe machine is being initiated to boil water.";
    }
}
