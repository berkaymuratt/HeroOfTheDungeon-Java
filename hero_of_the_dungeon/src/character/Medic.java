package character;

import item.Clothing;
import item.Inventory;

import java.security.SecureRandom;

public class Medic extends Townspeople {

    public Medic(Clothing clothing, Inventory inventory, double hp) {
        super("Medic", clothing, inventory, hp);
    }

    @Override
    public void giveAward(Hero hero) {
        super.giveAward(hero);

        SecureRandom random = new SecureRandom();

        int value = random.nextInt(301) + 200; //200....500

        hero.healed(value);
    }
}
