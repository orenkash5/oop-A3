package control.initializers;

import model.tiles.Empty;
import model.tiles.Tile;
import model.tiles.Wall;
import model.tiles.units.enemies.*;
import model.tiles.units.players.Player;
import utils.Position;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;

public class LevelInitializer {
    private Player player;

    public LevelInitializer(Player player){
        this.player = player;
    }
    public void initLevel(String levelPath, File file, List<Tile> tiles, List<Enemy> enemies, Player player) throws FileNotFoundException {
 /*       List<String> lines;
        try {
            lines = Files.readAllLines(Paths.get(levelPath));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }*/
        int counter = 0;
        for (Scanner scanner = new Scanner(file); scanner.hasNext(); ++counter){
            String line = scanner.nextLine();
            readFile(line, counter, tiles, enemies, player);
        }

   /*     for(String line : lines){
            for(char c : line.toCharArray()){
                switch(c) {
                    case '.':
                        // create empty tile
                        break;
                    case '#':
                        // create wall tile
                        break;
                    case '@':
                        // create player tile
                        break;
                    default:
                        // create enemy tile
                        break;
                }
            }
        }*/
    }
    public void readFile(String line, int counter, List<Tile> tiles , List<Enemy> enemies, Player player){
        for (int c = 0; c < line.length(); ++c){
            if (line.charAt(c) == '.'){
                Empty empty = new Empty();
                empty.initialize(new Position(c, counter));
                tiles.add(empty);
            }
            if (line.charAt(c) == '#'){
                Wall wall = new Wall();
                wall.initialize(new Position(c, counter));
                tiles.add(wall);
            }
            if (line.charAt(c) == '@'){
                player.initialize(new Position(c, counter));
                tiles.add(player);
            }
            if (line.charAt(c) == 's'){
                Enemy e = new LannisterSolider();
                e.initialize(new Position(c, counter));
                enemies.add(e);
                tiles.add(e);
            }
            if (line.charAt(c) == 'k'){
                Enemy e = new LannisterKnight();
                e.initialize(new Position(c, counter));
                enemies.add(e);
                tiles.add(e);
            }
            if (line.charAt(c) == 'q'){
                Enemy e = new QueensGuard();
                e.initialize(new Position(c, counter));
                enemies.add(e);
                tiles.add(e);
            }
            if (line.charAt(c) == 'z'){
                Enemy e = new Wright();
                e.initialize(new Position(c, counter));
                enemies.add(e);
                tiles.add(e);
            }
            if (line.charAt(c) == 'b'){
                Enemy e = new BearWright();
                e.initialize(new Position(c, counter));
                enemies.add(e);
                tiles.add(e);
            }
            if (line.charAt(c) == 'g'){
                Enemy e = new GiantWright();
                e.initialize(new Position(c, counter));
                enemies.add(e);
                tiles.add(e);
            }
            if (line.charAt(c) == 'w'){
                Enemy e = new WhiteWalker();
                e.initialize(new Position(c, counter));
                enemies.add(e);
                tiles.add(e);
            }
            if (line.charAt(c) == 'M'){
                Enemy e = new TheMountain();
                e.initialize(new Position(c, counter));
                enemies.add(e);
                tiles.add(e);
            }
            if (line.charAt(c) == 'C'){
                Enemy e = new QueenCersei();
                e.initialize(new Position(c, counter));
                enemies.add(e);
                tiles.add(e);
            }
            if (line.charAt(c) == 'K'){
                Enemy e = new NightKing();
                e.initialize(new Position(c, counter));
                enemies.add(e);
                tiles.add(e);
            }
            if (line.charAt(c) == 'B'){
                Enemy e = new BonusTrap();
                e.initialize(new Position(c, counter));
                enemies.add(e);
                tiles.add(e);
            }
            if (line.charAt(c) == 'Q'){
                Enemy e = new QueensTrap();
                e.initialize(new Position(c, counter));
                enemies.add(e);
                tiles.add(e);
            }
            if (line.charAt(c) == 'D'){
                Enemy e = new DeathTrap();
                e.initialize(new Position(c, counter));
                enemies.add(e);
                tiles.add(e);
            }



        }
    }


    private boolean isEnemyCharacter(char character) {
        return character == 's' || character == 'k' || character == 'M' || character == 'q' || character == 'z' || character == 'b'
                || character == 'g' || character == 'w' || character == 'C' || character == 'K' || character == 'B'
                || character == 'Q' || character == 'D';
    }
    private boolean isTile(char character){
        return character == 's' || character == 'k' || character == 'M' || character == 'q' || character == 'z' || character == 'b'
                || character == 'g' || character == 'w' || character == 'C' || character == 'K' || character == 'B'
                || character == 'Q' || character == 'D' || character=='.' || character == '#' || character== '@';
    }
}
