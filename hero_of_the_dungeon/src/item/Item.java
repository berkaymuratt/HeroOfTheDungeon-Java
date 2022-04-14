package item;

public class Item {

    private String name;
    private String type;
    private int value;
    private double weight;

    public Item(String name, String type, int value, double weight) {
        this.name = name;
        this.type = type;
        this.value = value;
        this.weight = weight;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }

    public double getWeight() {
        return weight;
    }

    public void setWeight(double weight) {
        this.weight = weight;
    }

    @Override
    public String toString() {
        return "Name: " + this.name + "\n" +
                "Type: " + this.weight + "\n" +
                "Weight: " + this.weight + "\n";
    }
}
