package character;

import item.Clothing;
import item.Inventory;

public class Townspeople extends GameCharacter {

    private String peopleType;

    public Townspeople(String peopleType, Clothing clothing, Inventory inventory, double hp) {
        super("Townspeople", clothing, inventory, hp);
        this.peopleType = peopleType;
    }

    public String getPeopleType() {
        return peopleType;
    }

    public void setPeopleType(String peopleType) {
        this.peopleType = peopleType;
    }

    public void giveAward(Hero hero) {
        hero.gainPoints(5);
    }
}
