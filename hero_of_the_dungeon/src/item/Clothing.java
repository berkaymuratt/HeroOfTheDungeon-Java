package item;

public class Clothing extends Item {

    private double blocValue;

    public Clothing(String name, double blocValue, int value, double weight) {
        super(name, "Clothing", value, weight);
        this.blocValue = blocValue;
    }

    public double getBlocValue() {
        return blocValue;
    }

    public void setBlocValue(double blocValue) {
        this.blocValue = blocValue;
    }
}
