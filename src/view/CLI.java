package view;

import model.game.Board;
import model.tiles.units.players.Player;

public class CLI  extends View {
    public void display(String message) {
        System.out.println(message);
    }

    public void displayBoard(Board board) {
        this.display(board.toString());
    }
    public void displayStats(Board board) {
        Player player = board.getPlayer();
        this.display("name: " + player.toString() + "health: " + player.getHealth().getCurrent() + "attack points: " + player.getAttackDamage() + "defense points: "+player.getDefensePoints() + "experience: " + player. getExperience());
    }
}
