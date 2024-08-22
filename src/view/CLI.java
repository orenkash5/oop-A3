package view;

import model.game.Board;
import model.tiles.units.Unit;
import model.tiles.units.players.Player;
import model.tiles.units.players.Warrior;

public class CLI  extends View {
    public void display(String message) {
        System.out.println(message);
    }

    public void displayBoard(Board board) {
        this.display(board.toString());
    }

    public void displayStats(Board board) {
        Player player = board.getPlayer();
        this.display(player.description());
    }

    public void displayCombatInfo(Unit combatant1, Unit combatant2, int attack, int defence, int damageTaken) {
        System.out.println("attacker: " + combatant1.description());
        System.out.println("attack " + attack + " defence " + defence + " damage taken " + damageTaken);
        System.out.println("defender: " + combatant2.description());
    }

    public void displayLevelUp(Player player){
        System.out.println(player.description());
    }
}
