package MVC.Model.Decorator;

public class EmptyMessage implements Message {

    Message message;
    public EmptyMessage(Message message) {
        this.message = message;
    }
    @Override
    public String getMessage() {
        return message.getMessage() +  "\nThe machine has been emptied. Please fill cups.";
    }

}
