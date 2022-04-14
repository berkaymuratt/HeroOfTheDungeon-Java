package character;

import item.Clothing;
import item.Inventory;
import item.Item;

import java.security.SecureRandom;

public class Blacksmith extends Townspeople {

    public Blacksmith(Clothing clothing, Inventory inventory, double hp) {
        super("Blacksmith", clothing, inventory, hp);
    }

    public Item dropGift() {

        SecureRandom random = new SecureRandom();

        int itemsLength = this.getInventory().getItems().size();

        int randomIndex = random.nextInt(itemsLength);

        Item item = this.getInventory().getItems().get(randomIndex);

        this.getInventory().dropItem(item);

        return item;
    }
}
