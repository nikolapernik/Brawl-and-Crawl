public class Character {
    private String name;
    private int damage;
    private int armor;
    private int health;
    private int potCount;

    public Character() {
    }

    public int getArmor()
    {
        return armor;
    }

    public int getPotCount()
    {
        return potCount;
    }
    public void attack(Character enemy) {
        System.out.println(getName() + " attacks " + enemy.getName());
        enemy.takeDamage(damage);
    }
    public void displayInfo()
    {
        System.out.println(name + " | " + damage + " | " + armor + " | " + potCount);
    }
    public void consumePot() {
        if (potCount <= 0) {
            System.out.println(getName() + " tried to heal, but... oh dear... They don't have a pot! Too bad!");
        } else {
            health = health + 10;
            potCount = potCount - 1;
            System.out.println(getName() + " healed for 15HP and consumed one of their health potions. HP: " + health + " Pots: " + potCount);
        }
    }
    public void takeDamage(int enemyDamage)
    {
        int damage = enemyDamage - armor;

        if (damage<0)
        {
            damage = 0;

        }
        health = health - damage;

        System.out.println(getName() + " takes " + damage + " damage!");
        System.out.println(getName() + " health after attack: " + health);
    }

    public String getName() { return name; }
    public int getDamage() { return damage; }
    public int getHealth() { return health; }
}
