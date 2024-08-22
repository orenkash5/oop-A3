package model.tiles.units.players;

import model.tiles.units.enemies.Enemy;
import view.CLI;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Mage extends Player{
    protected int manaPool;
    protected int currentMana;
    protected int manaCost;
    protected int spellPower;
    protected int hitCount;
    protected int abilityRange;

    public Mage(String name, int hitPoints, int attack, int defense, int manaPool, int manaCost, int spellPower, int hitCount, int abilityRange){
        super(name, hitPoints, attack, defense);
        this.manaPool = manaPool;
        this.currentMana = manaPool/4;
        this.manaCost = manaCost;
        this.spellPower = spellPower;
        this.hitCount = hitCount;
        this.abilityRange = abilityRange;
    }

    @Override
    public void levelUp() {
        this.experience -= levelRequirement();
        this.level++;
        this.manaPool = manaPool + 25*this.level;
        this.currentMana = Math.min(this.currentMana + this.manaPool/4, this.manaPool);
        this.spellPower = this.spellPower + 10*this.level;
        this.health.heal();
        CLI cli = new CLI();
        cli.displayLevelUp(this);
    }

    public void onGameTick() {
        this.currentMana=Math.min(this.manaPool, this.currentMana + this.level);
    }
    public void specialAbility(List<Enemy> enemies) {
        CLI cli = new CLI();
        if (this.currentMana - this.manaCost >= 0) {
            this.currentMana = this.currentMana - this.manaCost;
            int hits = 0;
            List<Enemy> enemiesInRange = new ArrayList<>();
            for (Enemy enemy : enemies) {
                if (enemy.getPosition().range(this.getPosition()) < 3) {
                    enemiesInRange.add(enemy);
                }
            }
            if (!enemiesInRange.isEmpty()){
                while (hits < hitCount) {
                    Random random = new Random();
                    int randomIndex = random.nextInt(enemiesInRange.size());
                    Enemy chosenEnemy = enemiesInRange.get(randomIndex);
                    chosenEnemy.getHealth().takeDamage(this.spellPower);
                    hits++;
                }
            }
            cli.display("used special ability");
        } else
            cli.display("not enough mana");
    }
    public String description(){
        return name + " health " + health.toString() + " attack " + attack + " defence " + defense + " mana pool " + manaPool
                + " current mana " + currentMana + " mana cost " + manaCost + " spell power " + spellPower + " hit count "
                + hitCount + " ability range " + abilityRange  + " level " + this.level;
    }
}
