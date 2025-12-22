package MVC.Model.State;

import MVC.Model.TeaMakerMachine;
import MVC.Model.Strategy.BoilWater;
import MVC.Model.Strategy.MakeTea;

public class IdleState implements MachineState {

    public static final IdleState INSTANCE = new IdleState();

    @Override
    public void onFilled(TeaMakerMachine machine, int cups) {
        if(cups < 0) {
            machine.notifyMessage("Please enter a valid number of cups.");
            return;
        }

        machine.setCups(cups);
        machine.notifyMessage("Cup amount were updated to: " + cups);
    }

    @Override
    public void onStart(TeaMakerMachine machine) {
        machine.setStrategy(new MakeTea());
        machine.setState(new MakingTeaState());
        machine.notifyMessage("The machine is being initiated to make tea.");
    }

    @Override
    public void onBoilWater(TeaMakerMachine machine) {
        machine.setStrategy(new BoilWater());
        machine.setState(new BoilingWaterState());
        machine.notifyMessage("The machine is being initiated to boil water.");
    }

    @Override
    public void onReset(TeaMakerMachine machine) {
        machine.stopTimer();
        machine.setState(new EmptyState());
        machine.notifyMessage("The machine has been emptied. Please fill cups.");
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
