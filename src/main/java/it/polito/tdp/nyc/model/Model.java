package it.polito.tdp.nyc.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.jgrapht.Graph;
import org.jgrapht.Graphs;
import org.jgrapht.graph.DefaultWeightedEdge;
import org.jgrapht.graph.SimpleWeightedGraph;

import com.javadocmd.simplelatlng.LatLng;
import com.javadocmd.simplelatlng.LatLngTool;
import com.javadocmd.simplelatlng.util.LengthUnit;

import it.polito.tdp.nyc.db.NYCDao;

public class Model {
	
	private NYCDao dao;
	private Graph<String, DefaultWeightedEdge> grafo;
	private List<String> provider;
	private List<String> vertici;
	
	private List<String> percorso;

	public Model() {
		super();
		this.dao = new NYCDao();
		this.provider = this.dao.getProvider();
		
	}
	
	public void creaGrafo(String provider, double distance) {
		
		this.grafo = new SimpleWeightedGraph<String, DefaultWeightedEdge>(DefaultWeightedEdge.class);

		this.vertici = this.dao.getVertici(provider);
		
		Graphs.addAllVertices(this.grafo, vertici);
		
		aggiungiArchi(provider, distance);
	}
	
	public void aggiungiArchi(String provider, double distance) {
		
		List<Arco> archi = this.dao.getArchi(provider);
		
		
		for(Arco a : archi) {
			
			if(vertici.contains(a.getL1()) && vertici.contains(a.getL2())){
				
				double dis = Math.abs(LatLngTool.distance(a.getPos1(), a.getPos2(), LengthUnit.KILOMETER));
				 
				if(dis<=distance) {
					
					Graphs.addEdgeWithVertices(this.grafo, a.getL1(), a.getL2(), dis);			
				}
			}
			
		}
	}
	
	public List<Vicini> doAnalisi(){
		
		int viciniM = 0;
		
		List<Vicini> ver = new ArrayList<>();
		
		for(String l : vertici) {
				
			int grado = this.grafo.degreeOf(l);
			
			if(grado>viciniM) {
				
				viciniM = grado;
				ver.clear();
				ver.add(new Vicini(l, viciniM));	
			}
			else if(this.grafo.degreeOf(l) == viciniM) {
				ver.add(new Vicini(l, viciniM));
			}
				
		}
		return ver;
	}
	
    public List<String> findPath(String target, String evita) {
    	
    	this.percorso = new ArrayList<>();
    	
    	List<String> visited = new ArrayList<>();
		
		List<Vicini> localita = this.doAnalisi();
		
		Random ran = new Random();
		
		int index = ran.nextInt(localita.size());
		
		String partenza = localita.get(index).getLoc();
    	
        percorso.add(partenza);
        
        dfs(partenza, target, evita, visited, percorso);
        
        return percorso;
    }

    private boolean dfs(String current, String target, String substring, List<String> visited, List<String> path) {
        
    	visited.add(current);

        if (current.equals(target)) {
            return true;
        }

        List<String> neighbors = Graphs.neighborListOf(this.grafo, current);

        for (String neighbor : neighbors) {
            if (!visited.contains(neighbor) && !neighbor.contains(substring)) {
                path.add(neighbor);
                if (dfs(neighbor, target, substring, visited, path)) {
                    return true;
                }
                path.remove(path.size() - 1);
            }
        }

        return false;
    }
	
	public Graph<String, DefaultWeightedEdge> getGrafo() {
		return grafo;
	}

	public List<String> getProvider() {
		return provider;
	}
	
	public List<String> getVertici() {
		return vertici;
	}

	public String infoGrafo() {
		return "Grafo creato!\n#Vertici: "+this.grafo.vertexSet().size()+"\n#Archi: "+this.grafo.edgeSet().size();
	}
	
}
  
