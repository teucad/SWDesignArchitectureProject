package Patterns.Observer;

import MVC.Model.TeaMakerMachine;
import MVC.View.TeaMakerFrame;
import Patterns.State.TeaMakerState;

public class TeaMakerObserver implements Observer {

    private final TeaMakerMachine machine;
    private final TeaMakerFrame view;

    public TeaMakerObserver(TeaMakerMachine machine, TeaMakerFrame view) {
        this.machine = machine;
        this.view = view;
    }

    @Override
    public void onStateChanged(TeaMakerState state) {
        machine.setState(state);
    }
    //Todo: Implement the following methods after making the GUI.
    @Override
    public void onButtonsEnabled(boolean filled, boolean start, boolean boil) {
    }

    @Override
    public void onMessageChanged(String message) {

    }

    @Override
    public void onMonthlyTotalChanged(int total) {

    }

}
