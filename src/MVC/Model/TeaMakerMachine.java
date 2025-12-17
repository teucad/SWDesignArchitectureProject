package MVC.Model;

import Database.TeaLogToDB;
import MVC.Model.Observer.Observer;
import MVC.Model.Observer.Subject;
import MVC.Model.State.EmptyState;
import MVC.Model.State.TeaMakerState;
import MVC.Model.Strategy.BrewingMode;
import MVC.Model.Service.CupStatsService;
import MVC.Model.Service.TimerService;

import java.util.ArrayList;
import java.util.List;

public class TeaMakerMachine implements Subject {
    TeaMakerState state = new EmptyState();
    int cups = 0;
    BrewingMode mode;
    public List<Observer> observers;
    TimerService timerService;
    CupStatsService cupStatsService;
    TeaLogToDB teaLogToDB;

    public TeaMakerMachine(CupStatsService cupStatsService, TeaLogToDB teaLogToDB){
        this.timerService = new TimerService();
        this.cupStatsService = cupStatsService;
        this.teaLogToDB = teaLogToDB;
        this.observers = new ArrayList<>();
        mode = null;
    }


    public void setState(TeaMakerState state) {
        this.state = state;
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
        //TODO: Implement this method.
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

    public TeaMakerState getState() {
        return state;
    }
}
