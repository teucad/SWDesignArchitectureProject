package Patterns.Strategy.Strategies;

import Patterns.Strategy.BrewingTea;

public class MakeTea implements BrewingTea {
    @Override
    public int getDurationMillis() {
        return 0;
    }

    @Override
    public String getOperationName() {
        return "";
    }
}
