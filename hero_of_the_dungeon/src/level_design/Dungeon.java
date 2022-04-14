package level_design;

import character.*;
import item.*;

import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.Scanner;

public class Dungeon {

    public static final int dungeonsCount = 16;
    private int dungeonNumber;

    private boolean levelFinished;

    private final ArrayList<Monster> allMonsters = new ArrayList<>();
    private final ArrayList<Townspeople> allTownspeople = new ArrayList<>();

    private final ArrayList<Room> c1 = new ArrayList<Room>(); // Corridor 1 rooms
    private final ArrayList<Room> c2 = new ArrayList<Room>(); // Corridor 2 rooms
    private final ArrayList<Room> c3 = new ArrayList<Room>(); // Corridor 3 rooms

    public Dungeon() {
        createRooms();
    }

    private void createRooms() {

        SecureRandom random = new SecureRandom();

        ArrayList<Room> allRooms = new ArrayList<Room>(); // Corridor rooms

        int n = random.nextInt(2) + 4; // Number of rooms in each corridor (4 or 5)

        int roomNumber = 1;

        for (int c = 1; c <= 3; c++) {
            for (int r = 1; r <= n; r++) {
                allRooms.add(new Room(roomNumber, c));
                roomNumber++;
            }
        }

        for (Room room : allRooms) {
            switch (room.getCorridorNumber()) {
                case 1:
                    c1.add(room);
                    break;
                case 2:
                    c2.add(room);
                    break;
                case 3:
                    c3.add(room);
                    break;
            }
        }

        addDoorsToRooms(c1, c2, c3);

        int connectionIndex1 = random.nextInt(n); //Connection room's index in Corridor 1
        int connectionIndex2_1 = random.nextInt(n); //Connection room's index in Corridor 2 to Corridor 1
        int connectionIndex2_3 = random.nextInt(n); //Connection room's index in Corridor 2 to Corridor 3
        int connectionIndex3 = random.nextInt(n); //Connection room's index in Corridor 3

        // Adding extra door to Transition levels.Room in Corridor 1 (pass to Corridor 2)
        Room c1TransitionRoom = c1.get(connectionIndex1);
        c1TransitionRoom.addDoor(c2.get(connectionIndex2_1));

        // Adding extra door to Transition levels.Room in Corridor 2 (pass to Corridor 1)
        Room c2TransitionRoom_1 = c2.get(connectionIndex2_1);
        c2TransitionRoom_1.addDoor(c1.get(connectionIndex1));

        // Adding extra door to Transition levels.Room in Corridor 2 (pass to Corridor 3)
        Room c2TransitionRoom_3 = c2.get(connectionIndex2_3);
        c2TransitionRoom_3.addDoor(c3.get(connectionIndex3));

        // Adding extra door to Transition levels.Room in Corridor 3 (pass to Corridor 2)
        c3.get(connectionIndex3).addDoor(c2.get(connectionIndex2_3));


        // Creating Monsters and Townspeople

        int monstersNumber = random.nextInt(20) + 9; //9 ... 27

        while (monstersNumber % 3 != 0) {
            monstersNumber = random.nextInt(20) + 9; //9 or 12 or 15 or 18 or 21 or 24 or 27
        }

        int townspeopleNumber = monstersNumber / 3;

        //Monster Names
        Monster.addNamesToList();

        for (int i = 0; i < monstersNumber; i++) {
            allMonsters.add(new Monster(Monster.getRandomName(), AllWeapons.getRandomWeapon(), AllClothing.getRandomClothing(), (new Inventory(72)), 100));

            allMonsters.get(i).getInventory().getItems().add(AllWeapons.getRandomWeapon());
            allMonsters.get(i).getInventory().getItems().add(AllClothing.getRandomClothing());
        }

        for (int i = 0; i < townspeopleNumber; i++) {

            int randomNumber = random.nextInt(3);

            switch (randomNumber) {
                case 0:
                    allTownspeople.add(new Townspeople("Ordinary", AllClothing.getRandomClothing(), (new Inventory(72)), 100));
                    break;

                case 1:
                    allTownspeople.add(new Medic(AllClothing.getRandomClothing(), (new Inventory(72)), 100));
                    break;

                case 2:
                    allTownspeople.add(new Blacksmith(AllClothing.getRandomClothing(), (new Inventory(72)), 100));
                    break;
            }

            allTownspeople.get(i).getInventory().getItems().add(AllWeapons.getRandomWeapon());
            allTownspeople.get(i).getInventory().getItems().add(AllClothing.getRandomClothing());
        }

        // Add Monsters to Rooms Randomly
        int counter1 = 1;
        boolean isAvailable;

        for (Monster monster : allMonsters) {

            int c1Index = random.nextInt(c1.size());
            int c2Index = random.nextInt(c2.size());
            int c3Index = random.nextInt(c3.size());

            Room room;

            switch (counter1 % 3) {

                case 1:
                    while (true) {
                        room = c1.get(c1Index);

                        isAvailable = checkMonsterName(monster, room.getMonsterList());

                        if (isAvailable) {
                            room.getMonsterList().add(monster);
                        } else {
                            monster.setName(Monster.getRandomName());
                            continue;
                        }
                        break;
                    }
                    break;

                case 2:
                    while (true) {
                        room = c2.get(c2Index);

                        isAvailable = checkMonsterName(monster, room.getMonsterList());

                        if (isAvailable) {
                            room.getMonsterList().add(monster);
                        } else {
                            monster.setName(Monster.getRandomName());
                            continue;
                        }
                        break;
                    }
                    break;

                case 0:
                    while (true) {
                        room = c3.get(c3Index);

                        isAvailable = checkMonsterName(monster, room.getMonsterList());

                        if (isAvailable) {
                            room.getMonsterList().add(monster);
                        } else {
                            monster.setName(Monster.getRandomName());
                            continue;
                        }
                        break;
                    }
            }

            counter1++;
        }

        // Add Townspeople to Rooms Randomly
        int counter2 = 1;

        for (Townspeople townspeople : allTownspeople) {

            int c1Index = random.nextInt(c1.size());
            int c2Index = random.nextInt(c2.size());
            int c3Index = random.nextInt(c3.size());

            switch (counter2 % 3) {
                case 1:
                    c1.get(c1Index).getTownspeopleList().add(townspeople);
                    break;
                case 2:
                    c2.get(c2Index).getTownspeopleList().add(townspeople);
                    break;
                case 0:
                    c3.get(c3Index).getTownspeopleList().add(townspeople);
            }

            counter2++;
        }
    }

