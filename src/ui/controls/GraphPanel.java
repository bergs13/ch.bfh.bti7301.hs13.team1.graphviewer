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
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;
import javax.swing.JComponent;
import javax.swing.JPanel;
import defs.EdgeFormat;
import defs.FormatHelper;
import defs.VertexFormat;
import ui.painters.EdgePainter;
import logic.extlib.Edge;
import logic.extlib.Graph;
import logic.extlib.IncidenceListGraph;
import logic.extlib.Vertex;
import logic.DragAndDropTransferHandler;
import logic.VisualizationCalculator;

@SuppressWarnings("serial")
public class GraphPanel<V, E> extends JPanel {
	// Members
	private IncidenceListGraph<V, E> graph;
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
	/**
	 * Keep a list of the vertex components (cache)
	 */
	private final Map<Vertex<V>, VertexComponent<V>> vertexVertexComponents = new HashMap<Vertex<V>, VertexComponent<V>>();
	/**
	 * Keep a list of the the edgeformats (cache)
	 */
	private final ArrayList<Edge<E>> edges = new ArrayList<Edge<E>>();

	// End of Members
	// Constructors
	public GraphPanel(IncidenceListGraph<V, E> g) {
		if (null != g) {
			this.graph = g;
			Iterator<Vertex<V>> itV = this.graph.vertices();
			int i = 1;
			while (itV.hasNext()) {
				Vertex<V> v = itV.next();
				VertexComponent<V> vComp = new VertexComponent<V>(v);
				
				vComp.setCircleCenterLocation(new Point(i * 75, i * 100));
		
				this.vertexVertexComponents.put(v, vComp);
				i++;
			}
			for (Vertex<V> v : vertexVertexComponents.keySet()) {

				Iterator<Edge<E>> itE = this.graph.incidentEdges(v);
				while (itE.hasNext()) {
					Edge<E> e = itE.next();

					reCalculateAndSetEdgeFormatPoints(this.graph, v, e);

					this.edges.add(e);
				}
			}
		} else {
			this.graph = new IncidenceListGraph<V, E>(true);
		}

		// Again, needs to negotiate with the draggable object
		this.setTransferHandler(new DragAndDropTransferHandler());

		// Create the listener to do the work when dropping on this object!
		this.setDropTarget(new DropTarget(this,
				new GraphPanelDropTargetListener<V, E>(this)));

		this.setLayout(null);

		// Paint the components
		for (JComponent comp : this.vertexVertexComponents.values()) {
			Dimension size = comp.getPreferredSize();
			Point p = comp.getLocation();
			comp.setBounds(p.x, p.y, size.width, size.height);
			this.add(comp);
			comp.validate();
			comp.repaint();
		}
		// for the edges
		this.validate();
		this.repaint();
	}

	public void handleVertexDrop(VertexComponent<V> droppedVertexComponent,
			DropTargetDropEvent dtde) {
		if (null != droppedVertexComponent && null != dtde) {
			// Get the the point of the VertexComponent
			// for the drop option (the cursor on the drop)
			droppedVertexComponent.setCircleCenterLocation(dtde.getLocation());
			repaintContent();
		}
	}

	// End of Constructors
	
	@Override
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		Graphics2D graphPanelGraphics = (Graphics2D) g;
		// Add the edges by format
		for (Edge<E> edge : this.edges) {
			if (null != graphPanelGraphics) {
				EdgePainter.paintEdge(
						FormatHelper.getFormat(EdgeFormat.class, edge),
						(Graphics2D) g);
			}
		}
	}

	public void handleVertexDrop(VertexComponent<V> droppedVertexComponent,
			DropTargetDropEvent dtde) {
		if (null != droppedVertexComponent && null != dtde) {
			// Get the the point of the VertexComponent
			// for the drop option (the cursor on the drop)
			droppedVertexComponent.setCircleCenterLocation(dtde.getLocation());
			repaintDroppedAndAdjacent(droppedVertexComponent);
		}
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
		// Remove the component
		this.remove(comp);
		// readd the component
		Dimension size = comp.getPreferredSize();
		Point p = comp.getLocation();
		comp.setBounds(p.x, p.y, size.width, size.height);
		this.add(comp);
		// repaint the component
		comp.validate();
		comp.repaint();

		// Change the format of incident edges
		for (Entry<Vertex<V>, VertexComponent<V>> mapEntry : this.vertexVertexComponents
				.entrySet()) {
			if (mapEntry.getValue().equals(comp)) {
				Vertex<V> firstVertex = mapEntry.getKey();
				Iterator<Edge<E>> itE = this.graph.incidentEdges(firstVertex);
				while (itE.hasNext()) {
					Edge<E> e = itE.next();

					reCalculateAndSetEdgeFormatPoints(this.graph, firstVertex, e);
				}
			}
		}
		// repaint the panel for applying the new edgeformat
		this.validate();
		this.repaint();
	}

	/**
	 * calculate the edge length by the source and target vertex (via graph) and
	 * set the format
	 * 
	 * @param v
	 * @param e
	 */
	private void reCalculateAndSetEdgeFormatPoints(Graph<V, E> g, Vertex<V> v,
			Edge<E> e) {

		Point circleCenterSource = vertexVertexComponents.get(v)
				.getCircleCenterLocation();
		Point circleCenterTarget = vertexVertexComponents.get(g.opposite(e, v))
				.getCircleCenterLocation();
		
		//Umpolen wenn n�tig, wenn gerichtet
		if(g.isDirected())
		{
			if(g.destination(e).equals(v))
			{
				Point temp = circleCenterSource;
				circleCenterSource = circleCenterTarget;
				circleCenterTarget = temp; 
			}
		}
		
		EdgeFormat eFormat = FormatHelper.getFormat(EdgeFormat.class, e);
		if(null == eFormat)
		{
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

			if (VertexComponent.class.isInstance(transferableObj)) {
				// refresh graphpanel (vertex gedroppt)
				this.graphPanel.handleVertexDrop(
						(VertexComponent<V>) transferableObj, dtde);
			}
		}
	}
	// End of Listeners
}
