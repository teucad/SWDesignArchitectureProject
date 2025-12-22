package MVC.Model.State;

import MVC.Model.TeaMakerMachine;

public class EmptyState implements MachineState {

    public static final EmptyState INSTANCE = new EmptyState();

    @Override
    public void onFilled(TeaMakerMachine machine, int cups) {
        if(cups < 0) {
            // Machine, turn back NOW. The layers of this palace are not for your kind.
            // Turn back, or you will be facing THE. WILL. OF. GOD.
            machine.notifyMessage("Please enter a valid number of cups.");
            return;
        }
        machine.setCups(cups);
        machine.setState(new IdleState());

        machine.notifyMessage("The machine is ready to make: " + cups + " cups of tea.");
    }

    @Override
    public void onStart(TeaMakerMachine machine) {
        machine.notifyMessage("Warning: Please fill cups first.");
    }

    @Override
    public void onBoilWater(TeaMakerMachine machine) {
        machine.notifyMessage("Warning: Please fill cups first.");
    }

    @Override
    public void onReset(TeaMakerMachine machine) {
        machine.notifyMessage("Machine is already empty.");
    }

    @Override
    public void onTimerExpired(TeaMakerMachine machine) {
        // No timer is needed when the machine is not running.
        // Therefore, this method was left empty intentionally.
    }

    @Override
    public String toString() {
        return "EMPTY";
    }
}
