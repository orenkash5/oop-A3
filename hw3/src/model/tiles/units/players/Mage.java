package model.tiles.units.players;

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
    }

    public void gameTick() {
        this.currentMana=Math.min(this.manaPool, this.currentMana + this.level);
    }
    public void onAbilityCastAttempt() {

        if (this.currentMana-this.manaCost >= 0){
            this.currentMana = this.currentMana-this.manaCost;
            int hits = 0;
            // continue function
        }
    }
}
