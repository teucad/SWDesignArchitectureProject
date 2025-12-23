package MVC.Model.State;

import MVC.Model.Decorator.DefaultMessage;
import MVC.Model.Decorator.EmptyAlreadyMessage;
import MVC.Model.Decorator.EmptyMessage;
import MVC.Model.Decorator.ResetRequestMessage;
import MVC.Model.TeaMakerMachine;

public class DoneState implements MachineState {

    public static final DoneState INSTANCE = new DoneState();

    @Override
    public void onFilled(TeaMakerMachine machine, int cups) {
        machine.notifyMessage(new EmptyAlreadyMessage(new DefaultMessage(this)));
    }

    @Override
    public void onStart(TeaMakerMachine machine) {
        machine.notifyMessage(new ResetRequestMessage(new DefaultMessage(this)));
    }

    @Override
    public void onBoilWater(TeaMakerMachine machine) {
        machine.notifyMessage(new ResetRequestMessage(new DefaultMessage(this)));
    }

    @Override
    public void onReset(TeaMakerMachine machine) {
        machine.setState(new EmptyState());
        machine.notifyMessage(new EmptyMessage(new DefaultMessage(this)));
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
