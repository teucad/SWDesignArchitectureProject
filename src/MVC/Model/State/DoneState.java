package MVC.Model.State;

import MVC.Model.TeaMakerMachine;

public class DoneState implements MachineState {

    public static final DoneState INSTANCE = new DoneState();

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
        machine.notifyMessage("Please reset the machine to its initial state first.");
    }

    @Override
    public void onReset(TeaMakerMachine machine) {
        machine.setState(new EmptyState());
        machine.notifyMessage("The machine has been emptied. Please fill cups.");
    }

    @Override
    public void onTimerExpired(TeaMakerMachine machine) {
        // No timer is needed when the machine is not running.
        // Therefore, this method was left empty intentionally.
        // Get it?
    }

    @Override
    public String toString() {
        return "DONE";
    }

}
