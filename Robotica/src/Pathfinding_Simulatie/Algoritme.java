package Pathfinding_Simulatie;

public enum Algoritme {
	AStar(1),
	JPS(2),
	Dijkstra(3),
	HPA(4),
	Greedy(5);
	
	private int num;
	
	Algoritme(int num){
    	this.setNum(num);	    
    }

	public int getNum() {
		return num;
	}

	public void setNum(int num) {
		this.num = num;
	}
}
