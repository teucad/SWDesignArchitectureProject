package Patterns.Composite;

import java.util.List;

public class DrinkComposite implements DrinkComponent{

    @Override
    public String getName() {
        return "";
    }

    @Override
    public int getCups() {
        return 0;
    }

    public void add(DrinkComponent component){}

    public void remove(DrinkComponent component){}

    public List<DrinkComponent> getComponents(){return null;}

}
