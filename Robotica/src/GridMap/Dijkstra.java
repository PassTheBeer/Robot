package GridMap;

import java.util.ArrayList;

//now we must create graph object and implement dijkstra algorithm
public class Dijkstra {
	
	Grid grid = new Grid();
    D_Node[] nodes = new D_Node[Grid.aantal_nodes];
	private D_Edge[][] edges;
	private int noOfEdges;
	private static double pytho = Math.sqrt(Math.pow(Grid.lengte_node, 2) + Math.pow(Grid.lengte_node, 2));
	private double waarde = Grid.lengte_node;
	private static double max_waarde = 1000000.0;
	private boolean isObj;
	

	// next video to implement the Dijkstra algorithm !!!

	public void BerekenPad(ArrayList<Coords> nc, ArrayList<Coords> ob) {
		
		for(int i=0; i<Grid.aantal_nodes; i++) {
			for(int y=0; y<grid.aantal_obstakels; y++) {
				if(nc.get(i).getX() == ob.get(y).getX() && nc.get(i).getY() == ob.get(y).getY()) {
					nodes[i] = new D_Node("D_Node " + i, true);
					break;
				}
				else {
					nodes[i] = new D_Node("D_Node " + i, false);
				}
			}
		}
		for(int i=0; i<Grid.aantal_nodes; i++) {
			//D_Node Links bovenin
		/*	for(int y=0; y<Grid.aantal_obstakels; y++) {
				if(nc.get(i).getX() == ob.get(y).getX() && nc.get(i).getY() == ob.get(y).getY()) {
					Grid.lengte_node = max_waarde;
					pytho = max_waarde;
					break;
				}
				else {
					Grid.lengte_node = waarde;
					pytho = Math.sqrt(Math.pow(Grid.lengte_node, 2) + Math.pow(Grid.lengte_node, 2));
				}
				
			} */
			if(nc.get(i).getX() == 0 && nc.get(i).getY() == 0) {
           	 nodes[i].adjacencies = new D_Edge[]{
        			new D_Edge(i, i + 1, Grid.lengte_node),
        			new D_Edge(i, i + Grid.grid_width, Grid.lengte_node),
        			new D_Edge(i, i + Grid.grid_width + 1, pytho)
        	            };
           		}
			//D_Node links onder in
           else if(nc.get(i).getX() == 0 && nc.get(i).getY() == Grid.grid_height - 1) {
      		 nodes[i].adjacencies = new D_Edge[]{
   					new D_Edge(i, i + 1, Grid.lengte_node),
   					new D_Edge(i, i - Grid.grid_width, Grid.lengte_node),
   					new D_Edge(i, i - Grid.grid_width + 1, pytho)
   	            };
      		}
			//D_Node rechts bovenin
           else if(nc.get(i).getX() == Grid.grid_width - 1 && nc.get(i).getY() == 0) {
        	 nodes[i].adjacencies = new D_Edge[]{
   					new D_Edge(i, i - 1, Grid.lengte_node),
   					new D_Edge(i, i + Grid.grid_width, Grid.lengte_node),
   					new D_Edge(i, i + Grid.grid_width - 1, pytho)
   	            };
      		}
			//D_Node rechts onderin
           else if(nc.get(i).getX() == Grid.grid_width - 1 && nc.get(i).getY() == Grid.grid_height - 1) {
        	   nodes[i].adjacencies = new D_Edge[]{
   					new D_Edge(i, i - 1, Grid.lengte_node),
   					new D_Edge(i, i - Grid.grid_width, Grid.lengte_node),
   					new D_Edge(i, i - Grid.grid_width - 1, pytho)
   	            };
      		}
			//Alle nodes links tegen de edge
           else if(nc.get(i).getX() == 0) {
        	   nodes[i].adjacencies = new D_Edge[]{
   					new D_Edge(i, i + 1, Grid.lengte_node),
   					new D_Edge(i, i + Grid.grid_width, Grid.lengte_node),
   					new D_Edge(i, i - Grid.grid_width, Grid.lengte_node),
   					new D_Edge(i, i + Grid.grid_width + 1, pytho),
   					new D_Edge(i, i - Grid.grid_width + 1, pytho)
   	            };
      		}
			//Alle nodes rechts tegen de edge
           else if(nc.get(i).getX() == Grid.grid_width - 1) {
        	   nodes[i].adjacencies = new D_Edge[]{
         			new D_Edge(i, i - 1, Grid.lengte_node),
   					new D_Edge(i, i + Grid.grid_width, Grid.lengte_node),
   					new D_Edge(i, i - Grid.grid_width, Grid.lengte_node),
   					new D_Edge(i, i + Grid.grid_width - 1, pytho),
   					new D_Edge(i, i - Grid.grid_width - 1, pytho)
      	            };
         		}
			//Alle nodes bovenin tegen de edge
           else if(nc.get(i).getY() == 0) {
        	   nodes[i].adjacencies = new D_Edge[]{
         			new D_Edge(i, i + 1, Grid.lengte_node),
   					new D_Edge(i, i - 1, Grid.lengte_node),
   					new D_Edge(i, i + Grid.grid_width, Grid.lengte_node),
   					new D_Edge(i, i + Grid.grid_width - 1, pytho),
   					new D_Edge(i, i + Grid.grid_width + 1, pytho)
      	            };
         		}
			//Alle nodes onderin tegen de edge
           else if(nc.get(i).getY() == Grid.grid_height - 1) {
        	   nodes[i].adjacencies = new D_Edge[]{
         			new D_Edge(i, i + 1, Grid.lengte_node),
   					new D_Edge(i, i - 1, Grid.lengte_node),
   					new D_Edge(i, i - Grid.grid_width, Grid.lengte_node),
   					new D_Edge(i, i - Grid.grid_width + 1, pytho),
   					new D_Edge(i, i - Grid.grid_width - 1, pytho)
      	            };
         		}
			//Elke andere node. (alles in het midden)
           else {
        	   nodes[i].adjacencies = new D_Edge[]{
        			new D_Edge(i, i + 1, Grid.lengte_node),
					new D_Edge(i, i - 1, Grid.lengte_node),
					new D_Edge(i, i + Grid.grid_width, Grid.lengte_node),
					new D_Edge(i, i - Grid.grid_width, Grid.lengte_node),
					new D_Edge(i, i + Grid.grid_width + 1, pytho),
					new D_Edge(i, i + Grid.grid_width - 1, pytho),
					new D_Edge(i, i - Grid.grid_width + 1, pytho),
					new D_Edge(i, i - Grid.grid_width - 1, pytho)
			   };
           }
			
		}
		
		/*
		D_Edge[] edges = {
			  new D_Edge(0, 2, 1), new D_Edge(0, 3, 4), new D_Edge(0, 4, 2),
			  new D_Edge(0, 1, 3), new D_Edge(1, 3, 2), new D_Edge(1, 4, 3),	
			  new D_Edge(1, 5, 1), new D_Edge(2, 4, 1), new D_Edge(3, 5, 4),
			  new D_Edge(4, 5, 2), new D_Edge(4, 6, 7), new D_Edge(4, 7, 2),
			  new D_Edge(5, 6, 4), new D_Edge(6, 7, 5)
		}; */
		  
		
		
		// add all the edges to the nodes, each edge added to two nodes (to and from)
		
		
	//	for (int n = 0; n < Grid.aantal_nodes; n++) {
		//	this.nodes[n] = new D_Node("D_Node " + n);
	//	}
		/*for(int i=0; i<Grid.aantal_nodes; i++) {		
			for(int y=0; y<Grid.aantal_obstakels; y++) {
				if(nc.get(i).getX() == ob.get(y).getX() && nc.get(i).getY() == ob.get(y).getY()) {
					nodes[i].setDistanceFromSource(max_waarde);
					continue;
					//nodes[i].setDistanceFromSource(max_waarde);
				}				
			}
		}*/
		
		noOfEdges = 0;
		
		for(int i=0; i<nodes.length; i++) {		
			for (int j=0; j <nodes[i].adjacencies.length ; j++) {
				nodes[nodes[i].adjacencies[j].getFromNodeIndex()].getEdges().add(nodes[i].adjacencies[j]);
				nodes[nodes[i].adjacencies[j].getToNodeIndex()].getEdges().add(nodes[i].adjacencies[j]);
			}
			noOfEdges += nodes[i].adjacencies.length;
		} 
		
		 
		// node 0 as source
		
		nodes[0].setDistanceFromSource(0);
		int nextNode = 0;
		// visit every node
		for (int i = 0; i < this.nodes.length; i++) {
	
			// loop around the edges of current node
			ArrayList<D_Edge> currentNodeEdges = this.nodes[nextNode].getEdges();
			for (int joinedEdge = 0; joinedEdge < currentNodeEdges.size(); joinedEdge++) {
				int neighbourIndex = currentNodeEdges.get(joinedEdge).getNeighbourIndex(nextNode);
				// only if not visited
				if (!this.nodes[neighbourIndex].isVisited()) {
					double tentative = this.nodes[nextNode].getDistanceFromSource() + currentNodeEdges.get(joinedEdge).getLength();
					
					if (tentative < nodes[neighbourIndex].getDistanceFromSource()) {
						nodes[neighbourIndex].setDistanceFromSource(tentative);
					}
				}
			}
   
			// all neighbours checked so node visited
			nodes[nextNode].setVisited(true);
			// next node must be with shortest distance
			nextNode = getNodeShortestDistanced();
		}
}
	
