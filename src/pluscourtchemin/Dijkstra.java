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

	    private int distance[]; 
	    private Set<Integer> sommetsOptimaux; //sommet pour lequel on a trouv� le chemin avec le temps optimal
	    private PriorityQueue<Noeud> fileAPriorite; 
	    private Noeud depart;
	    private int nbSommets;
	    private List<List<Noeud> > graph; 
	   
	    private int parents[];
	    private Carte carte;
	    
	    // Stockage des chemins
	    private List<List<Case>> chemins;
	    
	    public Dijkstra(Noeud depart, List<List<Noeud>> graph, Carte carte) { 
	        this.depart = depart;
	        nbSommets = graph.size();
	        distance = new int[graph.size()]; 
	        sommetsOptimaux = new HashSet<Integer>(); 
	        fileAPriorite = new PriorityQueue<Noeud>();
	        this.graph = graph;
	        //this.cases = new LinkedList<LinkedList<Case>>();
	        parents = new int[graph.size()];
	        chemins = new ArrayList<List<Case>>();
	        for (int i = 0; i < parents.length; i++) {
	        	parents[i] = -2;
	        	chemins.add(new ArrayList<Case>());
	        }
	        
	        parents[depart.getNumeroCase()] = -1;
	        this.carte = carte;
	    } 
	    
	    // Function for Dijkstra's Algorithm 
	    public void dijkstra() { 
	         
	  
	        for (int sommet = 0; sommet < nbSommets; sommet++) 
	            distance[sommet] = Integer.MAX_VALUE; 
	  
		        // Add source node to the priority queue 
		        fileAPriorite.add(depart); 
		  
		        // Distance to the source is 0 
		        distance[depart.getNumeroCase()] = 0; 
		        while (sommetsOptimaux.size() != nbSommets) { 
		  
		            // remove the minimum distance node  
		            // from the priority queue  
		            try {
		            	int sommetOptimal = fileAPriorite.remove().getNumeroCase(); 
		            	sommetsOptimaux.add(sommetOptimal); 
		      	  
		            	addVoisinsFile(sommetOptimal); 
		            } catch (NoSuchElementException e) {
		            	// Dans le cas où les sommets ne peuvent pas être atteint
		            	break;
		            }
		            
		            // adding the node whose distance is 
		            // finalized 
	           
	        } 
	        
	        for (int i = 0; i < nbSommets; i++) {
	        	this.stockchemin(chemins.get(i), i, this.parents);
	        }
	    } 
	    
	    private void addVoisinsFile(int sommet) { 
	        int edgeDistance = -1; 
	        int newDistance = -1; 
	  
	        // All the neighbors of sommet
	        for (int i = 0; i < graph.get(sommet).size(); i++) { 
	            Noeud noeud = graph.get(sommet).get(i); 
	  
	            // If current node hasn't already been processed 
	            if (!sommetsOptimaux.contains(noeud.getNumeroCase())){ 
	                edgeDistance = noeud.getTime(); 
	                newDistance = distance[sommet] + edgeDistance; 
	  
	                // If new distance is cheaper in cost 
	                if (newDistance < distance[noeud.getNumeroCase()]) {
	                    distance[noeud.getNumeroCase()] = newDistance; 
	                	parents[noeud.getNumeroCase()] = sommet;
	                }
	                // Add the current node to the queue 
	     
	                fileAPriorite.add(new Noeud(distance[noeud.getNumeroCase()], noeud.getCase(), noeud.getNumeroCase()));
	            } 
	        } 
	  
	    } 
	    // Fonction affiche chemin
	    public void printPath(int sommet, int[] parents) {
	    	if (sommet == -1) return;
	    	if (parents[sommet] == -2) {
	    		System.out.println("La case " + sommet + " est inatteignable");
	    		return;
	    	}
	    	printPath(parents[sommet], parents); 
	        System.out.print(this.carte.tranformeNombreCase(sommet) + " "); 
	    }
	    
	    // Fonction stock chemin 
	    public void stockchemin(List<Case> chemin, int sommet, int[] parents){
	    	if (sommet == -1) return;
	    	if (parents[sommet] == -2) return;
	    	this.stockchemin(chemin, parents[sommet], parents);
	    	chemin.add(this.carte.tranformeNombreCase(sommet));
	    }
	    
	    
	    
	    public int[] getDist() {
	    	return distance;
	    }
	    
	    public int[] getparents() {
	    	return parents;
	    }
	    
	    
	    public List<Case> getChemin(Case c) {
	    	int numCase = this.carte.transformeNombreCase(c);
	    	return this.chemins.get(numCase);
	    }
	 
}
