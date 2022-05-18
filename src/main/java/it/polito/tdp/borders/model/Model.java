package it.polito.tdp.borders.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.jgrapht.Graph;
import org.jgrapht.Graphs;
import org.jgrapht.graph.DefaultEdge;
import org.jgrapht.graph.SimpleGraph;
import org.jgrapht.traverse.BreadthFirstIterator;

import it.polito.tdp.borders.db.BordersDAO;

public class Model {
	
	BordersDAO dao = new BordersDAO();

	Graph<Country, DefaultEdge> grafo;
	
	public Model() {
	}

	public List<Country> getCountries(){
		return this.dao.getListaCountries();
	}
	
	public void creaGrafo(int anno) {
		grafo = new SimpleGraph<Country, DefaultEdge>(DefaultEdge.class);
		
		//prendo i paesi e richiamo il metodo per ottenere i confini
		Map<Integer, Country> countries = dao.loadAllCountries();
		List<Border> borders = dao.getBorders(anno, countries);
		
		//METODO ALTERNATIVO PER AGGIUNGERE VERTICI
		//Graphs.addAllVertices(this.grafo, countries.values());
		
		
		//creo grafo
		for(Border b : borders) {
			grafo.addVertex(b.getC1());
			grafo.addVertex(b.getC2());
			grafo.addEdge(b.getC1(), b.getC2());
		
		}
		
	}


	public Graph<Country, DefaultEdge> getGrafo() {
		return grafo;
	}

	public List<Country> cercaStatiRaggiungibili(Country paesePartenza) {
		//ALBERO DI VISITA DEL GRAFO: CON LE FUNZIONI DI JGRAPHT
		
		List<Country> statiRaggiungibili = new ArrayList<Country>();
		BreadthFirstIterator<Country, DefaultEdge> bfi = new BreadthFirstIterator<>(this.grafo, paesePartenza);
		
		while(bfi.hasNext()) {
			Country c = bfi.next();
			statiRaggiungibili.add(c);
		}
		return statiRaggiungibili;
		
	}
	public List<Country> cercaStatiRaggiungibili2(Country partenza){
		//METODO ITERATIVO LISTA DI VERTICI VISITATI E LISTA DI VERTICI DA VISITARE 
		List<Country> visited = new ArrayList<Country>();
		visited.add(partenza);
		
		List<Country> toVisit = new ArrayList<Country>();
		toVisit.addAll(Graphs.neighborListOf(this.grafo, partenza));
		
		while(!toVisit.isEmpty()) {
			//PRENDO PROSSIMO PAESE DA VISITARE
			Country next = toVisit.get(0);
			toVisit.remove(0);
			
			//PRENDO I VICINI DI QUESTO PAESE
			List<Country> neighbours = Graphs.neighborListOf(this.grafo, next);
			
			//RIMUOVO QUELLI CHE HO GIA VISITATO
			neighbours.removeAll(visited);
			
			//E QUELLI CHE GIA SAPEVO DI DOVER VISITARE
			neighbours.removeAll(toVisit);
			
			//AGGIUNGO I RIMANENTI ALLA LISTA DI VERTICI DA VISITARE
			toVisit.addAll(neighbours);
			
		}
		return visited;
	}
	
	public List<Country> cercaStatiRaggiungibili3 (Country partenza){
		//METODO RICORSIVO
		List <Country> visited = new ArrayList<Country>();
		visitaRicorsiva(visited, partenza);
		return visited;
		
	}
	public void visitaRicorsiva(List<Country> visited, Country next) {
		
		visited.add(next);
		
		for(Country c : Graphs.neighborListOf(this.grafo, next)) {
			if(!visited.contains(c))
				visitaRicorsiva(visited, c);
		}
	}
	

}
