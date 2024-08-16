package model.game;

import model.tiles.units.enemies.*;
import model.tiles.units.players.*;
import utils.Position;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.Stack;

public class Game {
    //private moveOrder o=new moveOrder();
    protected final File levelsPath= new File("./levels");
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
            List<Enemy> enemies = getEnemiesFromLevel(levels[k]);
            int width = getLineWidth(levels[k]);
            board=new Board(levels[k], p, enemies, );
            if(k==0) {
                System.out.println("Select Player: ");
                Player[] playerTypes= {new JonSnow(0,0), new TheHound(0,0), new Melisandre(0,0), new ThorosOfMyr(0,0), new AryaStark(0,0), new Bronn(0,0)};
                for(int i=0;i<playerTypes.length;i++)
                    System.out.println((i+1)+"."+" "+playerTypes[i].description());
                board.createBoard();
                p=board.getPlayer();
                System.out.println("you have selected:");
                System.out.println(p.description());
                System.out.println("");
            }
            else
                board.createBoard();
            numOfTraps=board.getNumOfTraps();
            numOfEnemies=board.getNumOfEnemies();
            numOfTotalEnemies=numOfEnemies+numOfTraps;
            o.addObservers(p);


            Stack<Monster> stackMonsters=board.getEnemies();
            Monster[]monsters=new Monster[numOfEnemies];
            Stack<Trap>stackTraps=board.getTraps();
            Trap[]traps=new Trap[numOfTraps];
            Stack<Trap>temp=new Stack<>();


            while(!stackTraps.isEmpty()) {
                o.addObservers(stackTraps.peek());
                temp.push(stackTraps.pop());
            }
            while(!temp.isEmpty())
                stackTraps.push(temp.pop());
            Enemy[]enemies=new Enemy[stackTraps.size()+stackMonsters.size()];

            int q=0;
            while(!stackMonsters.isEmpty()) {
                monsters[q]=stackMonsters.pop();
                enemies[q]=monsters[q];
                q++;

            }

            int index=q;
            q=0;
            while(!stackTraps.isEmpty()){
                traps[q]=stackTraps.pop();
                enemies[index+q]=traps[q];
                q++;

            }


            System.out.println("Level: "+(k+1));
            for(int i=0;i<board.getBoard().length;i++)
                System.out.println(board.printLine(i));
            PlayersStep ps=new PlayersStep(p);

