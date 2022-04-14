package character;

import item.Clothing;
import item.Inventory;
import item.Item;
import item.Weapon;
import level_design.Dungeon;
import level_design.Room;

import java.util.ArrayList;

public class Hero extends GameCharacter implements Attacker {

    private int score;
    private String name;
    private String gender;

    private boolean isDead;
    private boolean gameWon;

    private Dungeon currentDungeon;
    private Room currentRoom;
    private boolean fromUp;

    private final ArrayList<Dungeon> finishedLevels = new ArrayList<>();
    private final ArrayList<Monster> killedMonsters = new ArrayList<>();
    private final ArrayList<Townspeople> savedTownspeople = new ArrayList<>();

    private Weapon weapon;

    public Hero(String name, String gender, Weapon weapon, Clothing clothing, Inventory inventory, double hp, Dungeon currentDungeon) {
        super("Hero", clothing, inventory, hp);
        this.name = name;
        this.gender = gender;
        this.weapon = weapon;
        this.gameWon = false;
        this.isDead = false;
        this.currentDungeon = currentDungeon;
        this.currentRoom = null;
        this.score = 0;
        this.fromUp = false;
    }

    public void healed(int value) {
        double newHP = super.getHp() + value;

        if (newHP > 15000) {
            super.setHp(15000);
        } else {
            super.setHp(newHP);
        }
    }

    public void gainPoints(int point) {
        this.score += point;
    }

    public void showGeneralStatus() {
        System.out.println();
        System.out.println("xxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx");
        System.out.println();
        System.out.println("You are at; ");
        System.out.println("DUNGEON: " + this.getCurrentDungeon().getDungeonNumber());
        System.out.println("CORRIDOR: " + this.getCurrentRoom().getCorridorNumber() + " , ROOM: " + this.getCurrentRoom().getRoomNumber());

        System.out.println();

        System.out.println("Monsters: " + this.getCurrentRoom().getMonsterList().size() + " , Townspeople: " + this.getCurrentRoom().getTownspeopleList().size());

        System.out.println();

        System.out.println("Equipped WEAPON: " + this.getWeapon().getName() + " , Equipped CLOTHING: " + this.getClothing().getName());
        System.out.printf("Your HP: %.2f\n", this.getHp());

        System.out.println("xxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx");
        System.out.println();
    }

    public ArrayList<Dungeon> getFinishedLevels() {
        return finishedLevels;
    }

    public ArrayList<Monster> getKilledMonsters() {
        return killedMonsters;
    }

    public ArrayList<Townspeople> getSavedTownspeople() {
        return savedTownspeople;
    }

    @Override
    public void attack(GameCharacter character) {
        Monster monster = (Monster) character;

        double damageValue = this.weapon.getWeaponDamage() * this.weapon.getRange();

        // Heavy & Light Attack

        System.out.println();
        System.out.println("--------------------------");
        System.out.printf("You hit the %s (%.2f HP)\n", monster.getName(), damageValue);

        monster.getDamage(damageValue);

        System.out.println();
    }

    @Override
    public void getDamage(double damageValue) {

        double hpValue = damageValue - (damageValue * this.getClothing().getBlocValue()) / 100;

        double newHp = this.getHp() - hpValue;

        this.setHp(newHp);

        System.out.printf("You lost %.2f HP\n", hpValue);

        if (this.getHp() <= 0) {

            System.out.println("You are dead...");
            System.out.println("---XXXX-----XXXX-----XXX---");

            this.setDead(true);
        } else {
            System.out.println("---------------------------");
        }
    }

    @Override
    public ArrayList<Item> dropAllItems() {
        ArrayList<Item> heroItems = this.getInventory().getItems();

        ArrayList<Item> items = new ArrayList<Item>(heroItems);

        items.add(this.weapon);
        items.add(this.getClothing());

        for(Item item : items) {
            System.out.println("- " + item.getName());
        }

        heroItems.clear();

        System.out.println();

        System.out.println("------------------------------------------------------------------------------------------");
        System.out.println("You dropped all of your items... (Their values will be using to calculate your score)");
        System.out.println("------------------------------------------------------------------------------------------");

        System.out.println();

        return items;
    }

    @Override
    public void showHpStatus() {
        System.out.println("Your hp: " + this.getHp());
    }

    public void equipWeapon(Weapon weapon) {
        this.weapon = weapon;
        this.getInventory().getItems().remove(weapon);
        this.getInventory().updateEmptyArea();
    }

    public void equipClothing(Clothing clothing) {
        this.setClothing(clothing);
        this.getInventory().getItems().remove(clothing);
        this.getInventory().updateEmptyArea();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public Weapon getWeapon() {
        return weapon;
    }

    public void setWeapon(Weapon weapon) {
        this.weapon = weapon;
    }

    public Dungeon getCurrentDungeon() {
        return currentDungeon;
    }

    public void setCurrentDungeon(Dungeon currentDungeon) {
        this.currentDungeon = currentDungeon;
    }

    public boolean isFromUp() {
        return fromUp;
    }

    public void setFromUp(boolean fromUp) {
        this.fromUp = fromUp;
    }

    public Room getCurrentRoom() {
        return currentRoom;
    }

    public void setCurrentRoom(Room currentRoom) {
        this.currentRoom = currentRoom;
    }

    public boolean isGameWon() {
        return gameWon;
    }

    public void setGameWon(boolean gameWon) {
        this.gameWon = gameWon;
    }

    public boolean isDead() {
        return isDead;
    }

    public void setDead(boolean dead) {
        isDead = dead;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }
}