    public void putStairs(ArrayList<Dungeon> dungeons) {

        int roomsCount = c1.size(); // in each corridor (c1 or c2 or c3)

        SecureRandom random = new SecureRandom();

        int upstairsIndex;
        int downstairsIndex;

        while (true) {

            if (this.dungeonNumber == 1) {
                upstairsIndex = random.nextInt(roomsCount);
                downstairsIndex = -1;
                break;
            } else if (this.dungeonNumber == dungeonsCount) {
                downstairsIndex = random.nextInt(roomsCount);
                upstairsIndex = -1;
                break;
            } else {
                upstairsIndex = random.nextInt(roomsCount);
                downstairsIndex = random.nextInt(roomsCount);
                break;
            }
        } // end loop

        if (upstairsIndex != -1) {
            int nextDungeonIndex = dungeons.indexOf(this) + 1;
            c3.get(upstairsIndex).setStair(dungeons.get(nextDungeonIndex));
        }

        if (downstairsIndex != -1) {
            int previousDungeonIndex = dungeons.indexOf(this) - 1;
            c1.get(downstairsIndex).setStair(dungeons.get(previousDungeonIndex));
        }
    }

    public void enter(Hero hero) {

        Scanner scanner = new Scanner(System.in);

        Room newRoom = findRoom(hero);

        hero.setCurrentRoom(newRoom);

        String command; // User Input

        System.out.println();
        System.out.println("<----------- WELCOME TO DUNGEON " + dungeonNumber + " ----------->");

        while (this == hero.getCurrentDungeon() && !hero.isDead()) {

            if (!levelFinished) {
                if (this.allMonsters.size() == 0) {
                    dungeonReview(hero, scanner);
                    levelFinished = true;

                    hero.getFinishedLevels().add(this);

                    if (hero.getFinishedLevels().size() == dungeonsCount) {

                        // Hero wins
                        hero.setGameWon(true);

                        for (Item item : hero.getInventory().getItems()) {
                            hero.gainPoints(item.getValue());
                        }

                        hero.gainPoints(hero.getWeapon().getValue());
                        hero.gainPoints(hero.getClothing().getValue());

                        return;
                    }
                }
            }

            hero.showGeneralStatus();

            //Commands to attack Monsters
            if (hero.getCurrentRoom().hasMonsters()) {
                for (int i = 0; i < hero.getCurrentRoom().getMonsterList().size(); i++) {

                    Monster monster = hero.getCurrentRoom().getMonsterList().get(i);

                    System.out.println("m" + (i + 1) + ": Attack " + monster.toString());
                }
                System.out.println();
            }

            //Commands to open Doors
            for (int i = 0; i < hero.getCurrentRoom().getDoors().size(); i++) {
                System.out.println("d" + (i + 1) + ": Open Door " + (i + 1));
            }

            //Commands to use Stairs
            if (hero.getCurrentRoom().hasStair()) {

                int stairDungeonNumber = hero.getCurrentRoom().useStair().getDungeonNumber();

                int currentDungeonNumber = hero.getCurrentDungeon().getDungeonNumber();

                if (currentDungeonNumber < stairDungeonNumber) {
                    System.out.println("s: Use stair (Next Dungeon)");
                } else {
                    System.out.println("s: Use stair (Previous Dungeon)");
                }
            }

            System.out.println();

            //Command to see inventory
            System.out.println("i: See your inventory");

            //Command to see collectable items
            System.out.println("c: See the items you can get from the ground");

            System.out.println("------------------------------------------------");
            System.out.print(">: ");
            command = scanner.next();

            try {
                switch (command.charAt(0)) {

                    // Attack to Monsters
                    case 'm':
                        char numberM = command.charAt(command.length() - 1);

                        int monsterNumber = Integer.parseInt(Character.toString(numberM));

                        Monster monster = hero.getCurrentRoom().getMonsterList().get(monsterNumber - 1);

                        // Hero hit to monster
                        hero.attack(monster);

                        //Monster hit to hero
                        monster.attack(hero);

                        if (monster.isDead()) {

                            ArrayList<Item> monsterItems = monster.dropAllItems();
                            hero.getCurrentRoom().getCollectableItems().addAll(monsterItems); //Add dropped items to ground of the Room

                            this.allMonsters.remove(monster); // Remove the monster from the monsters list in Dungeon
                            hero.getCurrentRoom().getMonsterList().remove(monster); // Remove the monster from the monsters list in Room

                            hero.getKilledMonsters().add(monster);
                        }

                        if (hero.isDead()) {

                            ArrayList<Item> heroItems = hero.dropAllItems();
                            hero.getCurrentRoom().getCollectableItems().addAll(heroItems);

                            for (Item item : heroItems) {
                                hero.gainPoints(item.getValue());
                            }
                        }

                        System.out.println();

                        System.out.print("Press ENTER...");
                        scanner.nextLine();
                        scanner.nextLine();

                        if (hero.isDead()) return;

                        break;

                    // Open Doors
                    case 'd':
                        char numberD = command.charAt(command.length() - 1);

                        int doorNumber = Integer.parseInt(Character.toString(numberD));

                        hero.setCurrentRoom(hero.getCurrentRoom().openDoor(doorNumber));

                        break;

                    // Use Stairs
                    case 's':
                        if (command.length() > 1) {
                            throw new Exception("Error");
                        }

                        Room currentRoom = hero.getCurrentRoom();

                        Dungeon currentDungeon = hero.getCurrentDungeon();
                        Dungeon newDungeon = currentRoom.useStair();

                        if (newDungeon.getDungeonNumber() < currentDungeon.getDungeonNumber()) {
                            // Hero will go to previous Dungeon
                            hero.setFromUp(true);
                        } else {
                            // Hero will go to next Dungeon
                            hero.setFromUp(false);
                        }

                        hero.setCurrentDungeon(newDungeon);
                        break;

                    // See the items in hero's inventory
                    case 'i':
                        if (command.length() > 1) {
                            throw new Exception("Error");
                        }

                        String inventoryCommand = "";

                        while (!inventoryCommand.equals("e")) {

                            System.out.println();
                            System.out.println("<-------- INVENTORY --------> ");

                            itemsTable(hero.getInventory().getItems());

                            System.out.println("Current inventory weight: " + hero.getInventory().getCurrentInventoryWeight());
                            System.out.println("You can carry " + hero.getInventory().getEmptyArea() + " more");

                            System.out.println();

                            System.out.println("dr: Drop an item");
                            System.out.println("ew: Equip a weapon");
                            System.out.println("ec: Equip a clothing");
                            System.out.println("e: Go back");
                            System.out.println("(Command example --> dr1 (Drop the item with item index 1))");
                            System.out.println("<---------------------------->");

                            System.out.print(">: ");
                            inventoryCommand = scanner.next();

                            inventoryActions(inventoryCommand, hero, scanner);
                        }

                        break;

                    // See the collectable items on the ground
                    case 'c':
                        if (command.length() > 1) {
                            throw new Exception("Error");
                        }

                        String collectableCommand = "";

                        while (!collectableCommand.equals("e")) {

                            System.out.println();
                            System.out.println("<-------- COLLECTABLE ITEMS --------> ");

                            itemsTable(hero.getCurrentRoom().getCollectableItems());

                            System.out.println();

                            System.out.println("g: Get an item");
                            System.out.println("e: Go back");
                            System.out.println("(Command example --> g1 (Get the item with item index 1))");
                            System.out.println("<---------------------------->");

                            System.out.print(">: ");
                            collectableCommand = scanner.next();

                            collectableActions(collectableCommand, hero, scanner);
                        }

                        break;

                    default:
                        throw new Exception("Error");
                }
            } catch (Exception e) {
                System.out.println();
                System.out.println("--------------------------");
                System.out.println("ERROR: Invalid Command..!");
                System.out.println("--------------------------");
            }
        }
    }

