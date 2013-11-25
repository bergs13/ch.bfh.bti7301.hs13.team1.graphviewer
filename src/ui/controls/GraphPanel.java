package ui.controls;

import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.Transferable;
import java.awt.dnd.DropTarget;
import java.awt.dnd.DropTargetDragEvent;
import java.awt.dnd.DropTargetDropEvent;
import java.awt.dnd.DropTargetEvent;
import java.awt.dnd.DropTargetListener;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Map.Entry;
import java.util.Observable;
import java.util.Observer;

import javax.jws.WebParam.Mode;
import javax.swing.JComponent;
import javax.swing.JMenuItem;
import javax.swing.JPopupMenu;
import defs.EdgeFormat;
import defs.FormatHelper;
import defs.VertexFormat;
import ui.painters.EdgePainter;
import logic.extlib.Edge;
import logic.extlib.IncidenceListGraph;
import logic.extlib.Vertex;
import logic.DragAndDropTransferHandler;
import logic.GraphPanelModel;
import logic.VisualizationCalculator;

@SuppressWarnings("serial")
public class GraphPanel<V, E> extends JComponent implements Observer {
	private GraphPanelModel<V, E> model = null;
	private final Map<Vertex<V>, VertexComponent<V>> vertexVertexComponents = new HashMap<Vertex<V>, VertexComponent<V>>();
	private JMenuItem menuItemAddVertex = new JMenuItem("Add");
	private JMenuItem menuItemUpdVertex = new JMenuItem("Update");
	private JMenuItem menuItemDelVertex = new JMenuItem("Delete");
	private JPopupMenu popupMenu = new JPopupMenu();

	// End of Members

