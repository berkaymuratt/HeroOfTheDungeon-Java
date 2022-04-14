package item;

import java.security.SecureRandom;
import java.util.ArrayList;

public class AllClothing {

    private final static Clothing lightClothing = new Clothing("Light Clothing", 5, 13, 8);
    private final static Clothing mediumClothing = new Clothing("Medium Clothing", 8, 17, 13);
    private final static Clothing heavyClothing = new Clothing("Heavy Clothing", 11, 15, 18);

    private final static ArrayList<Clothing> clothingList = new ArrayList<>();

    public static void createItems() {
        clothingList.add(lightClothing);
        clothingList.add(mediumClothing);
        clothingList.add(heavyClothing);
    }

    public static Clothing getRandomClothing() {

        SecureRandom random = new SecureRandom();

        int listLength = clothingList.size();

        int index = random.nextInt(listLength);

        return clothingList.get(index);
    }

    public static Clothing getLightClothing() {
        return lightClothing;
    }

    public static Clothing getMediumClothing() {
        return mediumClothing;
    }

    public static Clothing getHeavyClothing() {
        return heavyClothing;
    }
}
