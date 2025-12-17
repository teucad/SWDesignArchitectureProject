package MVC.Model.Strategy;

public class MakeTea implements BrewingMode {
    @Override
    public int getDurationMillis() {
        return 10000;
    }

    @Override
    public String getOperationName() {
        return "MAKING TEA";
    }
}