    private void dungeonReview(Hero hero, Scanner scanner) {
        System.out.println("XXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXX");
        System.out.println("---- Dungeon " + hero.getCurrentDungeon().getDungeonNumber() + " Review ----");
        System.out.println();
        System.out.println("- You killed all of the monsters in this dungeon..");
        System.out.println("- You saved the townspeople in here..");

        System.out.println();

        int ordinaryPeopleCount = 0;
        int medicCount = 0;
        int blacksmithCount = 0;

        boolean giftDropped = false;
        Item item = null;

        for (Townspeople townspeople : this.allTownspeople) {

            townspeople.giveAward(hero);

            switch (townspeople.getPeopleType()) {
                case "Ordinary":
                    ordinaryPeopleCount++;
                    break;

                case "Medic":
                    medicCount++;
                    break;

                case "Blacksmith":
                    if (!giftDropped) {
                        Blacksmith blacksmith = (Blacksmith) townspeople;
                        item = blacksmith.dropGift();

                        hero.getCurrentRoom().getCollectableItems().add(item);
                        giftDropped = true;
                    }
                    blacksmithCount++;
                    break;
            }
        }

        hero.getSavedTownspeople().addAll(this.allTownspeople);

        System.out.println("TOWNSPEOPLE COUNT (You gain +5 Points from every each townspeople you saved)");
        System.out.println("Ordinary People: " + ordinaryPeopleCount);
        System.out.println("Medic: " + medicCount + " (Every each medic heals you 200 between 500 HP)");
        System.out.println("Blacksmith: " + blacksmithCount + " (One of the blacksmiths drops an item for you)");
        System.out.println("-------------------------------------------------------------------------------------------");
        System.out.println("TOTAL COUNT: " + this.allTownspeople.size());
        System.out.println("- You gained: +" + this.allTownspeople.size() * 5 + " Points");

        if (medicCount > 0) {
            System.out.printf("- Medics healed you.. Your Hp: %.2f\n", hero.getHp());
        }

        if (giftDropped) {
            System.out.println("- One of the blacksmiths dropped '" + item.getName() + "' to this room for you..");
        }

        System.out.println();
        System.out.println("-------------------------------------------------------------------------------------------");
        System.out.println("You can come anytime to this dungeon to take or look rest of the items in the rooms.");
        System.out.println("Your job is done here..");

        System.out.println();

        System.out.print("Press ENTER...");
        scanner.nextLine();
    }