	// now we're going to implement this method in next part !
	private int getNodeShortestDistanced() {
		int storedNodeIndex = 0;
		double storedDist = Integer.MAX_VALUE;
		
		for (int i = 0; i < this.nodes.length; i++) {
			double currentDist = this.nodes[i].getDistanceFromSource();
			if (!this.nodes[i].isVisited() && currentDist < storedDist) {
				storedDist = currentDist;
				storedNodeIndex = i;
			}
		}
		return storedNodeIndex;
	}

	// display result
	public void printResult() {
		String output = "Number of nodes = " + Grid.aantal_nodes;
		output += "\nNumber of edges = " + this.noOfEdges;
		output += "\nNumer of obstacles = " + grid.aantal_obstakels;
		for (int i = 0; i < this.nodes.length; i++) {
			output += ("\nThe shortest distance from node 0 to node " + i + " is " + nodes[i].getDistanceFromSource());
		}
		System.out.println(output);
		System.out.println("");
	}	

	 
	public D_Edge[][] getEdges() {
		return edges;
	}
	public int getNoOfEdges() {
		return noOfEdges;
	}
}

class D_Node {
	
	private double distanceFromSource = Integer.MAX_VALUE;
	private boolean visited;
	public ArrayList<D_Edge> edges = new ArrayList<D_Edge>(); // now we must create edges
	public final String value;
	public D_Edge[] adjacencies;
	public boolean isObject;
	
