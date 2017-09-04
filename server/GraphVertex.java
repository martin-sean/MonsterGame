import java.util.ArrayList;

/*
 * The graph consists of vertices in order for the AI to know 
 * where it is moving to. This will represent the many movement
 * tiles on the board, as they will all be connected to each other
 * like grapes on a vine. Player and monster pieces will be placed
 * on this network to find the shortest route possible with Dijkstra�s
 * algorithm for the MonsterAI.
 * 
 * source: 
 * http://www.vogella.com/tutorials/JavaAlgorithmsDijkstra/article.html (steal the JUnit from this bad boy) 
 * also looking at
 * https://medium.com/@ssaurel/calculate-shortest-paths-in-java-by-implementing-dijkstras-algorithm-5c1db06b6541
 */

public class GraphVertex 
{
	// Declare class variables
	private int distance_from_source = Integer.MAX_VALUE;
	private boolean checked;
	private ArrayList<GraphEdge> edges = new ArrayList<GraphEdge>();
	
	// distance accessor
	public int get_distance_from_source()
	{
		return distance_from_source;
	}	
	
	// distance mutator
	public void set_distance_from_source(int distance_from_source)
	{
		this.distance_from_source = distance_from_source;
	}
	
	// boolean accessor
	public boolean is_checked()
	{
		return checked;
	}
	
	// boolean mutator
	public void set_checked(boolean checked) 
	{
		this.checked = checked;
	}
	
	// arrayList accessor
	public ArrayList<GraphEdge> get_edges() 
	{
		return edges;
	}
	
	// arrayList mutator
	public void set_edges(ArrayList<GraphEdge> edge_count) 
	{
		this.edges = edge_count;
	}	
}
