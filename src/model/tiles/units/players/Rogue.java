package model.tiles.units.players;

import model.tiles.units.enemies.Enemy;
import view.CLI;

import java.util.List;

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
        CLI cli = new CLI();
        cli.displayLevelUp(this);
    }

    public void gameTick() {
        this.currentEnergy=Math.min(this.currentEnergy + 10, 100);
    }

    public void specialAbility(List<Enemy> enemies){
        if (this.currentEnergy - this.cost >= 0) {
            this.currentEnergy = this.currentEnergy - this.cost;
            for (Enemy enemy : enemies) {
                enemy.getHealth().takeDamage(this.attack);
            }
        }
    }
    public String description(){
        return name + " health " + health.toString() + " attack " + attack + " defence " + defense +
                " cost " + cost + " current energy " + currentEnergy  + " level " + this.level;
    }
}
