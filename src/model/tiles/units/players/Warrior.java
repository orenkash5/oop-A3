package model.tiles.units.players;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import model.tiles.Tile;
import model.tiles.units.enemies.Enemy;
import utils.Position;
import view.CLI;

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
        CLI cli = new CLI();
        cli.displayLevelUp(this);
    }

    public void gameTick() {
        this.remainingCooldown=Math.max(remainingCooldown-1, 0);
    }

    public void specialAbility(List<Enemy> enemies) {
        if (this.remainingCooldown == 0) {
            this.remainingCooldown = this.abilityCooldown;
            List<Enemy> enemiesInRange = new ArrayList<>();
            for (Enemy enemy : enemies) {
                if (enemy.getPosition().range(this.getPosition()) < 3) {
                    enemiesInRange.add(enemy);
                }
            }
            Random random = new Random();
            int randomIndex = random.nextInt(enemiesInRange.size());
            Enemy chosenEnemy = enemiesInRange.get(randomIndex);
            chosenEnemy.getHealth().takeDamage(this.health.getCurrent() / 10);
            this.health.healAmount(this.health.getCurrent() + 10 * this.defense);
        }
    }
    public String description(){
        return name + " health " + health.toString() + " attack " + attack + " defence " + defense + " ability cooldown " + abilityCooldown
                + " remaining cooldown " + remainingCooldown + " level " + this.level;
    }

}
