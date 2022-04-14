package character;

import item.Item;

import java.util.ArrayList;

public interface Attacker {
    void attack(GameCharacter character);
    void getDamage(double damageValue);
    ArrayList<Item> dropAllItems();
    void showHpStatus();
}
