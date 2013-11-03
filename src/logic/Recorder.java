/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package logic;

import java.util.Iterator;
import logic.extlib.Edge;
import logic.extlib.Graph;
import logic.extlib.Vertex;

/**
 *
 * @author Stephan_2
 * @param <V>
 * @param <E>
 */
public class Recorder<V, E> {

    private final Graph<V, E> graph;

    public Recorder(Graph g) {
        graph = g;
    }

    public void breakPoint() {
        Iterator<Vertex<V>> vertexIterator = graph.vertices();
        while (vertexIterator.hasNext()) {

        }
        Iterator<Edge<E>> edgeIterator = graph.edges();

        while (edgeIterator.hasNext()) {

        }

    }
}