    private void inventoryActions(String inventoryCommand, Hero hero, Scanner scanner) {

        System.out.println();

        if (!inventoryCommand.equals("e")) {

            try {
                char char1 = inventoryCommand.charAt(0);
                char char2 = inventoryCommand.charAt(1);
                char char3 = inventoryCommand.charAt(2);

                int itemId = Integer.parseInt(Character.toString(char3));

                String firstLetter = Character.toString(char1);
                String secondLetter = Character.toString(char2);

                String command = firstLetter + secondLetter;

                Inventory heroInventory = hero.getInventory();

                Item item = heroInventory.getItems().get(itemId);

                switch (command) {
                    case "dr":
                        heroInventory.dropItem(item);
                        hero.getCurrentRoom().getCollectableItems().add(item);

                        break;

                    case "ew":
                        Weapon newWeapon = (Weapon) item;
                        Weapon oldWeapon = hero.getWeapon();

                        hero.equipWeapon(newWeapon);

                        boolean hasPlaceWeapon = heroInventory.getItem(oldWeapon);

                        if (!hasPlaceWeapon) {
                            System.out.println();
                            System.out.println("You dropped your old weapon..");
                            System.out.println("You can get it or change it if you want..");
                            System.out.println();
                        }

                        System.out.println("You equipped new weapon: " + newWeapon.getName());
                        break;

                    case "ec":
                        Clothing newClothing = (Clothing) item;
                        Clothing oldClothing = hero.getClothing();

                        hero.equipClothing(newClothing);

                        boolean hasPlaceClothing = heroInventory.getItem(oldClothing);

                        if (!hasPlaceClothing) {
                            System.out.println();
                            System.out.println("You dropped your old clothing..");
                            System.out.println("You can get it or change it if you want..");
                            System.out.println();
                        }

                        System.out.println("You equipped new clothing: " + newClothing.getName());
                        break;

                    default:
                        throw new Exception("Error");
                }

                System.out.println();

                System.out.print("Press ENTER...");
                scanner.nextLine();
                scanner.nextLine();

                System.out.println();

            } catch (Exception e) {
                System.out.println();
                System.out.println("--------------------------");
                System.out.println("ERROR: Invalid Command..!");
                System.out.println("--------------------------");
            }

        }
    }

