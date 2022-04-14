package item;

public class Weapon extends Item {

    private String weaponType;
    private double weaponDamage;
    private double range;

    public Weapon(String name, String weaponType, double damage, double range, int value, double weight) {
        super(name, "Weapon", value, weight);
        this.weaponType = weaponType;
        this.weaponDamage = damage;
        this.range = range;
    }

    public String getWeaponType() {
        return weaponType;
    }

    public void setWeaponType(String weaponType) {
        this.weaponType = weaponType;
    }

    public double getWeaponDamage() {
        return weaponDamage;
    }

    public void setWeaponDamage(double weaponDamage) {
        this.weaponDamage = weaponDamage;
    }

    public double getRange() {
        return range;
    }

    public void setRange(double range) {
        this.range = range;
    }
}
