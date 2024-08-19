package model.tiles.units.enemies;

import model.tiles.units.players.Player;
import utils.Position;

import java.util.Random;

public class Monster extends Enemy {
    protected int visionRange;

    public Monster(char tile, String name, int hitPoints, int attack, int defense, int experienceValue, int visionRange) {
        super(tile, name, hitPoints, attack, defense, experienceValue);
        this.visionRange = visionRange;
    }

    public int visionRange() {
        return visionRange;
    }


    public boolean isPlayerInRange(Position position) {
        return this.position.range(position)<this.visionRange;
    }


}
