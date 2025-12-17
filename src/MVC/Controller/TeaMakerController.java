package MVC.Controller;

import MVC.Model.Observer.Observer;
import MVC.Model.State.TeaMakerState;
import MVC.Model.TeaMakerMachine;
import MVC.View.TeaMakerFrame;

public class TeaMakerController implements Observer {
    private final TeaMakerMachine machine;
    private final TeaMakerFrame view;

    public TeaMakerController(TeaMakerMachine machine, TeaMakerFrame view) {
        this.machine = machine;
        this.view = view;
    }

    @Override
    public void onStateChanged(TeaMakerState state) {
        machine.setState(state);
    }


    @Override
    public void onMessageChanged(String message) {

    }

    @Override
    public void onMonthlyTotalChanged(int total) {

    }
}
