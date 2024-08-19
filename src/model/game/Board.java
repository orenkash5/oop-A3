package model.game;

import model.tiles.Tile;
import model.tiles.units.enemies.Enemy;
import model.tiles.units.players.*;
import utils.Position;

import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.TreeMap;

public class Board {
    private Map<Position, Tile> board;
    private Player player;
    private List<Enemy> enemies;
    private final int width;

    public Board(List<Tile> tiles, Player p, List<Enemy> enemies, int width){
        this.player = p;
        this.enemies = enemies;
        this.width = width;
        this.board = new TreeMap<>();
        for(Tile t : tiles){
            board.put(t.getPosition(), t);
        }
    }
    public void updateTilePosition(Tile tile) {
        board.remove(tile.getPosition());
        board.put(tile.getPosition(), tile);
    }
    public Player getPlayer() {
        return player;
    }

    @Override
    public String toString() {

        StringBuilder sb = new StringBuilder();
        int counter = 0;
        for(Map.Entry<Position, Tile> entry : board.entrySet()){
            if(entry.getKey().getY() > counter){
                sb.append("\n");
                counter++;
            }
            sb.append(entry.getValue().toString());
        }
        return sb.toString();
    }
    public Map<Position, Tile> getBoard(){
        return board;
    }




}
