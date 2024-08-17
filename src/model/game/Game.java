package model.game;

import model.tiles.Empty;
import model.tiles.Tile;
import model.tiles.Wall;
import model.tiles.units.enemies.*;
import model.tiles.units.players.*;
import utils.Position;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

public class Game {
    protected final File levelsPath= new File("C:\\Users\\owner\\IdeaProjects\\hw3\\levels_dir");
    protected File answer;
    protected File[] levels;
    private Board board;
    private int numOfEnemies;
    private int numOfTraps;
    private int numOfTotalEnemies;
    private Player p;
    private boolean endGame=false;


    public void runGame() throws IOException {
        answer=new File(levelsPath.getCanonicalPath());
        levels = answer.listFiles();
        for(int k=0;k<levels.length&!endGame;k++) {
            if(k==0) {
                System.out.println("Select Player: ");
                Player[] playerTypes= {new JonSnow(), new TheHound(), new Melisandre(), new ThorosOfMyr(), new AryaStark(), new Bronn()};
                for(int i=0;i<playerTypes.length;i++)
                    System.out.println((i+1)+"."+" "+playerTypes[i].description());
                Scanner scanner = new Scanner(System.in);
                int i = -1;
                while(i<0 || i>7){
                    i = scanner.nextInt();
                }
                p = playerTypes[i-1];
                System.out.println("you have selected:");
                System.out.println(p.description());
                System.out.println("");
            }
            List<Tile> enemies = new ArrayList<>();
            String tilesStr = readFileToString(levels[k]);
            int width = getLineLength(tilesStr);
            List<Character> clst = convertStringToCharacterList(tilesStr);
            List<Tile> tiles = new ArrayList<>();
            List<Character> boardlst = new ArrayList<>();
            for (char c : clst) {
                if (c != '\n') {
                    boardlst.add(c);
                }
            }
            int counter = 0;
            for(int i = 0;i<clst.size();i++) {
                if (isTile(clst.get(i))) {
                    Tile t = makeTile(clst.get(i));
                    if (isEnemyCharacter(clst.get(i))) {
                        enemies.add(t);
                    }
                    t = t.initialize(new Position((i+counter) % width, (i+counter) / width));
                    tiles.add(t);
                }
            }
            board=new Board(tiles, p, enemies, width);
            System.out.println("Level: "+(k+1));
            while(!enemies.isEmpty()&&!endGame){
                System.out.println(board.toString());
                char c = '_';
                Scanner scanner = new Scanner(System.in);
                while (c != 'w' && c != 'e' && c != 'a' && c != 's' && c != 'd') {
                    c = scanner.next().charAt(0);
                }
                playerMove(c, enemies);

                if (p.getHealth().getCurrent() <= 0){
                    p.onDeath();
                    endGame = true;
                }
                
            }
            if (!endGame)
                System.out.println("The Level is Finished!");

        }
        System.out.println("YOU WON!!!");


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

    public static List<Character> convertStringToCharacterList(String str) {
        char[] charArray = str.toCharArray();
        List<Character> charList = new ArrayList<>();
        for (char c : charArray) {
            charList.add(c);
        }
        return charList;
    }
    public Tile makeTile(char c){
        switch (c){
            case 's':
                return new LannisterSolider();
            case 'k':
                return new LannisterKnight();
            case 'q':
                return new QueensGuard();
            case 'z':
                return new Wright();
            case 'b':
                return new BearWright();
            case 'g':
                return new GiantWright();
            case 'w':
                return new WhiteWalker();
            case 'M':
                return new TheMountain();
            case 'C':
                return new QueenCersei();
            case 'K':
                return new NightKing();
            case 'B':
                return new BonusTrap();
            case 'Q':
                return new QueensTrap();
            case 'D':
                return new DeathTrap();
            case '@':
                return this.p;
            case '.':
                return new Empty();
            case '#':
                return new Wall();

            default:
                return null;
        }
    }
    public void playerMove(char c,List<Tile> enemies){
        switch (c){
            case('w'):
                Position pos1 = new Position(p.getPosition().getX(), p.getPosition().getY()+1);
                Tile t1 = board.getBoard().get(pos1);
                p.interact(t1);
            case('a'):
                Position pos2 = new Position(p.getPosition().getX()-1, p.getPosition().getY());
                Tile t2 = board.getBoard().get(pos2);
                p.interact(t2);
            case('s'):
                Position pos3 = new Position(p.getPosition().getX(), p.getPosition().getY()-1);
                Tile t3 = board.getBoard().get(pos3);
                p.interact(t3);
            case('d'):
                Position pos4 = new Position(p.getPosition().getX()+1, p.getPosition().getY());
                Tile t4 = board.getBoard().get(pos4);
                p.interact(t4);
            case('e'):
                //sdf
        }
    }
    public void enemyMove(int i, Enemy e){
        if (e.isPlayerInRange()) {
            switch (i) {
                case ('0'):
                    Position pos1 = new Position(e.getPosition().getX(), e.getPosition().getY() + 1);
                    Tile t1 = board.getBoard().get(pos1);
                    e.interact(t1);
                case ('1'):
                    Position pos2 = new Position(e.getPosition().getX() - 1, e.getPosition().getY());
                    Tile t2 = board.getBoard().get(pos2);
                    e.interact(t2);
                case ('2'):
                    Position pos3 = new Position(e.getPosition().getX(), e.getPosition().getY() - 1);
                    Tile t3 = board.getBoard().get(pos3);
                    e.interact(t3);
                case ('3'):
                    Position pos4 = new Position(e.getPosition().getX() + 1, e.getPosition().getY());
                    Tile t4 = board.getBoard().get(pos4);
                    e.interact(t4);
            }
        }else {
            int dx = e.getPosition().getX() - p.getPosition().getX();
            int dy = e.getPosition().getY() - p.getPosition().getY();
            if (dx > dy) {
                if (dx > 0) {
                    Position pos2 = new Position(e.getPosition().getX() - 1, e.getPosition().getY());
                    Tile t2 = board.getBoard().get(pos2);
                    e.interact(t2);
                }else {
                    Position pos4 = new Position(e.getPosition().getX() + 1, e.getPosition().getY());
                    Tile t4 = board.getBoard().get(pos4);
                    e.interact(t4);
                }
            }else {
                if (dy>0){
                    Position pos3 = new Position(e.getPosition().getX(), e.getPosition().getY() - 1);
                    Tile t3 = board.getBoard().get(pos3);
                    e.interact(t3);
                }else {
                    Position pos1 = new Position(e.getPosition().getX(), e.getPosition().getY() + 1);
                    Tile t1 = board.getBoard().get(pos1);
                    e.interact(t1);
                }
            }
        }
    }


}
