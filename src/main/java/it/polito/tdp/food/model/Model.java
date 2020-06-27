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

	private List<String> bestCammino;
	private int bestPeso;

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

	public List<Arco> getVicini(String inizio) {
		List<Arco> ls = new LinkedList<Arco>();
		for (String v : Graphs.neighborListOf(grafo, inizio)) {
			ls.add(new Arco(inizio, v, (int) grafo.getEdgeWeight(grafo.getEdge(inizio, v))));
		}
		return ls;
	}

	public Set<String> getAllVertex() {
		return grafo.vertexSet();
	}

	public void trovaBest(String partenza, int passi) {
		bestCammino = new LinkedList<String>();
		bestPeso = 0;

		List<String> parziale = new LinkedList<String>();
		parziale.add(partenza);

		ricorsiva(parziale, passi);
	}

	private void ricorsiva(List<String> parziale, int passi) {
		if (parziale.size() == passi) {
			if (calcolaPeso(parziale) > bestPeso) {
				bestPeso = calcolaPeso(parziale);
				bestCammino = new LinkedList<String>(parziale);
			}
			return;
		}

		for (Arco a : getVicini(parziale.get(parziale.size() - 1))) {
			String vicino = a.getP2();
			if (!parziale.contains(vicino)) {
				parziale.add(vicino);
				ricorsiva(parziale, passi);
				parziale.remove(parziale.size() - 1);
			}
		}

	}

	private int calcolaPeso(List<String> parziale) {
		int tot = 0;
		for (int i = 1; i < parziale.size(); i++) {
			DefaultWeightedEdge e = grafo.getEdge(parziale.get(i), parziale.get(i - 1));
			tot += grafo.getEdgeWeight(e);
		}
		return tot;
	}

	public List<String> getBestCammino() {
		return bestCammino;
	}

	public int getBestPeso() {
		return bestPeso;
	}
	
	
}
