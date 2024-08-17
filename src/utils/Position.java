package utils;

public class Position implements Comparable<Position>{
    private int x;
    private int y;

    public Position(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public double range(Position other){
        return Math.sqrt(Math.pow(this.x - other.x, 2) + Math.pow(this.y - other.y, 2));
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    @Override
    public int compareTo(Position other) {
        int cmpX = Integer.compare(this.y, other.y);
        if (cmpX != 0) {
            return cmpX;
        }

        return Integer.compare(this.x, other.x);
    }
}
