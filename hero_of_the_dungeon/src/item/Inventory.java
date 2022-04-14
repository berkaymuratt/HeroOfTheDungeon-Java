package item;

import java.util.ArrayList;

public class Inventory {

    private final ArrayList<Item> items = new ArrayList<>();
    private double currentInventoryWeight = 0;
    private double maxItemWeight;
    private double emptyArea;

    public Inventory(double maxItemWeight) {
        this.maxItemWeight = maxItemWeight;
        this.emptyArea = maxItemWeight - currentInventoryWeight;
    }

    public boolean getItem(Item item) {

        if(emptyArea >= item.getWeight()) {

            this.items.add(item);

            updateEmptyArea();

            System.out.println("The '" + item.getName() + "' added to inventory..");

            return true;

        } else {
            System.out.println("You can't carry that much !");

            return false;
        }

    }

    public void dropItem(Item item) {

        this.items.remove(item);

        updateEmptyArea();

        System.out.println("The '" + item.getName() + "' dropped..");
    }

    public double getEmptyArea() {
        return this.emptyArea;
    }

    public void updateEmptyArea() {

        this.currentInventoryWeight = 0;

        for(Item item : items) {
            this.currentInventoryWeight+=item.getWeight();
        }

        this.emptyArea = this.maxItemWeight - this.currentInventoryWeight;
    }

    public ArrayList<Item> getItems() {
        return items;
    }

    public double getCurrentInventoryWeight() {
        return currentInventoryWeight;
    }

    public void setCurrentInventoryWeight(double currentInventoryWeight) {
        this.currentInventoryWeight = currentInventoryWeight;
    }

    public double getMaxItemWeight() {
        return maxItemWeight;
    }

    public void setMaxItemWeight(double maxItemWeight) {
        this.maxItemWeight = maxItemWeight;
    }

}
