package MVC.Model.State;

import MVC.Model.Decorator.BoilingWaterMessage;
import MVC.Model.Decorator.DefaultMessage;
import MVC.Model.Decorator.ResetMessage;
import MVC.Model.TeaMakerMachine;

import java.time.LocalDate;

public class BoilingWaterState implements MachineState {

    LocalDate currentDate = LocalDate.now();

    @Override
    public void onFilled(TeaMakerMachine machine, int cups) {
        machine.notifyMessage(new BoilingWaterMessage(new DefaultMessage(currentDate, this)).getMessage());
    }

    @Override
    public void onStart(TeaMakerMachine machine) {
        machine.notifyMessage(new BoilingWaterMessage(new DefaultMessage(currentDate, this)).getMessage());
    }

    @Override
    public void onBoilWater(TeaMakerMachine machine) {
        machine.setState(new BoilingWaterState());
        machine.enableDisableButtons(false, false, false);
        machine.notifyMessage(new BoilingWaterMessage(new DefaultMessage(currentDate, this)).getMessage());
    }

    @Override
    public void onReset(TeaMakerMachine machine) {
        machine.stopTimer();
        machine.setState(new EmptyState());
        machine.notifyMessage(new ResetMessage(new DefaultMessage(currentDate, this)).getMessage());
    }

    @Override
    public void onTimerExpired(TeaMakerMachine machine) {
        machine.setState(new DoneState());
        machine.notifyMessage("The machine has boiled your water. Enjoy.");

    }

    @Override
    public String toString() {
        return "BOILING WATER";
    }
}
