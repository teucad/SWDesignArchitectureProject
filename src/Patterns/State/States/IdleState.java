package Patterns.State.States;

import MVC.Model.TeaMakerMachine;
import Patterns.State.TeaMakerState;
import Patterns.Strategy.Strategies.BoilWater;
import Patterns.Strategy.Strategies.MakeTea;

public class IdleState implements TeaMakerState {
    @Override
    public void onFilled(TeaMakerMachine machine, int cups) {
        if(cups < 0) {
            machine.notifyMessage("Please enter a valid number of cups.");
            return;
        }

        machine.setCups(cups);
        machine.notifyMessage("Cups were updated to: " + cups);
    }

    @Override
    public void onStart(TeaMakerMachine machine) {
        machine.setStrategy(new MakeTea());
        machine.setState(new MakingTeaState());
    }

    @Override
    public void onBoilWater(TeaMakerMachine machine) {
        machine.setStrategy(new BoilWater());
        machine.setState(new BoilingWaterState());
    }

    @Override
    public void onReset(TeaMakerMachine machine) {
        machine.stopTimer();
        machine.setState(new EmptyState());
    }

    @Override
    public void onTimerExpired(TeaMakerMachine machine) {
        // No timer is needed when the machine is not running.
        // Therefore, this method was left empty intentionally.
    }

    @Override
    public String toString() {
        return "IDLE";
    }


}
