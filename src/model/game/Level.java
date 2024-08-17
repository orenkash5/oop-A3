package model.game;

import model.tiles.units.enemies.Enemy;
import model.tiles.units.players.Player;

import java.io.*;

import java.util.List;

public class Level {
    Player player;
    Board board;
    List<Enemy> enemies;


    public File[] getLevels() throws IOException {
        File input = new File("C:\\Users\\owner\\IdeaProjects\\hw3\\levels_dir");
        File[] st = input.listFiles();
        for (int i = 0; i < st.length; i++) {
            if(st[i].isFile()){//other condition like name ends in html
                String content = "";
                BufferedReader in = new BufferedReader(new FileReader(st[i]));
                String str;
                while ((str = in.readLine()) != null) {
                    content += str;
                    System.out.println(str);

                }
            }
        }
        return st;

    }
}
