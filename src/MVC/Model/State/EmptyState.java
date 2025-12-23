package MVC.Model.State;

import MVC.Model.Decorator.DefaultMessage;
import MVC.Model.Decorator.EmptyAlreadyMessage;
import MVC.Model.Decorator.FillCupsMessage;
import MVC.Model.Decorator.ReadyMessage;
import MVC.Model.TeaMakerMachine;

public class EmptyState implements MachineState {

    public static final EmptyState INSTANCE = new EmptyState();

    @Override
    public void onFilled(TeaMakerMachine machine, int cups) {
        if(cups <= 0) {
            // Machine, turn back NOW. The layers of this palace are not for your kind.
            // Turn back, or you will be facing THE. WILL. OF. GOD.
            return;
        }
        machine.setCups(cups);
        machine.setState(new IdleState());
        machine.notifyMessage(new ReadyMessage(new DefaultMessage(this), cups));
    }

    @Override
    public void onStart(TeaMakerMachine machine) {
        machine.notifyMessage(new FillCupsMessage(new DefaultMessage(this)));
    }

    @Override
    public void onBoilWater(TeaMakerMachine machine) {
        machine.notifyMessage(new  FillCupsMessage(new DefaultMessage(this)));
    }

    @Override
    public void onReset(TeaMakerMachine machine) {
        machine.notifyMessage(new EmptyAlreadyMessage(new  DefaultMessage(this)));
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
