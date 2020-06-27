package it.polito.tdp.food.model;

import java.util.LinkedList;
import java.util.List;
import java.util.Set;

import org.jgrapht.Graph;
import org.jgrapht.Graphs;
import org.jgrapht.graph.DefaultWeightedEdge;
import org.jgrapht.graph.SimpleWeightedGraph;

import it.polito.tdp.food.db.FoodDao;

public class Model {

	private FoodDao dao;
	private Graph<String, DefaultWeightedEdge> grafo;

	public Model() {
		this.dao = new FoodDao();
	}

	public List<String> getAllPortion() {
		return dao.getAllPortion();
	}

	public void creaGrafo(int C) {
		grafo = new SimpleWeightedGraph<String, DefaultWeightedEdge>(DefaultWeightedEdge.class);

		// VERTICI
		Graphs.addAllVertices(grafo, dao.getAllVertex(C));

		// ARCHI
		for (Arco a : dao.getAllEdges(C)) {
			Graphs.addEdge(grafo, a.getP1(), a.getP2(), a.getPeso());
		}

	}

	public int numVertex() {
		return grafo.vertexSet().size();
	}

	public int numEdges() {
		return grafo.edgeSet().size();
	}

	public List<Arco> getVicini(String inizio){
		List<Arco> ls = new LinkedList<Arco>();
		for(String v: Graphs.neighborListOf(grafo, inizio)) {
			ls.add(new Arco(inizio, v, (int) grafo.getEdgeWeight(grafo.getEdge(inizio, v))));
		}
		return ls;
	}

	public Set<String> getAllVertex() {
		return grafo.vertexSet();
	}
}
