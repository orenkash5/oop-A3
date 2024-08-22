package Tests;


import model.tiles.Empty;
import model.tiles.units.players.JonSnow;
import org.junit.Test;
import utils.Position;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class PlayerTest {
    @Test
    public void PlayerTest1(){
        JonSnow jonSnow = new JonSnow();
        jonSnow.initialize(new Position(0,0));
        Empty empty = new Empty();
        Position position = new Position(0,1);
        empty.initialize(position);
        jonSnow.interact(empty);
        assertEquals(jonSnow.getPosition(), position);
    }
    public void PlayerTest2(){
        JonSnow jonSnow = new JonSnow();
        jonSnow.initialize(new Position(0,0));
        jonSnow.addExperience(50);
    }

}
