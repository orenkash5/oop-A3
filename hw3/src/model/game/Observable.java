package model.game;

import model.tiles.Tile;
import model.tiles.units.enemies.Enemy;

public interface Observable {
    public String move(Tile t,  Tile[][]board);
    public String action(Enemy[]enemies, Tile[][]board);
    public void addObservers(Tile t);
    public void notifiyGameTick();
}
