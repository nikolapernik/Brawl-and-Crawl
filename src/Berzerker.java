public class Berzerker extends Character{

        private String name;
        private int health = 50;
        private int damage = 17;
        private int armor = 3;
        private double lifesteal = 3.5;
        private int potCount = 3;

        // Constructor
        public Berzerker(String name) {
            this.name = name;
        }

        // Getters
        public String getName() { return name; }
        public int getHealth() { return health; }
        public int getDamage() { return damage; }



    // 4. Method for attacking a wizard

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
    public int getArmor()
    {
        return armor;
    }
    @Override
    public int getPotCount()
    {
        return potCount;
    }
    @Override
    public void displayInfo()
    {
        System.out.println(name + " | " + damage + " | " + armor + " | " + potCount);
    }
    public void applyLifesteal()
        {
            health = health + (int)(lifesteal*damage)/10;
        }
        @Override
        public void attack(Character enemy) {
            System.out.println(name + " attacks " + enemy.getName() + "!");
            enemy.takeDamage(damage);
            applyLifesteal();
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

