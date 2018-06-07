package Pathfinding_Simulatie;

import java.util.Comparator;

public class Coords {
    private int x;
    private int y;

    public boolean equals(Object o) {
        Coords c = (Coords) o;
        return c.getX() == getX() && c.getY() == getY();
    }

    public Coords(int x, int y) {
        super();
        this.setX(x);
        this.setY(y);
    }

    public int hashCode() {
        return getX() * 3 + getY() * 5;
    }
    
    public int getY() {
		return y;
	}

	public void setY(int y) {
		this.y = y;
	}

	public int getX() {
		return x;
	}

	public void setX(int x) {
		this.x = x;
	}

	public static Comparator<Coords> comp = new Comparator<Coords>() {
		
    	public int compare(Coords c1, Coords c2) {
			   int cy_1 = c1.getY();
			   int cy_2 = c2.getY();
			   return cy_1 - cy_2;
		   }};	
}
