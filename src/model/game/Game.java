package model.game;

import control.initializers.LevelInitializer;
import control.initializers.TileFactory;
import model.tiles.Empty;
import model.tiles.Tile;
import model.tiles.Wall;
import model.tiles.units.enemies.*;
import model.tiles.units.players.*;
import utils.Position;
import utils.generators.Generator;
import utils.generators.RandomGenerator;
import view.CLI;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

public class Game {
    protected final File levelsPath= new File("levels_dir");
    protected File answer;
    protected File[] levels;
    private Board board;
    private Player p;
    private boolean endGame=false;
    private CLI cli;
    private LevelInitializer levelInitializer;
    private TileFactory tileFactory;


    public void runGame() throws IOException {
        tileFactory = new TileFactory();
        cli = new CLI();
        answer=new File(levelsPath.getCanonicalPath());
        levels = answer.listFiles();
        for(int k=0;k<levels.length&!endGame;k++) {
            if(k==0) {
                cli.display("Select Player: ");
                Player[] playerTypes= {new JonSnow(), new TheHound(), new Melisandre(), new ThorosOfMyr(), new AryaStark(), new Bronn()};
                for(int i=0;i<playerTypes.length;i++)
                    cli.display((i+1)+"."+" "+playerTypes[i].description());
                Scanner scanner = new Scanner(System.in);
                int i = -1;
                while(i<0 || i>7){
                    i = scanner.nextInt();
                }
                p = playerTypes[i-1];
                cli.display("you have selected:");
            }
            levelInitializer = new LevelInitializer(p);
            List<Enemy> enemies = new ArrayList<>();
            String tilesStr = readFileToString(levels[k]);
            int width = getLineLength(tilesStr);
            List<Tile> tiles = new ArrayList<>();
            levelInitializer.initLevel("", levels[k], tiles, enemies, p);
            board=new Board(tiles, p, enemies, width);
            while(!enemies.isEmpty()&&!endGame){
                cli.displayStats(board);
                cli.displayBoard(board);
                char c = '_';
                Scanner scanner = new Scanner(System.in);
                while (c != 'w' && c != 'e' && c != 'a' && c != 's' && c != 'd' && c != 'q') {
                    c = scanner.next().charAt(0);
                }
                Iterator<Enemy> iterator1 = enemies.iterator();
                while (iterator1.hasNext()){
                    Enemy e = iterator1.next();
                    if (e.getHealth().getCurrent()==0){
                        Position pos = e.getPosition();
                        iterator1.remove();
                        tiles.remove(e);
                        Tile empty = tileFactory.produceEmpty(pos);
                        tiles.add(empty);
                        board.updateTilePosition(empty);
                    }
                }

                playerMove(c, enemies);
                for (Enemy e: enemies){
                    if(isMonster(e)) {
                        RandomGenerator g = new RandomGenerator();
                        int i = g.generate(4);
                        enemyMove(i % 4, e);
                    }else
                        e.onGameTick(p);

                }

                if (p.getHealth().getCurrent() <= 0){
                    p.onDeath();
                    endGame = true;
                    cli.displayBoard(board);
                }
                p.onGameTick();

            }
            if (!endGame)
                cli.display("The Level is Finished!");

        }
        if (!endGame)
            cli.display("YOU WON!!!");
        else
            cli.display("YOU DIED");


    }


    private  boolean isMonster(Enemy e){
        return e.getTile() == 's' || e.getTile() == 'k' || e.getTile() == 'M' || e.getTile() == 'q' || e.getTile() == 'z'
                || e.getTile() == 'b' || e.getTile() == 'g' || e.getTile() == 'w' || e.getTile() == 'C' || e.getTile() == 'K';
    }

