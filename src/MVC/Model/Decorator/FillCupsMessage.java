package MVC.Model.Decorator;

public class FillCupsMessage implements Message{

    private final Message message;


    public FillCupsMessage(Message message) {
        this.message = message;
    }


    @Override
    public String getMessage() {
        return message.getMessage() + "\nWarning: Please fill cups first.";
    }
}
