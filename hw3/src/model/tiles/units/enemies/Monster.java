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
    public void onEnemyTurn(Position playerPosition) {
        double distance = this.position.range(playerPosition);
        if (distance <= visionRange) {
            int dx = this.position.getX() - playerPosition.getX();
            int dy = this.position.getY() - playerPosition.getY();
            if (Math.abs(dx) > Math.abs(dy)) {
                if (dx > 0) {
                    moveLeft();
                } else {
                    moveRight();
                }
            } else {
                if (dy > 0) {
                    moveUp();
                } else {
                    moveDown();
                }
            }
        } else {
            performRandomMovement();
        }
    }

    private void moveLeft() {
        this.position = new Position(this.position.getX() - 1, this.position.getY());
    }
    private void moveRight() {
        this.position = new Position(this.position.getX() + 1, this.position.getY());
    }
    private void moveUp() {
        this.position = new Position(this.position.getX(), this.position.getY() - 1);
    }
    private void moveDown() {
        this.position = new Position(this.position.getX(), this.position.getY() + 1);
    }
    private void performRandomMovement() {
        Random random = new Random();
        int move = random.nextInt(5);
        switch (move) {
            case 0:
                this.moveLeft();
            case 1:
                moveRight();
                break;
            case 2:
                moveUp();
                break;
            case 3:
                moveDown();
                break;
            default:
                break;
        }
    }


}
