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
        this.health.heal();
        CLI cli = new CLI();
        cli.displayLevelUp(this);
    }

    public void onGameTick() {
        this.remainingCooldown=Math.max(remainingCooldown-1, 0);
    }

    public void specialAbility(List<Enemy> enemies) {
        CLI cli = new CLI();
        if (this.remainingCooldown == 0) {
            this.remainingCooldown = this.abilityCooldown;
            List<Enemy> enemiesInRange = new ArrayList<>();
            for (Enemy enemy : enemies) {
                if (enemy.getPosition().range(this.getPosition()) < 3) {
                    enemiesInRange.add(enemy);
                }
            }
            Random random = new Random();
            if (enemies.isEmpty()){
                int randomIndex = random.nextInt(enemiesInRange.size());
                Enemy chosenEnemy = enemiesInRange.get(randomIndex);
                chosenEnemy.getHealth().takeDamage(this.health.getCurrent() / 10);
            }

            this.health.healAmount(this.health.getCurrent() + 10 * this.defense);

            cli.display("used special ability");
        }else
            cli.display("cooldown did not reach 0");

    }
    public String description(){
        return name + " health " + health.toString() + " attack " + attack + " defence " + defense + " ability cooldown " + abilityCooldown
                + " remaining cooldown " + remainingCooldown + " level " + this.level;
    }

}
