package pluscourtchemin;
import java.util.*; 
import briques.*;


public class Dijkstra {

	    private int dist[]; 
	    private Set<Integer> settled; 
	    private PriorityQueue<Noeud> pq; 
	    private Noeud depart;
	    private int V;
	    private List<List<Noeud> > graph; 
	    //private LinkedList<LinkedList<Case>> cases;
	    private int parents[];
	  
	    public Dijkstra(Noeud depart, List<List<Noeud>> graph) 
	    { 
	        this.depart = depart;
	        V = graph.size();
	        dist = new int[graph.size()]; 
	        settled = new HashSet<Integer>(); 
	        pq = new PriorityQueue<Noeud>();
	        this.graph = graph;
	        //this.cases = new LinkedList<LinkedList<Case>>();
	        parents = new int[graph.size()];
	        parents[depart.getNumberGraph()] = -1;
	    } 
	    
	    // Function for Dijkstra's Algorithm 
	    public void dijkstra() 
	    { 
	         
	  
	        for (int i = 0; i < V; i++) 
	            dist[i] = Integer.MAX_VALUE; 
	  
	        // Add source node to the priority queue 
	        pq.add(depart); 
	  
	        // Distance to the source is 0 
	        dist[depart.getNumberGraph()] = 0; 
	        while (settled.size() != V) { 
	  
	            // remove the minimum distance node  
	            // from the priority queue  
	            int u = pq.remove().getNumberGraph(); 
	  
	            // adding the node whose distance is 
	            // finalized 
	            settled.add(u); 
	  
	            e_Neighbours(u); 
	        } 
	    } 
	    
	    private void e_Neighbours(int u) 
	    { 
	        int edgeDistance = -1; 
	        int newDistance = -1; 
	  
	        // All the neighbors of v 
	        for (int i = 0; i < graph.get(u).size(); i++) { 
	            Noeud v = graph.get(u).get(i); 
	  
	            // If current node hasn't already been processed 
	            if (!settled.contains(v.getNumberGraph())){ 
	                edgeDistance = v.getTime(); 
	                newDistance = dist[u] + edgeDistance; 
	  
	                // If new distance is cheaper in cost 
	                if (newDistance < dist[v.getNumberGraph()]) {
	                    dist[v.getNumberGraph()] = newDistance; 
	                	parents[v.getNumberGraph()] = u;
	                }
	                // Add the current node to the queue 
	     
	                pq.add(new Noeud(dist[v.getNumberGraph()], v.getCase(), v.getNumberGraph()));
	            } 
	        } 
	  
	    } 
	    
	    public void printPath(int v, int[] parents) {
	    	if (v == -1) return;
	    	printPath(parents[v], parents); 
	        System.out.print(v + " "); 
	    }
	    
	    public int[] getDist() {
	    	return dist;
	    }
	    
	    public int[] getparents() {
	    	return parents;
	    }
	    

	 
}