            while(numOfTotalEnemies>0&!endGame) {
                boolean checkCorrectInput=true;
                String move="";
                while(checkCorrectInput) {
                    Scanner in2 = new Scanner(System.in);
                    System.out.println("move in a direction");
                    String name = in2.nextLine();
                    if(name.equals("w")){
                        Visited pi=board.getBoard()[p.getYValue()-1][p.getXValue()];
                        if(!board.getBoard()[p.getYValue()-1][p.getXValue()].description().equals("")) {
                            System.out.println(p.getName()+" engaged in combat with "+board.getBoard()[p.getYValue()-1][p.getXValue()].getName()+".");
                            System.out.println(p.description());
                            System.out.println(board.getBoard()[p.getYValue()-1][p.getXValue()].description());
                        }
                        checkCorrectInput=false;
                        move=o.move(ps, pi, board.getBoard());
                    }

                    else if(name.equals("s")){
                        Visited pi=board.getBoard()[p.getYValue()+1][p.getXValue()];
                        if(!board.getBoard()[p.getYValue()+1][p.getXValue()].description().equals("")) {
                            System.out.println(p.getName()+" engaged in combat with "+board.getBoard()[p.getYValue()+1][p.getXValue()].getName()+".");
                            System.out.println(p.description());
                            System.out.println(board.getBoard()[p.getYValue()+1][p.getXValue()].description());
                        }
                        checkCorrectInput=false;
                        move=o.move(ps, pi, board.getBoard());
                    }


                    else if(name.equals("a")){
                        Visited pi=board.getBoard()[p.getYValue()][p.getXValue()-1];
                        if(!board.getBoard()[p.getYValue()][p.getXValue()-1].description().equals("")) {
                            System.out.println(p.getName()+" engaged in combat with "+board.getBoard()[p.getYValue()][p.getXValue()-1].getName()+".");
                            System.out.println(p.description());
                            System.out.println(board.getBoard()[p.getYValue()][p.getXValue()-1].description());
                        }
                        checkCorrectInput=false;
                        move=o.move(ps, pi, board.getBoard());
                    }

                    else if(name.equals("d")){
                        Visited pi=board.getBoard()[p.getYValue()][p.getXValue()+1];
                        if(!board.getBoard()[p.getYValue()][p.getXValue()+1].description().equals("")) {
                            System.out.println(p.getName()+" engaged in combat with "+board.getBoard()[p.getYValue()][p.getXValue()+1].getName()+".");
                            System.out.println(p.description());
                            System.out.println(board.getBoard()[p.getYValue()][p.getXValue()+1].description());
                        }
                        checkCorrectInput=false;
                        move=o.move(ps, pi, board.getBoard());
                    }

                    else if(name.equals("e")){
                        move=o.cast(ps, enemies, board.getBoard());
                        checkCorrectInput=false;
                    }
                    else if(name.equals("q")){
                        checkCorrectInput=false;
                    }
                    else
                        checkCorrectInput=true;
                }

                if(!move.equals("")) {
                    int count=0;
                    for(int i=0;i<move.length();i++)
                        if(move.charAt(i)==',') {
                            if(!move.substring(count, i).equals(""))
                                System.out.println(move.substring(count, i));
                            count=i+1;
                        }

                }

                for(int i=0;i<monsters.length;i++) {
                    if(monsters[i].getHealth()<=0) {
                        this.numOfTotalEnemies--;
                        Monster[]temp2=new Monster[monsters.length-1];
                        for(int j=0;j<temp2.length;j++) {
                            if(j<i)
                                temp2[j]=monsters[j];
                            else
                                temp2[j]=monsters[j+1];

                        }
                        monsters=temp2;
                    }

                }

                for(int i=0;i<monsters.length;i++) {
                    int[]place=monsters[i].whereToMove(p);
                    Visited pi=board.getBoard()[place[0]][place[1]];
                    EnemiesStep es=new EnemiesStep(monsters[i]);
                    String move2="";
                    move2=o.move(es, pi, board.getBoard());

                    if(!move2.equals("")) {
                        System.out.println(monsters[i].getName()+" engaged in combat with "+p.getName());
                        System.out.println(monsters[i].description());
                        System.out.println(p.description());
                        int count=0;
                        for(int j=0;j<move2.length();j++)
                            if(move2.charAt(j)==',') {
                                if(!move2.substring(count, j).equals(""))
                                    System.out.println(move2.substring(count, j));
                                if(move2.substring(count, j).equals("Game Over!"))
                                    this.endGame=true;
                                count=j+1;
                            }
                    }
                }



                for(int i=0;i<traps.length;i++) {
                    if(traps[i].getHealth()<=0) {
                        this.numOfTotalEnemies--;
                        Trap[]temp2=new Trap[traps.length-1];
                        for(int j=0;j<temp2.length;j++) {
                            if(j<i)
                                temp2[j]=traps[j];
                            else
                                temp2[j]=traps[j+1];

                        }
                        traps=temp2;
                    }

                }

                for(int i=0;i<traps.length;i++) {
                    Visited pi=p;
                    TrapsStep ts=new TrapsStep(traps[i]);
                    String move2="";
                    traps[i].description();
                    move2=o.move(ts, pi, board.getBoard());
                    if(!move2.equals("")) {
                        System.out.println(traps[i].getName()+" engaged in combat with "+p.getName());
                        System.out.println(traps[i].description());
                        System.out.println(p.description());
                        int count=0;
                        for(int j=0;j<move2.length();j++)
                            if(move2.charAt(j)==',') {
                                if(!move2.substring(count, j).equals(""))
                                    System.out.println(move2.substring(count, j));
                                if(move2.substring(count, j).equals("Game Over!"))
                                    this.endGame=true;
                                count=j+1;
                            }
                    }
                }

                for(int i=0;i<enemies.length;i++) {
                    if(enemies[i].getHealth()<=0) {
                        Enemy[]temp2=new Enemy[enemies.length-1];
                        for(int j=0;j<temp2.length;j++) {
                            if(j<i)
                                temp2[j]=enemies[j];
                            else
                                temp2[j]=enemies[j+1];

                        }
                        enemies=temp2;
                    }

                }

                if(!this.endGame) {
                    for(int i=0;i<board.getBoard().length;i++)
                        System.out.println(board.printLine(i));
                    System.out.println(p.description());
                }
                o.notifiyGameTick();
            }

            for(int i=0;i<board.getBoard().length;i++)
                System.out.println(board.printLine(i));
            if(!endGame)
                System.out.println("The Level is Finished!");

        }
        System.out.println("YOU WON!!!");


    }
    public List<Enemy> getEnemiesFromLevel(File levelFile) throws IOException {
        List<Enemy> enemies = new ArrayList<>();

        try (BufferedReader br = new BufferedReader(new FileReader(levelFile))) {
            String line;
            int row = 0;
            while ((line = br.readLine()) != null) {
                for (int col = 0; col < line.length(); col++) {
                    char character = line.charAt(col);
                    if (isEnemyCharacter(character)) {
                        Enemy enemy = createEnemy(character);
                        enemy.initialize(new Position(row, col));
                        enemies.add(enemy);
                    }
                }
                row++;
            }
        }
        return enemies;
    }

    private boolean isEnemyCharacter(char character) {
        return character == 's' || character == 'k' || character == 'M' || character == 'q' || character == 'z' || character == 'b'
                || character == 'g' || character == 'w' || character == 'C' || character == 'K' || character == 'B'
                || character == 'Q' || character == 'D';
    }

    private Enemy createEnemy(char character) {
        switch (character) {
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
            default:
                throw new IllegalArgumentException("Invalid enemy character: " + character);
        }
    }
    public static int getLineWidth(String filePath) throws IOException {
        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String firstLine = reader.readLine();
            return firstLine.length();
        }

}
