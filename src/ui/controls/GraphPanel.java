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
import javax.swing.JComponent;
import javax.swing.JPanel;
import defs.EdgeFormat;
import defs.VertexFormat;
import ui.EdgePainter;
import logic.extlib.Edge;
import logic.extlib.Graph;
import logic.extlib.Vertex;
import logic.DragAndDropTransferHandler;

@SuppressWarnings("serial")
public class GraphPanel<V, E> extends JPanel {
	// Members
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
	private final Map<Vertex<V>, VertexComponent<V>> vertexComponents = new HashMap<Vertex<V>, VertexComponent<V>>();
	/**
	 * Keep a list of the the edgeformats (cache)
	 */
	private final ArrayList<EdgeFormat> edgeFormats = new ArrayList<EdgeFormat>();

	// End of Members
	// Constructors
	public GraphPanel(Graph<V, E> g) {
		if (null != g) {
			Iterator<Vertex<V>> itV = g.vertices();
			int i = 1;
			while (itV.hasNext()) {
				Vertex<V> v = itV.next();
				VertexComponent<V> vComp = new VertexComponent<V>(v);
				vComp.setCircleCenterLocation(new Point(i * 75, i * 100));
				this.vertexComponents.put(v, vComp);
				i++;
			}
			for (Vertex<V> v : vertexComponents.keySet()) {
				Point circleCenterSource = vertexComponents.get(v)
						.getCircleCenterLocation();
				Iterator<Edge<E>> itE = g.incidentEdges(v);
				while (itE.hasNext()) {
					Edge<E> e = itE.next();
					Point circleCenterTarget = vertexComponents.get(
							g.opposite(e, v)).getCircleCenterLocation();
					EdgeFormat eFormat = new EdgeFormat();
					if (circleCenterTarget.x > circleCenterSource.x) {
						eFormat.setFromPoint(circleCenterSource.x
								+ VertexFormat.getOUTERCIRCLEDIAMETER() / 2,
								circleCenterSource.y);
						eFormat.setToPoint(
								circleCenterTarget.x
										- VertexFormat.getOUTERCIRCLEDIAMETER()
										/ 2, circleCenterTarget.y);
					} else {
						eFormat.setFromPoint(circleCenterSource.x
								- VertexFormat.getOUTERCIRCLEDIAMETER() / 2,
								circleCenterSource.y);
						eFormat.setToPoint(
								circleCenterTarget.x
										+ VertexFormat.getOUTERCIRCLEDIAMETER()
										/ 2, circleCenterTarget.y);
					}
					e.set(EdgeFormat.FORMAT, eFormat);
					this.edgeFormats.add(eFormat);
				}
			}
		}

		// Again, needs to negotiate with the draggable object
		this.setTransferHandler(new DragAndDropTransferHandler());

		// Create the listener to do the work when dropping on this object!
		this.setDropTarget(new DropTarget(this,
				new GraphPanelDropTargetListener<V, E>(this)));

		this.setLayout(null);

		// Paint the components
		repaintContent();
	}

	// End of Constructors
	@Override
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		Graphics2D graphPanelGraphics = (Graphics2D) g;
		// Add the edges by format
		for (EdgeFormat eFormat : edgeFormats) {
			if (null != graphPanelGraphics) {
				EdgePainter.paintEdge(eFormat, (Graphics2D) g);
			}
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
	public void repaintContent() {
		// Clear out all previously added items
		this.removeAll();

		// Add the vertex components, if any
		for (JComponent comp : vertexComponents.values()) {
			Dimension size = comp.getPreferredSize();
			Point p = comp.getLocation();
			comp.setBounds(p.x, p.y, size.width, size.height);
			this.add(comp);
			comp.validate();
			comp.repaint();
		}
		this.validate();
		this.repaint();
	}

	private EdgeFormat getEdgeFormat(Point from, Point to) {
		Point formatFromPoint;
		Point formatToPoint;
		for (EdgeFormat f : edgeFormats) {
			formatFromPoint = f.getFromPoint();
			formatToPoint = f.getToPoint();
			if (formatFromPoint.x == from.x && formatFromPoint.y == from.y
					&& formatToPoint.x == to.x && formatToPoint.y == to.y) {
				return f;
			}
		}
		return null;
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
