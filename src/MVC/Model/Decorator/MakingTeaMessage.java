package MVC.Model.Decorator;

public class MakingTeaMessage implements Message{

    private final Message message;

    public MakingTeaMessage(Message message) {
        this.message = message;
    }


    @Override
    public String getMessage() {
        return message.getMessage() + "\nMaking tea..";
    }
}
