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
	    private Set<Integer> optimaux; //sommet pour lequel on a trouv� le chemin avec le temps optimal
	    private PriorityQueue<Noeud> fileAPriorite; 
	    private Noeud depart;
	    private int arrivee;
	    private List<List<Noeud> > graphAdjacence; 
	    private List<Noeud> graph;
	    private Boolean eau;
	   
	    private int parents[];
	    private Carte carte;  //=> liste noeud
	    
	    // Stockage des chemins
	    //private List<List<Case>> chemins;
	    private List<Case> chemin;
	    
	    public Dijkstra(Noeud depart, int arrivee, List<List<Noeud>> graphAdjacence, List<Noeud> graph, Carte carte) { 
	        this.depart = depart;
	        this.arrivee = arrivee;
	        this.graph = graph;
	        this.graphAdjacence = graphAdjacence;
	        distance = new int[graph.size()]; 
	        optimaux = new HashSet<Integer>(); 
	        fileAPriorite = new PriorityQueue<Noeud>();
	        //this.cases = new LinkedList<LinkedList<Case>>();
	        System.out.println("taille : " +graph.size());
	        
	        parents = new int[graph.size()];
	        //chemins = new ArrayList<List<Case>>();
	        chemin = new ArrayList<Case>();
	        
	        for (int i = 0; i < graph.size(); i++) {
	        	parents[i] = -2;
	        	distance[i] = Integer.MAX_VALUE;
	        }
	        
	        parents[depart.getNumeroCase()] = -1;
	        distance[depart.getNumeroCase()] = 0;
	        
	        this.carte = carte;
	        this.eau = false;
	    } 
	    
	    public Dijkstra(Noeud depart, List<List<Noeud>> graphAdjacence, List<Noeud> graph, Carte carte) { 
	        this.depart = depart;
	        this.graph = graph;
	        this.graphAdjacence = graphAdjacence;
	        distance = new int[graph.size()]; 
	        optimaux = new HashSet<Integer>(); 
	        fileAPriorite = new PriorityQueue<Noeud>();
	        //this.cases = new LinkedList<LinkedList<Case>>();
	        System.out.println("taille : " +graph.size());
	        
	        parents = new int[graph.size()];
	        //chemins = new ArrayList<List<Case>>();
	        chemin = new ArrayList<Case>();
	        
	        for (int i = 0; i < graph.size(); i++) {
	        	parents[i] = -2;
	        	distance[i] = Integer.MAX_VALUE;
	        }
	        
	        parents[depart.getNumeroCase()] = -1;
	        distance[depart.getNumeroCase()] = 0;
	        
	        this.carte = carte;
	        this.eau = true;
	    } 
	    
	    
	    // Function for Dijkstra's Algorithm 
	    public void dijkstra() { 
	         
	    	Set<Noeud> optimaux = new HashSet<>();
		    Set<Noeud> aTraiter = new HashSet<>();
		    
		    
		    aTraiter.add(depart);
			 
		    while (aTraiter.size() != 0) {
		        Noeud currentNode = this.getNoeudDistanceMin(aTraiter);
		        aTraiter.remove(currentNode);
		        for (Noeud adjacentNode : graphAdjacence.get(currentNode.getNumeroCase())) {
		            if (!optimaux.contains(adjacentNode)) {
		                updateDistance(adjacentNode, currentNode);
		                aTraiter.add(adjacentNode);
		            }
		        }
		        optimaux.add(currentNode);
		        if (! eau)  {
		        if (currentNode.getNumeroCase() == this.arrivee) break;
		        }
		        else {
		        	if (currentNode.getEau()) {
		        		this.arrivee = currentNode.getNumeroCase();
		        		break;
		        	}
		        }
		    }
	    /*
	        for (int sommet = 0; sommet < nbSommets; sommet++) {
	            distance[sommet] = Integer.MAX_VALUE; 
	        }
	  
		    // Add source node to the priority queue 
		    fileAPriorite.add(depart); 
		  
		    // Distance to the source is 0 
		    distance[depart.getNumeroCase()] = 0; 
		    while (sommetsOptimaux.size() != nbSommets) { 
		    	System.out.println("pas fini");
		    	
		    	try {
		    	// remove the minimum distance node  
			    // from the priority queue  
		      	int sommetOptimal = fileAPriorite.remove().getNumeroCase();
		      		if (!sommetsOptimaux.contains(sommetOptimal)) {
			          	if (sommetOptimal == this.arrivee) {
		            		this.stockchemin();
		            		return;
		            	}
			          	
				        // adding the node whose distance is 
				    	// finalized   
			           	sommetsOptimaux.add(sommetOptimal); 
			      	 
			           	addVoisinsFile(sommetOptimal);
		      		}
		    	} catch (NoSuchElementException e) {
		    		// Dans le cas où les sommets ne peuvent pas être atteint
		    		System.out.println("pas atteignable");
		    		break;
		        }
		            
         
	        } 
	        
		    this.stockchemin();*/
		        /*
	        for (int i = 0; i < nbSommets; i++) {
	        	this.stockchemin(chemins.get(i), i, this.parents);
	        }
	        */
		    
		    this.backtrack();
	    } 
	    
	    
		private Noeud getNoeudDistanceMin(Set < Noeud > aTraiter) { // distance vers le sommet
		    Noeud noeudDistanceMin = null;
		    int distanceMin = Integer.MAX_VALUE;
		    for (Noeud noeud : aTraiter) {
		        int dist = distance[noeud.getNumeroCase()];
		        if (dist < distanceMin) {
		            distanceMin = dist;
		            noeudDistanceMin = noeud;
		        }
		    }
		    return noeudDistanceMin;
		}
		
		
		//ATTENTION, ce serait mieux d'utiliser des int d�s le debut
		private void updateDistance(Noeud evaluationNode, Noeud sourceNode) {
				    int sourceDistance = distance[sourceNode.getNumeroCase()];
				    if (sourceDistance + sourceNode.getTime() < distance[evaluationNode.getNumeroCase()]) {
				        distance[evaluationNode.getNumeroCase()] = sourceDistance + sourceNode.getTime();
				        parents[evaluationNode.getNumeroCase()] = sourceNode.getNumeroCase();
				    }
				}
	    
		
		/*
	    private void addVoisinsFile(int sommet) { 
	        int edgeDistance = -1; 
	        int newDistance = -1; 
	        Noeud noeud;
	  
	        // All the neighbors of sommet
	        for (int destinationNode = 0; destinationNode < graphAdjacence.get(sommet).size(); destinationNode++) { 
	 
	        	noeud = graphAdjacence.get(sommet).get(destinationNode);
	            // If current node hasn't already been processed 
	            if (!optimaux.contains(destinationNode) ){ 
	                edgeDistance = noeud.getTime(); 
	                newDistance = distance[sommet] + edgeDistance; 
	  
	                // If new distance is cheaper in cost 
	                if (newDistance < distance[noeud.getNumeroCase()]) {
	                    distance[noeud.getNumeroCase()] = newDistance; 
	                	parents[noeud.getNumeroCase()] = sommet;
	                }
	                
	                // Add the current node to the queue 
	                fileAPriorite.add(new Noeud(distance[noeud.getNumeroCase()], noeud.getNumeroCase()));
	            } 
	        }   
	    } 
	    */
	    
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
	    public void backtrack(int sommet) {
	    	if (sommet == -1) return;
	    	if (this.parents[sommet] == -2) return;
	    	this.backtrack(parents[sommet]);
	    	chemin.add(this.carte.tranformeNombreCase(sommet));
	    }
	    
	    public void backtrack() {
	    	if(parents[arrivee] == -1) {
	    		System.out.println("inaccessible");
	    		return;
	    	}
	    	backtrack(arrivee);
	    }
	    
	    
	    
	    public int[] getDistance() {
	    	return distance;
	    }
	    
	    public void printDistance() {
	    	StringJoiner stringJoiner1 =  new StringJoiner(", ",  "[",  "]") ;
	    	for (int i=0; i<this.getDistance().length; i++) {
				stringJoiner1.add(this.getDistance()[i] + "");

			}
			System.out.println("distance : " +stringJoiner1.toString());
	    }
	    
	    public void printParents() {
	    	StringJoiner stringJoiner1 =  new StringJoiner(", ",  "[",  "]") ;
	    	for (int i=0; i<this.getParents().length; i++) {
				stringJoiner1.add(this.getParents()[i] + "");

			}
			System.out.println("parents : " +stringJoiner1.toString());
	    }
	    
	    public int[] getParents() {
	    	return parents;
	    }
	    
	    
	    
	    
	    public List<Case> getChemin() {
	    	return this.chemin;
	    }
	 
}
