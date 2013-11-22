package ui.controls;

import javax.swing.JPanel;

import defs.VertexFormat;
import logic.extlib.Vertex;

@SuppressWarnings("serial")
public class VertexFormatEditor<V> extends JPanel {
	Vertex<V> vertex = null;
	VertexFormat format = null;

	public VertexFormatEditor(Vertex<V> vertex, VertexFormat format) {
		this.vertex = vertex;
		this.format = format;
	}
}
