package pluscourtchemin;
import java.util.*; 
import briques.*;


/**
 * 
 * Algorithme de Dijkstra, prend en argument une matrice d'adjacence
 * dist contient les temps de déplacement du noeud de départ aux autres 
 * noeuds
 * 
 */

public class Dijkstra {

	    private int dist[]; 
	    private Set<Integer> settled; 
	    private PriorityQueue<Noeud> pq; 
	    private Noeud depart;
	    private int V;
	    private List<List<Noeud> > graph; 
	   
	    private int parents[];
	    private Carte carte;
	    
	    // Stockage des chemins
	    private List<List<Case>> chemins;
	    
	    public Dijkstra(Noeud depart, List<List<Noeud>> graph, Carte carte) 
	    { 
	        this.depart = depart;
	        V = graph.size();
	        dist = new int[graph.size()]; 
	        settled = new HashSet<Integer>(); 
	        pq = new PriorityQueue<Noeud>();
	        this.graph = graph;
	        //this.cases = new LinkedList<LinkedList<Case>>();
	        parents = new int[graph.size()];
	        chemins = new ArrayList<List<Case>>();
	        for (int i = 0; i < parents.length; i++) {
	        	parents[i] = -2;
	        	chemins.add(new ArrayList<Case>());
	        }
	        
	        parents[depart.getNumberGraph()] = -1;
	        
	        
	        
	        
	        this.carte = carte;
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
	            try {
	            int u = pq.remove().getNumberGraph(); 
	            settled.add(u); 
	      	  
	            e_Neighbours(u); 
	            } catch (NoSuchElementException e) {
	            	// Dans le cas où les sommets ne peuvent pas être atteint
	            	break;
	            }
	            
	            // adding the node whose distance is 
	            // finalized 
	           
	        } 
	        
	        for (int i = 0; i < V; i++) {
	        	this.stockchemin(chemins.get(i), i, this.parents);
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
	    // Fonction affiche chemin
	    public void printPath(int v, int[] parents) {
	    	if (v == -1) return;
	    	if (parents[v] == -2) {
	    		System.out.println("La case " + v + " est inatteignable");
	    		return;
	    	}
	    	printPath(parents[v], parents); 
	        System.out.print(this.tranformeNombreCase(v) + " "); 
	    }
	    
	    // Fonction stock chemin 
	    public void stockchemin(List<Case> chemin, int v, int[] parents){
	    	if (v == -1) return;
	    	if (parents[v] == -2) return;
	    	this.stockchemin(chemin, parents[v], parents);
	    	chemin.add(this.tranformeNombreCase(v));
	    }
	    
	    public Case tranformeNombreCase(int n) {
	    	int ligne = (int) n / this.carte.getNbColonnes();
	    	int colonne = (int) n - ligne * this.carte.getNbColonnes();
	    	return this.carte.getCase(ligne, colonne);
	    }
	    
	    public int[] getDist() {
	    	return dist;
	    }
	    
	    public int[] getparents() {
	    	return parents;
	    }
	    
	    public List<List<Case>> getChemins() {
	    	return chemins;
	    }
	 
}
