public class Joker extends Character {
    private String name;
    private int damage = (int)(Math.random()*10+10);;
    private int health = 50;
    private int armor = (int)(Math.random()*10+6);
    private int potCount = (int)(Math.random()*5 + 1);


    public Joker(String name)
    {
        this.name = name;
    }
    @Override
    public void attack(Character enemy) {
        System.out.println(name + " attacks " + enemy.getName() + "!");
        enemy.takeDamage(damage);
    }
    public String getName()
    {
        return name;
    }
    public int getHealth() { return health; }
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
@Override
public void displayInfo()
{
    System.out.println(name + " | " + damage + " | " + armor + " | " + potCount);
}
@Override
public int getArmor()
{
    return armor;
}
    public int getDamage() { return damage; }

    @Override
    public int getPotCount()
    {
        return potCount;
    }
}