	D_Node(String id, boolean isObject){
		value = id;
		this.isObject = isObject;
	}
	
	public double getDistanceFromSource() {
		return distanceFromSource;
	}
	public void setDistanceFromSource(double distanceFromSource) {
		this.distanceFromSource = distanceFromSource;
	}
	public boolean isVisited() {
		return visited;
	}
	public void setVisited(boolean visited) {
		this.visited = visited;
	}
	public ArrayList<D_Edge> getEdges() {
		return edges;
	}
	public void setEdges(ArrayList<D_Edge> edges) {
		this.edges = edges;
	}
}

class D_Edge {
	  private int fromNodeIndex;
	  private int toNodeIndex;
	  private double length;
	  
	  public D_Edge(int fromNodeIndex, int toNodeIndex, double length) {
		  this.fromNodeIndex = fromNodeIndex;
		  this.toNodeIndex = toNodeIndex;
		  this.setLength(length);
	  }
	  
	  
	  public int getFromNodeIndex() {
		  return fromNodeIndex;
	  }
	  public int getToNodeIndex() {
		  return toNodeIndex;
	  }
	  
	  // determines the neighbouring node of a supplied node, based on the two nodes connected by this edge
	  
	  public int getNeighbourIndex(int nodeIndex) {
		  if (this.fromNodeIndex == nodeIndex) {
			  return this.toNodeIndex;
		  } 
		  else {
			  return this.fromNodeIndex;
		  }	
	  }


	public double getLength() {
		return length;
	}


	public void setLength(double length) {
		this.length = length;
	}
}
