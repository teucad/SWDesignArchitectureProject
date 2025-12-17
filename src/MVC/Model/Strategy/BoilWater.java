package MVC.Model.Strategy;

public class BoilWater implements BrewingMode {
    @Override
    public int getDurationMillis() {
        return 5000;
    }

    @Override
    public String getOperationName() {
        return "BOILING WATER";
    }
}
