package MVC.Model.Decorator;

public class NoChangeCupsMessage implements Message {

    private final Message message;
    public NoChangeCupsMessage(Message message) {
        this.message = message;
    }


    @Override
    public String getMessage() {
        return message.getMessage() + "\nCannot change cups while the machine is working.";
    }
}
