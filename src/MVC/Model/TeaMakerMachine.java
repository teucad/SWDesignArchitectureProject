package MVC.Model;

import Database.TeaLogToDB;
import Patterns.Observer.Observer;
import Patterns.Observer.Subject;
import Patterns.Observer.TeaMakerObserver;
import Patterns.State.States.EmptyState;
import Patterns.State.TeaMakerState;
import Patterns.Strategy.BrewingTea;
import Service.CupStatsService;
import Service.TimerService;

import java.util.ArrayList;
import java.util.List;

public class TeaMakerMachine implements Subject {
    TeaMakerState state = new EmptyState();
    int cups = 0;
    BrewingTea strategy;
    public List<Observer> observers;
    TimerService timerService;
    CupStatsService cupStatsService;
    TeaLogToDB teaLogToDB;

    public TeaMakerMachine(CupStatsService cupStatsService, TeaLogToDB teaLogToDB){
        this.timerService = new TimerService();
        this.cupStatsService = cupStatsService;
        this.teaLogToDB = teaLogToDB;
        this.observers = new ArrayList<>();
        strategy = null;
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

    public void setStrategy(BrewingTea strategy) {
        this.strategy = strategy;
    }

    public void notifyMessage(String msg) {
        //TODO: Implement this method.
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
