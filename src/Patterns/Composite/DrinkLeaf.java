package Patterns.Composite;

public class DrinkLeaf implements DrinkComponent{
    @Override
    public String getName() {
        return "";
    }

    @Override
    public int getCups() {
        return 0;
    }
}
