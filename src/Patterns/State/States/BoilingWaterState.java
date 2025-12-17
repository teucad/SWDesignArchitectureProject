package Patterns.State.States;

import MVC.Model.TeaMakerMachine;
import Patterns.State.TeaMakerState;

public class BoilingWaterState implements TeaMakerState {

    @Override
    public void onFilled(TeaMakerMachine machine, int cups) {
        machine.notifyMessage("Cannot change cups while boiling water.");
    }

    @Override
    public void onStart(TeaMakerMachine machine) {
        machine.notifyMessage("Busy: Boiling water.");
    }

    @Override
    public void onBoilWater(TeaMakerMachine machine) {
        machine.notifyMessage("Busy: Boiling water.");

    }

    @Override
    public void onReset(TeaMakerMachine machine) {
        machine.stopTimer();
        machine.setState(new EmptyState());
        machine.notifyMessage("Reset complete. Machine is empty now.");
    }

    @Override
    public void onTimerExpired(TeaMakerMachine machine) {
        machine.setState(new DoneState());

    }

    @Override
    public String toString() {
        return "BOILING WATER";
    }
}
