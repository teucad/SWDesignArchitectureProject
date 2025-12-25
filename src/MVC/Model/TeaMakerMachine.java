package MVC.Model;

import MVC.Controller.Database.TeaLogToDB;
import MVC.Model.Decorator.DefaultMessage;
import MVC.Model.Decorator.Message;
import MVC.Model.Observer.Observer;
import MVC.Model.Observer.Subject;
import MVC.Model.Service.CupStatsService;
import MVC.Model.Service.TimerService;
import MVC.Model.State.*;
import MVC.Model.Strategy.BrewingMode;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

public class TeaMakerMachine implements Subject {
    MachineState state = new EmptyState();
    int cups = 0;
    BrewingMode mode;
    Message message;
    public List<Consumer<Message>> observers;
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


    public void setState(MachineState newState) {
        MachineState old = this.state;
        this.state = newState;
        pcs.firePropertyChange("state", old, newState);
    }

    public void addPropertyChangeListener(java.beans.PropertyChangeListener listener) {
        pcs.addPropertyChangeListener(listener);
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

    public void notifyMessage(Message m) {
        for (var l : observers) l.accept(m);
    }

    public Message getMessage() {
        return message;
    }


    public String getMessageString() {
        return message.getMessage();
    }

    public void setMessage(Message message) {
        this.message = message;
    }

    //Todo: Implement the following methods after making the GUI.


    @Override
    public void addObserver(Consumer<Message> o) {
     observers.add(o);
    }

    public BrewingMode getMode() {
        return mode;
    }



    @Override
    public void removeObserver(Observer o) {
        observers.remove(o);
    }

    public void stopTimer() {
        timerService.stopTimer();
    }

    public void startTimer() {
        if (mode == null) {
            return;
        }
        timerService.startTimer(mode.getDurationMillis(), this::timerExpired);
    }



    public MachineState getState() {
        return state;
    }
}
