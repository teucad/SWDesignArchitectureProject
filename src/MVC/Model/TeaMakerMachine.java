package MVC.Model;

import MVC.Controller.Database.TeaLogToDB;
import MVC.Model.Decorator.DefaultMessage;
import MVC.Model.Decorator.Message;
import MVC.Model.Observer.Observer;
import MVC.Model.Observer.Subject;
import MVC.Model.Service.CupStatsService;
import MVC.Model.Service.TimerService;
import MVC.Model.State.EmptyState;
import MVC.Model.State.IdleState;
import MVC.Model.State.MachineState;
import MVC.Model.Strategy.BrewingMode;
import java.util.ArrayList;
import java.util.List;

public class TeaMakerMachine implements Subject {
    MachineState state = new EmptyState();
    int cups = 0;
    BrewingMode mode;
    DefaultMessage message;
    public List<Observer> observers;
    TimerService timerService;
    CupStatsService cupStatsService;
    TeaLogToDB teaLogToDB;
    private final java.beans.PropertyChangeSupport pcs = new java.beans.PropertyChangeSupport(this);

    public TeaMakerMachine(CupStatsService cupStatsService, TeaLogToDB teaLogToDB){
        this.timerService = new TimerService();
        this.cupStatsService = cupStatsService;
        this.teaLogToDB = teaLogToDB;
        this.observers = new ArrayList<>();
        this.message = new DefaultMessage(state);
        mode = null;
    }

    public void addStateListener(java.beans.PropertyChangeListener l) {
        pcs.addPropertyChangeListener("state", l);
    }

    public void setState(MachineState newState) {
        MachineState old = this.state;
        this.state = newState;
        pcs.firePropertyChange("state", old, newState);
    }

    public void filled(int cups) {
        state.onFilled(this, cups);
    }

    public void setCups(int cups) {
        this.cups = cups;
    }

    public void startTea() {
        state.onStart(this);
    }

    public void boilWater() {
        state.onBoilWater(this);
    }
    public void reset() {
        state.onReset(this);
    }

    public void timerExpired() {
        state.onTimerExpired(this);
    }

    public void setStrategy(BrewingMode mode) {
        this.mode = mode;
    }

    public void notifyMessage(String msg) {
        message.extendMessage(msg);
    }

    public Message getMessage() {
        return message;
    }


    public String getMessageString() {
        return message.getMessage();
    }

    public void setMessage(DefaultMessage message) {
        this.message = message;
    }

    //Todo: Implement the following methods after making the GUI.
    public void enableDisableButtons(boolean filled, boolean start, boolean boil) {

    }


    @Override
    public void addObserver(Observer o) {
     observers.add(o);
    }

    @Override
    public void removeObserver(Observer o) {
        observers.remove(o);
    }

    public void stopTimer() {
        timerService.stopTimer();
    }

    public MachineState getState() {
        return state;
    }
}
