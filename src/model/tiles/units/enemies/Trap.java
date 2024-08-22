package model.tiles.units.enemies;

import model.tiles.units.players.Player;
public class Trap extends Enemy{
    protected int visibilityTime;
    protected int invisibilityTime;
    protected int ticksCount;
    protected boolean isVisible;
    protected char visibleTile;

    public Trap(char tile, String name, int hitPoints, int attack, int defense, int experienceValue, int visibilityTime, int invisibilityTime){
        super(tile, name, hitPoints, attack, defense, experienceValue);
        this.visibilityTime = visibilityTime;
        this.invisibilityTime = invisibilityTime;
        this.ticksCount = 0;
        this.isVisible = true;
        this.visibleTile = tile;
    }

    public void onGameTick(Player player){
        this.isVisible = this.ticksCount < this.visibilityTime;
        if(this.ticksCount == this.visibilityTime + this.invisibilityTime){
            this.ticksCount = 0;
        }
        else
            this.ticksCount++;
        if (isVisible && this.position.range(player.getPosition())<2 ){
            this.visit(player);
        }
        if (isVisible)
            this.tile = visibleTile;
        else
            this.tile = '.';
    }

}
