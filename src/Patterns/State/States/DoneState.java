package Patterns.State.States;

import MVC.Model.TeaMakerMachine;
import Patterns.State.TeaMakerState;

public class DoneState implements TeaMakerState {
    @Override
    public void onFilled(TeaMakerMachine machine, int cups) {
        machine.notifyMessage("Machine is empty. Please fill cups and try again.");
    }

    @Override
    public void onStart(TeaMakerMachine machine) {
        machine.notifyMessage("Please reset the machine to its initial state first.");
    }

    @Override
    public void onBoilWater(TeaMakerMachine machine) {
        machine.notifyMessage("The machine is boiling water.");
        machine.setState(new BoilingWaterState());
    }

    @Override
    public void onReset(TeaMakerMachine machine) {
        machine.notifyMessage("The machine has been emptied. Please fill cups.");
    }

    @Override
    public void onTimerExpired(TeaMakerMachine machine) {
        // No timer is needed when the machine is not running.
        // Therefore, this method was left empty intentionally.
    }

    @Override
    public String toString() {
        return "DONE";
    }

}
