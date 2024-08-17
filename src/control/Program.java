package control;

import model.game.Game;

import java.io.IOException;

public class Program {
    public static void main(String[] args) throws IOException {
        Game game = new Game();
        game.runGame();
    }
}
