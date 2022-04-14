package item;

import java.security.SecureRandom;
import java.util.ArrayList;

public class AllWeapons {

    //--Swords--
    private final static Weapon shortSword = new Weapon("Short Sword", "Sword", 20, 1.3, 13, 5);
    private final static Weapon longSword = new Weapon("Long Sword", "Sword", 30, 1.6, 15, 10);
    private final static Weapon dagger = new Weapon("Dagger", "Sword", 10, 1.0, 10, 1.5);

    //--Axes--
    private final static Weapon oneHandedAxe = new Weapon("One Handed Axe", "Axe", 18, 1.3, 15, 8);
    private final static Weapon doubleHandedAxe = new Weapon("Double Headed Axe", "Axe",28, 1.6, 17, 13);
    private final static Weapon twoHandedAxe = new Weapon("Two Handed Axe", "Axe", 12, 1.0, 12, 4.5);

    //--Bows--
    private final static Weapon shortBow = new Weapon("Short Bow", "Bow", 8, 4.1, 11, 4);
    private final static Weapon longBow = new Weapon("Long Bow", "Bow", 10, 5.6, 13, 7);
    private final static Weapon crossBow = new Weapon("Cross Bow", "Bow", 6, 2.6, 8, 2.5);

    private final static ArrayList<Weapon> swords = new ArrayList<Weapon>();
    private final static ArrayList<Weapon> axes = new ArrayList<Weapon>();
    private final static ArrayList<Weapon> bows = new ArrayList<Weapon>();

    private final static ArrayList<Weapon> list = new ArrayList<Weapon>();

    public static void createItems() {
        swords.add(shortSword);
        swords.add(longSword);
        swords.add(dagger);

        axes.add(oneHandedAxe);
        axes.add(doubleHandedAxe);
        axes.add(twoHandedAxe);

        bows.add(shortBow);
        bows.add(longBow);
        bows.add(crossBow);

        list.addAll(swords);
        list.addAll(axes);
        list.addAll(bows);
    }

    public static Weapon getRandomWeapon() {

        SecureRandom random = new SecureRandom();

        int listLength = list.size();

        int index = random.nextInt(listLength);

        return list.get(index);
    }

    public static Weapon getRandomWeapon(String type) {

        SecureRandom random = new SecureRandom();

        ArrayList<Weapon> list;

        switch (type) {

            case "Sword":
                list = swords;
                break;

            case "Axe":
                list = axes;
                break;

            case "Bow":
                list = bows;
                break;
            default:
                System.out.println("Invalid Type");
                return null;
        }

        int listLength = list.size();

        int index = random.nextInt(listLength);

        return list.get(index);
    }

    public static ArrayList<Weapon> getList() {
        return list;
    }

    public static ArrayList<Weapon> getSwords() {
        return swords;
    }

    public static ArrayList<Weapon> getAxes() {
        return axes;
    }

    public static ArrayList<Weapon> getBows() {
        return bows;
    }

    public static Weapon getShortSword() {
        return shortSword;
    }

    public static Weapon getLongSword() {
        return longSword;
    }

    public static Weapon getDagger() {
        return dagger;
    }

    public static Weapon getOneHandedAxe() {
        return oneHandedAxe;
    }

    public static Weapon getDoubleHeadedAxe() {
        return doubleHandedAxe;
    }

    public static Weapon getTwoHandedAxe() {
        return twoHandedAxe;
    }

    public static Weapon getShortBow() {
        return shortBow;
    }

    public static Weapon getLongBow() {
        return longBow;
    }

    public static Weapon getCrossBow() {
        return crossBow;
    }
}
