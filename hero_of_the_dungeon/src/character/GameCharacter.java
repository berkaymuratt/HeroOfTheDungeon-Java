package character;

import item.Clothing;
import item.Inventory;

public class GameCharacter {

    private String type;
    private Clothing clothing;
    private Inventory inventory;
    private double hp;

    public GameCharacter(String type, Clothing clothing, Inventory inventory, double hp) {
        this.type = type;
        this.clothing = clothing;
        this.inventory = inventory;
        this.hp = hp;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Clothing getClothing() {
        return clothing;
    }

    public void setClothing(Clothing clothing) {
        this.clothing = clothing;
    }

    public Inventory getInventory() {
        return inventory;
    }

    public void setInventory(Inventory inventory) {
        this.inventory = inventory;
    }

    public double getHp() {
        return hp;
    }

    public void setHp(double hp) {
        this.hp = hp;
    }
}
