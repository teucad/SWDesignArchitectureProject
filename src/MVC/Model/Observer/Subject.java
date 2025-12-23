package MVC.Model.Observer;

import MVC.Model.Decorator.Message;

import java.util.function.Consumer;

public interface Subject {

    void addObserver(Consumer<Message> o);
    void removeObserver(Observer o);


}