    public static int getLineLength(String text) {
        String[] lines = text.split("\n"); // Handles different line separators
        return lines[0].length();
    }
    public static String readFileToString(File file) throws IOException {
        StringBuilder content = new StringBuilder();
        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = reader.readLine()) != null) {
                content.append(line).append(System.lineSeparator());
            }
        }
        return content.toString();

    }

    public void playerMove(char c,List<Enemy> enemies){
        switch (c){
            case('w'):
                Position pos1 = new Position(p.getPosition().getX(), p.getPosition().getY()-1);
                Tile t1 = board.getBoard().get(pos1);
                p.interact(t1);
                board.updateTilePosition(p);
                board.updateTilePosition(t1);
                break;
            case('a'):
                Position pos2 = new Position(p.getPosition().getX()-1, p.getPosition().getY());
                Tile t2 = board.getBoard().get(pos2);
                p.interact(t2);
                board.updateTilePosition(p);
                board.updateTilePosition(t2);
                break;
            case('s'):
                Position pos3 = new Position(p.getPosition().getX(), p.getPosition().getY()+1);
                Tile t3 = board.getBoard().get(pos3);
                p.interact(t3);
                board.updateTilePosition(p);
                board.updateTilePosition(t3);
                break;
            case('d'):
                Position pos4 = new Position(p.getPosition().getX()+1, p.getPosition().getY());
                Tile t4 = board.getBoard().get(pos4);
                p.interact(t4);
                board.updateTilePosition(p);
                board.updateTilePosition(t4);
                break;

            case('e'):
                p.specialAbility(enemies);
        }
    }

    public void enemyMove(int i, Enemy e){
        if (!e.isPlayerInRange(p.getPosition())) {
            switch (i) {
                case (0):
                    Position pos1 = new Position(e.getPosition().getX(), e.getPosition().getY() + 1);
                    Tile t1 = board.getBoard().get(pos1);
                    e.interact(t1);
                    board.updateTilePosition(e);
                    board.updateTilePosition(t1);
                    break;
                case (1):
                    Position pos2 = new Position(e.getPosition().getX() - 1, e.getPosition().getY());
                    Tile t2 = board.getBoard().get(pos2);
                    e.interact(t2);
                    board.updateTilePosition(e);
                    board.updateTilePosition(t2);
                    break;
                case (2):
                    Position pos3 = new Position(e.getPosition().getX(), e.getPosition().getY() - 1);
                    Tile t3 = board.getBoard().get(pos3);
                    e.interact(t3);
                    board.updateTilePosition(e);
                    board.updateTilePosition(t3);
                    break;
                case (3):
                    Position pos4 = new Position(e.getPosition().getX() + 1, e.getPosition().getY());
                    Tile t4 = board.getBoard().get(pos4);
                    e.interact(t4);
                    board.updateTilePosition(e);
                    board.updateTilePosition(t4);
                    break;
            }
        }else {
            int dx = e.getPosition().getX() - p.getPosition().getX();
            int dy = e.getPosition().getY() - p.getPosition().getY();
            if (dx > dy) {
                if (dx > 0) {
                    Position pos2 = new Position(e.getPosition().getX() - 1, e.getPosition().getY());
                    Tile t2 = board.getBoard().get(pos2);
                    e.interact(t2);
                    board.updateTilePosition(e);
                    board.updateTilePosition(t2);
                }else {
                    Position pos4 = new Position(e.getPosition().getX() + 1, e.getPosition().getY());
                    Tile t4 = board.getBoard().get(pos4);
                    e.interact(t4);
                    board.updateTilePosition(e);
                    board.updateTilePosition(t4);
                }
            }else {
                if (dy>0){
                    Position pos3 = new Position(e.getPosition().getX(), e.getPosition().getY() - 1);
                    Tile t3 = board.getBoard().get(pos3);
                    e.interact(t3);
                    board.updateTilePosition(e);
                    board.updateTilePosition(t3);
                }else {
                    Position pos1 = new Position(e.getPosition().getX(), e.getPosition().getY() + 1);
                    Tile t1 = board.getBoard().get(pos1);
                    e.interact(t1);
                    board.updateTilePosition(e);
                    board.updateTilePosition(t1);
                }
            }
        }
    }


}
