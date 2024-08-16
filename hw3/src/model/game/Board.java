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

    public Player getPlayer() {
        return player;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        for(Map.Entry<Position, Tile> entry : board.entrySet()){
            sb.append(entry.getValue().toString());
            if(entry.getKey().getX() == width-1){
                sb.append("\n");
            }
        }
        return sb.toString();
    }

    public Player chooseCharacter() {
        Player[] playerTypes= {new JonSnow(), new TheHound(), new Melisandre(), new ThorosOfMyr(),
                new AryaStark(), new Bronn()};
        Scanner in = new Scanner(System.in);
        int playerPick = in.nextInt();
        Player characterChosen=playerTypes[playerPick-1];
        return characterChosen;
    }

}