    private void collectableActions(String collectableCommand, Hero hero, Scanner scanner) {
        System.out.println();

        if (!collectableCommand.equals("e")) {

            try {
                char char1 = collectableCommand.charAt(0);
                char char2 = collectableCommand.charAt(1);

                int itemId = Integer.parseInt(Character.toString(char2));

                String firstLetter = Character.toString(char1);

                if (!firstLetter.equals("g")) {
                    throw new Exception("Error");
                }

                Inventory heroInventory = hero.getInventory();

                Item item = hero.getCurrentRoom().getCollectableItems().get(itemId);

                boolean hasPlace = heroInventory.getItem(item);

                if (hasPlace) {
                    hero.getCurrentRoom().getCollectableItems().remove(item);
                }

                System.out.println();

                System.out.print("Press ENTER...");
                scanner.nextLine();
                scanner.nextLine();


            } catch (Exception e) {
                System.out.println();
                System.out.println("--------------------------");
                System.out.println("ERROR: Invalid Command..!");
                System.out.println("--------------------------");
            }

        }
    }

    private Room findRoom(Hero hero) {

        Room newRoom = null;

        if (hero.isFromUp()) {

            if (dungeonNumber != dungeonsCount) {

                for (int i = 0; i < c3.size(); i++) {

                    if (c3.get(i).hasStair()) {
                        newRoom = c3.get(i);
                    }
                }
            } else {
                newRoom = c3.get(0);
            }
        } else {
            if (dungeonNumber != 1) {

                for (int i = 0; i < c1.size(); i++) {

                    if (c1.get(i).hasStair()) {
                        newRoom = c1.get(i);
                    }
                }
            } else {
                newRoom = c1.get(0);
            }
        }
        return newRoom;
    }

