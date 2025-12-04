public class Warrior extends Character{
    private String name;
    private int health = 50;
    private int damage = 12;
    private int armor = 12;
    private int potCount = 2;

    // Constructor
    public Warrior(String name)
    {
        this.name = name;
    }



    // Getters
    public String getName() { return name; }
    public int getHealth() { return health; }
    public int getDamage() { return damage; }
    public int getArmor() {return armor; }
    public int getPotCount(){return potCount; }

    @Override
    public void attack(Character enemy) {
        System.out.println(name + " attacks " + enemy.getName() + "!");
        enemy.takeDamage(damage);
    }
    @Override
    public void consumePot() {
        if (potCount <= 0) {
            System.out.println(getName() + " tried to heal, but... oh dear... They don't have a pot! Too bad!");
        } else {
            health = health + 10;
            potCount = potCount - 1;
            System.out.println(getName() + " healed for 15HP and consumed one of their health potions. HP: " + health + " Pots: " + potCount);
        }
    }

    @Override
    public void displayInfo()
    {
        System.out.println(name + " | " + damage + " | " + armor + " | " + potCount);
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

}

