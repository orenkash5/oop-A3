package model.tiles.units;

import model.tiles.Empty;
import model.tiles.Tile;
import model.tiles.Wall;
import model.tiles.units.enemies.Enemy;
import model.tiles.units.players.Player;
import utils.Health;
import utils.Position;
import utils.generators.Generator;
import utils.generators.RandomGenerator;
import view.CLI;

public abstract class Unit extends Tile {
    protected String name;
    protected Health health;
    protected int attack;
    protected int defense;

    protected Generator generator;


    public Unit(char tile, String name, int hitPoints, int attack, int defense) {
        super(tile);
        this.name = name;
        this.health = new Health(hitPoints);
        this.attack = attack;
        this.defense = defense;
        generator = new RandomGenerator();
    }

    public Unit initialize(Position p, Generator generator){
        super.initialize(p);
        this.generator = generator;
        return this;
    }

    public int attack(){
        return generator.generate(attack);
    }

    public int defend(){
        return generator.generate(defense);
    }

    public boolean alive(){
        return health.getCurrent() > 0;
    }

    public void battle(Unit enemy) {
        int attack = this.attack();
        int defense = enemy.defend();
        int damageTaken = enemy.health.takeDamage(attack - defense);
        CLI cli = new CLI();
        cli.displayCombatInfo(this, enemy, attack, defense, damageTaken);
    }

    public void interact(Tile t){
        t.accept(this);
    }

    public void visit(Empty e){
        swapPosition(e);
    }

    public void visit(Wall w){
        // Do nothing
    }

    public abstract void visit(Player p);
    public abstract void visit(Enemy e);


    public String getName(){
        return this.name;
    }
    public String description(){
        return name + " health " + health.toString() + " attack " + attack + " defence " + defense;
    }
    public Health getHealth(){
        return this.health;
    }



}
