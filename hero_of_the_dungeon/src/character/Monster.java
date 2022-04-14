package character;

import item.Clothing;
import item.Inventory;
import item.Item;
import item.Weapon;

import java.security.SecureRandom;
import java.text.DecimalFormat;
import java.util.ArrayList;

public class Monster extends GameCharacter implements Attacker {

    private Weapon weapon;
    private String name;
    private boolean isDead;

    private final double normalHP;

    public static ArrayList<String> monsterNames = new ArrayList<>();

    public static void addNamesToList() {
        monsterNames.add("Ikurd");
        monsterNames.add("Zizgurd");
        monsterNames.add("Urlanak");
        monsterNames.add("Ugnuk");
        monsterNames.add("Vagtogus");
        monsterNames.add("Nugard");
        monsterNames.add("Orozak");
        monsterNames.add("Azagar");
        monsterNames.add("Darguzak");
        monsterNames.add("Grabom");
    }

    public static String getRandomName() {

        SecureRandom random = new SecureRandom();

        int index = random.nextInt(monsterNames.size() - 1);

        return monsterNames.get(index);
    }

    public Monster(String name, Weapon weapon, Clothing clothing, Inventory inventory, double hp) {
        super("Monster", clothing, inventory, hp);
        this.weapon = weapon;
        this.name = name;
        this.normalHP = hp;
    }

    @Override
    public void attack(GameCharacter character) {

        Hero hero = (Hero) character;

        double damageValue = this.weapon.getWeaponDamage() * this.weapon.getRange();

        System.out.println();
        System.out.println("--------------------------");
        System.out.printf("%s hit you (%.2f HP)\n", this.getName(), damageValue);

        hero.getDamage(damageValue);

        System.out.println();
    }

    @Override
    public void getDamage(double damageValue) {

        double hpValue = damageValue - (damageValue * this.getClothing().getBlocValue()) / 100;

        double newHp = this.getHp() - hpValue;

        this.setHp(newHp);

        System.out.printf("%s lost %.2f HP\n", this.getName(), hpValue);

        System.out.println();
        this.showHpStatus();

        if (this.getHp() <= 0) {
            System.out.printf("%s is dead...\n", this.getName());
            System.out.println("---XXXX-----XXXX-----XXX---");

            this.setDead(true);
        } else {
            System.out.println("---------------------------");
        }
    }

    @Override
    public ArrayList<Item> dropAllItems() {

        ArrayList<Item> monsterItems = this.getInventory().getItems();

        ArrayList<Item> items = new ArrayList<Item>();

        System.out.println("DROPPED ITEMS:");

        for (Item item : monsterItems) {
            System.out.println("- " + item.getName() + "(From Inventory)");

            items.add(item);
        }

        monsterItems.clear();

        System.out.println("- " + this.weapon.getName() + "(From Body)");
        System.out.println("- " + this.getClothing().getName() + "(From Body)");

        items.add(this.weapon);
        items.add(this.getClothing());

        System.out.println();

        System.out.println("-------------------------------------");
        System.out.println("Now you can get " + this.name + "'s items from the ground.. ");
        System.out.println("--------------------------------------");

        System.out.println();

        return items;
    }

    @Override
    public void showHpStatus() {

        double value = this.normalHP/3;

        double currentHp = this.getHp();

        if(currentHp <= value*3 && currentHp>value*2) {
            System.out.println(this.name + " glares at you. It doesn't look like much damage has been done to the monster");
        } else if(currentHp <= value*2 && currentHp>value) {
            System.out.println("You annoyed the " + this.name + " a bit. " + this.name + " is frowning at you. The monster has minor scratches on it.");
        } else if(currentHp <= value && currentHp>0) {
            System.out.println(this.name + " is angry and shouts some kind of war cry. He sees you as a warrior. There are bleeding wounds on the monster.");
        } else if(currentHp <= 0) {
            System.out.println("As " + this.name + " dies, " + this.name + " begins to swell and then explodes. The monster's leftover body parts melts into the ground.");
        }


    }

    @Override
    public String toString() {

        DecimalFormat df2 = new DecimalFormat("#.##");

        return this.getName() + " (" + df2.format(this.getHp()) + " HP, " + this.getWeapon().getName() + ", " + this.getClothing().getName() + ")";
    }

    public Weapon getWeapon() {
        return weapon;
    }

    public void setWeapon(Weapon weapon) {
        this.weapon = weapon;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isDead() {
        return isDead;
    }

    public void setDead(boolean dead) {
        isDead = dead;
    }
}
