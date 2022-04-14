package level_design;

import character.Monster;
import character.Townspeople;
import item.Item;

import java.util.ArrayList;

public class Room {

    private final int roomNumber;
    private final int corridorNumber;

    private Dungeon stair;
    private final ArrayList<Room> doors = new ArrayList<Room>();

    private final ArrayList<Townspeople> townspeopleList = new ArrayList<Townspeople>();
    private final ArrayList<Monster> monsterList = new ArrayList<Monster>();

    private final ArrayList<Item> collectableItems = new ArrayList<Item>();

    public Room(int roomNumber, int corridorNumber) {
        this.roomNumber = roomNumber;
        this.corridorNumber = corridorNumber;
        stair = null;
    }

    public boolean hasStair() {
        if(this.stair != null) {
            return true;
        } else {
            return false;
        }
    }

    public ArrayList<Townspeople> getTownspeopleList() {
        return townspeopleList;
    }

    public ArrayList<Monster> getMonsterList() {
        return monsterList;
    }

    public ArrayList<Item> getCollectableItems() {
        return collectableItems;
    }

    public Dungeon useStair() {
        return stair;
    }

    public void setStair(Dungeon stair) {
        this.stair = stair;
    }

    public int getRoomNumber() {
        return roomNumber;
    }

    public int getCorridorNumber() {
        return corridorNumber;
    }

    public Room openDoor(int doorNumber) {

        int index = doorNumber-1;

        return doors.get(index);
    }

    public ArrayList<Room> getDoors() {
        return doors;
    }

    public void addDoor(Room room1) {
        this.doors.add(room1);
    }

    public void addDoor(Room room1, Room room2) {
        this.doors.add(room1);
        this.doors.add(room2);
    }

    public boolean hasMonsters() {
        if(monsterList.size() > 0) {
            return true;
        } else {
            return false;
        }
    }
}
