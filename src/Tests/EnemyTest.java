package Tests;

import model.tiles.Empty;
import model.tiles.units.enemies.Enemy;
import model.tiles.units.enemies.LannisterSolider;
import model.tiles.units.players.JonSnow;
import org.junit.Test;
import utils.Position;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class EnemyTest {
    @Test
    public void EnemyTest1(){
        LannisterSolider enemy = new LannisterSolider();
        enemy.initialize(new Position(0,0));
        Empty empty = new Empty();
        Position position = new Position(0,1);
        empty.initialize(position);
        enemy.interact(empty);
        assertEquals(enemy.getPosition(), position);
    }
}
