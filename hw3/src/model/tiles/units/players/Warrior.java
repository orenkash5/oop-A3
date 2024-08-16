package model.tiles.units.players;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import model.tiles.units.enemies.Enemy;
import utils.Position;

public class Warrior extends Player{

    protected int abilityCooldown;
    protected int remainingCooldown;

    public Warrior(String name, int hitPoints, int attack, int defense, int abilityCooldown){
        super(name, hitPoints, attack, defense);
        this.abilityCooldown = abilityCooldown;
        this.remainingCooldown = abilityCooldown;
    }
    public void levelUp(){
        this.experience -= levelRequirement();
        this.level++;
        this.remainingCooldown = 0;
        this.health.increaseMax(5*this.level);
        this.attack = this.attack + 2*this.level;
        this.defense = this.defense + this.level;
    }

    public void gameTick() {
        this.remainingCooldown=Math.max(remainingCooldown-1, 0);
    }

    public String onAbilityCastAttempt(List<Enemy> enemies) {
        if (this.remainingCooldown == 0) {
            this.remainingCooldown = this.abilityCooldown;
            this.health.healAmount(this.health.getCurrent() + 10 * this.defense);
            Random random = new Random();
            int randomIndex = random.nextInt(enemies.size());
            Enemy chosenEnemy = enemies.get(randomIndex);
            return "Ability casted";
            // continue function
        }else{
            return "You still cant cast your ability";
        }
    }

}
