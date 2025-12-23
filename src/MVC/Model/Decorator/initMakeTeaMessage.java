package MVC.Model.Decorator;

public class initMakeTeaMessage implements Message{

    private final Message message;

    public initMakeTeaMessage(Message message) {
        this.message = message;
    }

    @Override
    public String getMessage() {
        return message.getMessage() + "\nThe machine is being initiated to make tea.";
    }
}
