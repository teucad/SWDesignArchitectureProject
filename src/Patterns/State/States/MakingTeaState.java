package Patterns.State.States;

import MVC.Model.TeaMakerMachine;
import Patterns.State.TeaMakerState;

public class MakingTeaState implements TeaMakerState {
    @Override
    public void onFilled(TeaMakerMachine machine, int cups) {
        machine.notifyMessage("Cannot change cups while making tea.");
    }

    @Override
    public void onStart(TeaMakerMachine machine) {
        machine.notifyMessage("Busy: Making tea.");
    }

    @Override
    public void onBoilWater(TeaMakerMachine machine) {
        machine.notifyMessage("Busy: Making tea.");
    }

    @Override
    public void onReset(TeaMakerMachine machine) {
        machine.stopTimer();
        machine.setState(new EmptyState());
        machine.notifyMessage("Reset complete. Machine is now empty.");
    }

    @Override
    public void onTimerExpired(TeaMakerMachine machine) {
        machine.setState(new DoneState());
    }

    @Override
    public String toString() {
        return "MAKING TEA";
    }

}