	// Constructors
	public GraphPanel(final GraphPanelModel<V, E> model) {
		if (null == model) {
			throw new IllegalArgumentException("no model set for graph panel");
		}
		this.model = model;
		this.model.addObserver(this);

		int i = 1;
		Iterator<Vertex<V>> itV = model.getGraph().vertices();
		while (itV.hasNext()) {
			Vertex<V> vertex = itV.next();
			VertexComponent<V> vComp = new VertexComponent<V>(vertex);

			// Vertices in einem Kreis verteilen
			vComp.setCircleCenterLocation(new Point(i * 75, i * 100));

			this.vertexVertexComponents.put(vertex, vComp);
			i++;
		}

		for (Vertex<V> vertex : this.vertexVertexComponents.keySet()) {
			Iterator<Edge<E>> itE = model.getGraph().incidentEdges(vertex);
			while (itE.hasNext()) {
				reCalculateAndSetEdgeFormatPoints(vertex, itE.next());
			}
		}

		// Drag & Drop
		// Again, needs to negotiate with the draggable object
		this.setTransferHandler(new DragAndDropTransferHandler());
		// Create the listener to do the work when dropping on this object!
		this.setDropTarget(new DropTarget(this,
				new GraphPanelDropTargetListener<V, E>(this)));

		// No layout, set all the points of the controls manually
		this.setLayout(null);

		// Paint the vertices
		for (JComponent comp : this.vertexVertexComponents.values()) {
			paintVertexComponent(comp);
		}
		// .. and edges
		repaintEdges();

		// context menu and actions
		popupMenu.add(menuItemAddVertex);
		menuItemAddVertex.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				// Liste für Quell-Vertex-Auswahl
				ArrayList<Vertex<V>> arrLV = new ArrayList<Vertex<V>>();
				Iterator<Vertex<V>> itV = model.getGraph().vertices();
				while (itV.hasNext()) {
					arrLV.add(itV.next());
				}
				VertexFormatEditor<V> editor = new VertexFormatEditor<V>(arrLV, null, new VertexFormat());
				editor.setVisible(true);
				model.addVertex(editor.getSourceVertex(), editor.getFormat());
			}
		});
		popupMenu.add(menuItemUpdVertex);
		menuItemUpdVertex.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				model.updateVertex(null, new VertexFormat());
			}
		});
		popupMenu.add(menuItemDelVertex);
		menuItemDelVertex.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				model.deleteVertex(null);
			}
		});
		this.setComponentPopupMenu(popupMenu);
	}

	// End of Constructors

	// Painting methods
	// vertices
	private void addAndPaintVertexComponent(Vertex<V> vertex) {
		if (null == vertex) {
			return;
		}

		// GUI update
		VertexComponent<V> vComp = new VertexComponent<V>(vertex);
		// Find position for new vertex
		vComp.setCircleCenterLocation(new Point(1 * 75, 1 * 100));
		// add to list
		this.vertexVertexComponents.put(vertex, vComp);
		// paint
		paintVertexComponent(vComp);
		// calculate and paint the edges of the new vertex comp
		Iterator<Edge<E>> itE = model.getGraph().incidentEdges(vertex);
		while (itE.hasNext()) {
			reCalculateAndSetEdgeFormatPoints(vertex, itE.next());
		}
		// repaint the edges (applying new edgeformat)
		repaintEdges();
	}

	private void paintVertexComponent(JComponent comp) {
		Dimension size = comp.getPreferredSize();
		Point p = comp.getLocation();
		comp.setBounds(p.x, p.y, size.width, size.height);
		this.add(comp);
		comp.validate();
		comp.repaint();
	}

	private void repaintVertexComponent(VertexComponent<V> comp) {
		// Remove the component
		this.remove(comp);
		// readd the component
		paintVertexComponent(comp);
	}

	/**
	 * <p>
	 * Removes all components from the panel and re-adds them.
	 * </p>
	 * <p>
	 * This is important for reordering components (user drags and drops a
	 * vertex to acceptable drop target region)
	 * </p>
	 */
	public void repaintDroppedAndAdjacent(VertexComponent<V> comp) {
		repaintVertexComponent(comp);

		// Change the format of incident edges
		Vertex<V> vertex = this.getVertexByComponent(comp);
		// calculate new edge points after drop
		Iterator<Edge<E>> itE = model.getGraph().incidentEdges(vertex);
		while (itE.hasNext()) {
			reCalculateAndSetEdgeFormatPoints(vertex, itE.next());
		}

		// repaint the edges (applying new edgeformat)
		repaintEdges();
	}

	// Edges
	private void repaintEdges() {
		// Edges are repainted in the paintComponent of the panel
		this.validate();
		this.repaint();
	}

	@Override
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		Graphics2D graphPanelGraphics = (Graphics2D) g;
		// Add the edges by format
		Iterator<Edge<E>> itE = model.getGraph().edges();
		while (itE.hasNext()) {
			if (null != graphPanelGraphics) {
				EdgePainter.paintEdge(
						FormatHelper.getFormat(EdgeFormat.class, itE.next()),
						(Graphics2D) g);
			}
		}
	}

	// End of painting methods

	// Drag & Drop
	/**
	 * <p>
	 * This represents the data that is transmitted in drag and drop.
	 * </p>
	 * <p>
	 * In our limited case with only 1 type of dropped item, it will be a
	 * VertexComponent object!
	 * </p>
	 * <p>
	 * Note DataFlavor can represent more than classes -- easily text, images,
	 * etc.
	 * </p>
	 */
	private static DataFlavor vertexComponentDataFlavor = null;

	public void handleObjectDrop(Object transferableObj,
			DropTargetDropEvent dtde) {
		if (VertexComponent.class.isInstance(transferableObj)) {
			@SuppressWarnings("unchecked")
			VertexComponent<V> vComp = (VertexComponent<V>) transferableObj;
			if (null != vComp && null != dtde) {
				// refresh graphpanel (vertex gedroppt)
				// Get the the point of the VertexComponent
				// for the drop option (the cursor on the drop)
				vComp.setCircleCenterLocation(dtde.getLocation());
				repaintDroppedAndAdjacent(vComp);
			}
		}
	}

	/**
	 * <p>
	 * Returns (creating, if necessary) the DataFlavor representing
	 * RandomDragAndDropPanel
	 * </p>
	 * 
	 * @return
	 */
	public static DataFlavor getVertexComponentDataFlavor() throws Exception {
		// Lazy load/create the flavor
		if (vertexComponentDataFlavor == null) {
			vertexComponentDataFlavor = new DataFlavor(
					DataFlavor.javaJVMLocalObjectMimeType + ";class=\""
							+ VertexComponent.class.getName() + "\"");
		}
		return vertexComponentDataFlavor;
	}

	// Listeners
	/**
	 * <p>
	 * Listens for drops and performs the updates.
	 * </p>
	 * <p>
	 * The real magic behind the drop!
	 * </p>
	 */
	static class GraphPanelDropTargetListener<V, E> implements
			DropTargetListener {
		private final GraphPanel<V, E> graphPanel;
		/*
		 * ,* <p> Two cursors with which we are primarily interested while
		 * dragging: </p> <ul> <li>Cursor for droppable condition</li>
		 * <li>Cursor for non-droppable consition</li> </ul> <p> After drop, we
		 * manually change the cursor back to default, though does this anyhow
		 * -- just to be complete. </p>
		 */
		private static final Cursor droppableCursor = Cursor
				.getPredefinedCursor(Cursor.HAND_CURSOR),
				notDroppableCursor = Cursor
						.getPredefinedCursor(Cursor.CROSSHAIR_CURSOR);

		public GraphPanelDropTargetListener(GraphPanel<V, E> graphPanel) {
			this.graphPanel = graphPanel;
		}

		// Could easily find uses for these, like cursor changes, etc.
		public void dragEnter(DropTargetDragEvent dtde) {
		}

		public void dragOver(DropTargetDragEvent dtde) {
			if (!this.graphPanel.getCursor().equals(droppableCursor)) {
				this.graphPanel.setCursor(droppableCursor);
			}
		}

		public void dropActionChanged(DropTargetDragEvent dtde) {
		}

		public void dragExit(DropTargetEvent dte) {
			this.graphPanel.setCursor(notDroppableCursor);
		}

		/**
		 * <p>
		 * The user drops the item. Performs the drag and drop calculations and
		 * layout.
		 * </p>
		 * 
		 * @param dtde
		 */
		public void drop(DropTargetDropEvent dtde) {
			// Done with cursors, dropping
			this.graphPanel.setCursor(Cursor.getDefaultCursor());

			// Just going to grab the expected DataFlavor to make sure
			// we know what is being dropped
			DataFlavor vertexComponentDataFlavor = null;

			Object transferableObj = null;
			Transferable transferable = null;

			try {
				// Grab expected flavor
				vertexComponentDataFlavor = GraphPanel
						.getVertexComponentDataFlavor();

				transferable = dtde.getTransferable();

				// What does the Transferable support
				if (transferable
						.isDataFlavorSupported(vertexComponentDataFlavor)) {
					transferableObj = dtde.getTransferable().getTransferData(
							vertexComponentDataFlavor);
				}

			} catch (Exception ex) { /* nope, not the place */
			}

			// If didn't find an item, bail
			if (transferableObj == null) {
				return;
			}
			this.graphPanel.handleObjectDrop(transferableObj, dtde);
		}
	}

	// End of Listeners
	// End of Drag & Drop

	// Observer methods
	@Override
	public void update(Observable observable, Object objArgs) {
		// Argumente müssen bestimmte Form haben
		if (Vertex.class.isInstance(objArgs)) {
			Vertex<V> vertex = (Vertex<V>) objArgs;
			if (null == vertex) {
				// Delete
				// Remove from list and GUI
				VertexComponent<V> comp = this.vertexVertexComponents
						.remove(vertex);
				// Remove the component
				this.remove(comp);
				comp = null;
			} else if (this.vertexVertexComponents.containsKey(vertex)) {
				VertexComponent<V> vComp = this.vertexVertexComponents
						.get(vertex);
				// Update
				repaintVertexComponent(vComp);
			} else {
				// Add
				addAndPaintVertexComponent(vertex);
			}
		}
	}

	// End of observer methods

	// Helper methods

	/**
	 * calculate the edge length by the source and target vertex (via graph) and
	 * set the format
	 * 
	 * @param v
	 * @param e
	 */
	private void reCalculateAndSetEdgeFormatPoints(Vertex<V> v, Edge<E> e) {
		IncidenceListGraph<V, E> g = model.getGraph();
		Point circleCenterSource = vertexVertexComponents.get(v)
				.getCircleCenterLocation();
		Point circleCenterTarget = vertexVertexComponents.get(g.opposite(e, v))
				.getCircleCenterLocation();

		// Umpolen wenn nötig, wenn gerichtet
		if (g.isDirected()) {
			if (g.destination(e).equals(v)) {
				Point temp = circleCenterSource;
				circleCenterSource = circleCenterTarget;
				circleCenterTarget = temp;
			}
		}

		EdgeFormat eFormat = FormatHelper.getFormat(EdgeFormat.class, e);
		if (null == eFormat) {
			eFormat = new EdgeFormat();
		}
		Point eFFromPoint = VisualizationCalculator.getPointOnStraightLine(
				circleCenterSource, circleCenterTarget,
				VertexFormat.getOUTERCIRCLEDIAMETER() / 2);
		Point eFToPoint = VisualizationCalculator.getPointOnStraightLine(
				circleCenterTarget, circleCenterSource,
				VertexFormat.getOUTERCIRCLEDIAMETER() / 2);
		eFormat.setFromPoint(eFFromPoint.x, eFFromPoint.y);
		eFormat.setToPoint(eFToPoint.x, eFToPoint.y);
		e.set(FormatHelper.FORMAT, eFormat);
	}

	private Vertex<V> getVertexByComponent(VertexComponent<V> vComp) {
		Vertex<V> vertex = null;
		for (Entry<Vertex<V>, VertexComponent<V>> mapEntry : this.vertexVertexComponents
				.entrySet()) {
			if (mapEntry.getValue().equals(vComp)) {
				vertex = mapEntry.getKey();
				break;
			}
		}
		if (null == vertex) {
			throw new NoSuchElementException("no vertex for component found.");
		}
		return vertex;
	}

	// End of Helper methods
}
