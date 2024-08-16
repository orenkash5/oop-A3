package model.tiles.units.players;

public class Rogue extends Player {

    protected int cost;
    protected int currentEnergy;

    public Rogue(String name, int hitPoints, int attack, int defense, int cost){
        super(name, hitPoints, attack, defense);
        this.cost = cost;
        this.currentEnergy = 100;
    }

    public void levelUp() {
        this.experience -= levelRequirement();
        this.level++;
        this.currentEnergy = 100;
        this.attack = this.attack + 3*this.level;
    }

    public void gameTick() {
        this.currentEnergy=Math.min(this.currentEnergy + 10, 100);
    }

    public void onAbilityCastAttempt(){
        if (this.currentEnergy - this.cost >= 0){
            this.currentEnergy = this.currentEnergy - this.cost;
            // continue function
        }

    }
}