    public void addDoorsToRooms(ArrayList<Room> c1, ArrayList<Room> c2, ArrayList<Room> c3) {

        int numberOfRooms = c1.size(); // or c2.size() or c3.size()

        for (int i = 0; i < numberOfRooms; i++) {

            if (i == 0) {
                c1.get(0).addDoor(c1.get(i + 1));
                c2.get(0).addDoor(c2.get(i + 1));
                c3.get(0).addDoor(c3.get(i + 1));
            } else if (i == numberOfRooms - 1) {
                c1.get(i).addDoor(c1.get(i - 1));
                c2.get(i).addDoor(c2.get(i - 1));
                c3.get(i).addDoor(c3.get(i - 1));
            } else {
                c1.get(i).addDoor(c1.get(i + 1), c1.get(i - 1));
                c2.get(i).addDoor(c2.get(i + 1), c2.get(i - 1));
                c3.get(i).addDoor(c3.get(i + 1), c3.get(i - 1));
            }
        }
    }

    public boolean checkMonsterName(Monster monster, ArrayList<Monster> monsterList) {

        boolean isAvailable = true;

        for (Monster m : monsterList) {
            if (monster.getName().equals(m.getName())) {
                isAvailable = false;
                break;
            }
        }

        return isAvailable;
    }

    public void itemsTable(ArrayList<Item> items) {

        ArrayList<Weapon> weaponList = new ArrayList<Weapon>();
        ArrayList<Clothing> clothingList = new ArrayList<Clothing>();

        for (Item item : items) {

            if (item.getType().equals("Weapon")) {

                Weapon weapon = (Weapon) item;

                weaponList.add(weapon);
            }
        }

        for (Item item : items) {

            if (item.getType().equals("Clothing")) {

                Clothing clothing = (Clothing) item;

                clothingList.add(clothing);
            }
        }

        System.out.println();


        // Weapon List
        if (weaponList.size() != 0) {
            System.out.println("Weapon");
            System.out.println("----------");
            System.out.printf("%s%30s%25s%25s%25s%25s%25s\n", "Item Index", "Name", "Weapon Type", "Damage", "Range", "Value", "Weight");

            for (Weapon weapon : weaponList) {

                System.out.printf("%d%39s%25s%25.2f%25.2f%25d%25.2f\n", items.indexOf(weapon), weapon.getName(), weapon.getWeaponType(), weapon.getWeaponDamage(), weapon.getRange(), weapon.getValue(), weapon.getWeight());

            }
        } else {
            System.out.println("- Cannot found any weapon...");
        }

        System.out.println();

        //Clothing List
        if (clothingList.size() != 0) {
            System.out.println("Clothing");
            System.out.println("------------");
            System.out.printf("%s%30s%25s%25s%25s\n", "Item Index", "Name", "Bloc Value", "Value", "Weight");

            for (Clothing clothing : clothingList) {

                System.out.printf("%d%39s%25.2f%25d%25.2f\n", items.indexOf(clothing), clothing.getName(), clothing.getBlocValue(), clothing.getValue(), clothing.getWeight());

            }
        } else {
            System.out.println("- Cannot found any clothing...");
        }

        System.out.println();
    }

    public ArrayList<Monster> getAllMonsters() {
        return allMonsters;
    }

    public int getDungeonNumber() {
        return dungeonNumber;
    }

    public void setDungeonNumber(int dungeonNumber) {
        this.dungeonNumber = dungeonNumber;
    }
}
