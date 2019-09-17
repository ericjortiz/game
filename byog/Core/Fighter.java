package byog.Core;
import java.util.Random;

public class Fighter {
    private String name;
    private double health;
    private double maxHealth;
    private double mana;
    private double maxMana;
    private int manaRegen;
    private int averageAttack;
    private int fireBallDamage;
    private int healthDrainDamage;
    private int healAmount;
    private Random random = new Random();
    public static final int FIREBALLCOST = 500;
    public static final int HEALTHDRAINCOST = 425;
    public static final int HEALCOST = 2000;

    public Fighter(char archetype) {
        if (archetype == 'a') {
            instantiateWizard();
        } else if (archetype == 'd') {
            instantiateHercules();
        } else {
            instantiateBowser();
        }
    }

    /**
     * Creates the player to be focused on magic
     */
    private void instantiateWizard() {
        this.health = 1500;
        this.maxHealth = this.health;
        this.mana = 1250;
        this.maxMana = 3000;
        this.manaRegen = 250;
        this.averageAttack = 300;
        this.fireBallDamage = 1000;
        this.healthDrainDamage = 500;
        this.healAmount = 1000;
    }

    /**
     * Creates the player to be focused on attacking
     */
    private void instantiateHercules() {
        this.health = 2500;
        this.maxHealth = this.health;
        this.mana = 625;
        this.maxMana = 2000;
        this.manaRegen = 125;
        this.averageAttack = 450;
        this.fireBallDamage = 750;
        this.healthDrainDamage = 350;
    }

    /**
     * Made by default.
     */
    private void instantiateBowser() {
        this.health = 6000;
        this.maxHealth = this.health;
        this.mana = Double.MAX_VALUE;
        this.maxMana = this.mana;
        this.manaRegen = 0;
        this.averageAttack = 200;
        this.fireBallDamage = 250;
    }

    /**
     * Causes the fighter to take a specific amount of damage
     * @param amount the amount of damage that the player will take
     */
    private void decreaseHealth(double amount) {
        this.health -= amount;
        if (this.health < 0) {
            this.health = 0;
        }
    }

    /**
     * Decreases the mana by spellCost if possible
     * @param spellCost the cost of the given spell
     * @return whether or not the spell was able to be casted
     */
    public boolean decreaseMana(int spellCost) {
        if (this.mana >= spellCost) {
            this.mana -= spellCost;
            return true;
        }
        return false;
    }

    /**
     * Increases the health of the fighter by amount. Will increase up to max health
     * @param amount the maximum amount of health to be increased by
     */
    public void increaseHealth(double amount) {
        this.health += amount;
        if (health > maxHealth) {
            this.health = maxHealth;
        }
    }

    /**
     * Increases the mana of the fighter after the end of their turn
     */
    public void increaseMana() {
        this.mana += this.manaRegen;
        if (this.mana > this.maxMana) {
            this.mana = this.maxMana;
        }
    }

    /**
     * Casts the fireball to another fighter
     * @param other the fighter who will be receiving the damage
     */
    public double castFireball(Fighter other) {
        if (this.decreaseMana(FIREBALLCOST)) {
            other.decreaseHealth(this.fireBallDamage);
        }
        return fireBallDamage;
    }

    /**
     * Casts the health drain spell on another fighter.
     * @param other the fighter who will take the damage
     */
    public double castHealthDrain(Fighter other) {
        if (decreaseMana(HEALTHDRAINCOST)) {
            this.increaseHealth(HEALTHDRAINCOST / 2);
            other.decreaseHealth(this.healthDrainDamage);
        }
        return healthDrainDamage;
    }

    /**
     * Casts the heal spell (if possible)
     */
    public double castHeal() {
        if (decreaseMana(HEALCOST)) {
            this.increaseHealth(this.healAmount);
        }
        return healAmount;
    }

    /**
     * Attacks the other fighter by a random amount based on the average attack damage
     * @param other the fighter who will take the damage
     */
    public double attack(Fighter other) {
        double damage = this.averageAttack + 50*random.nextDouble() - 25;
        other.decreaseHealth(damage);
        return damage;
    }

    /**
     * Describes whether the fighter is alive or not
     * @return true if the fighter is alive and false otherwise
     */
    public boolean isAlive() {
        return this.health > 0;
    }

    /**
     * @return the amount of health the fighter has left
     */
    public double getHealth() {
        return this.health;
    }

    /**
     * @return the amount of mana the fighter has left
     */
    public double getMana() {
        return this.mana;
    }

    /**
     * @return the maximum health of the fighter
     */
    public double getMaxHealth() {
        return maxHealth;
    }

    /**
     * @return the maximum mana of the fighter
     */
    public double getMaxMana() {
        return maxMana;
    }
}
