package ui;

import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Point;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.Transferable;
import java.awt.dnd.DropTarget;
import java.awt.dnd.DropTargetDragEvent;
import java.awt.dnd.DropTargetDropEvent;
import java.awt.dnd.DropTargetEvent;
import java.awt.dnd.DropTargetListener;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JPanel;

import logic.DragAndDropTransferHandler;


@SuppressWarnings("serial")
public class GraphPanel extends JPanel {
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
	 * Keep a list of the user-added panels so can re-add
	 */
	private final List<VertexComponent> circles = new ArrayList<VertexComponent>();

	// End of Members
	// Constructors
	public GraphPanel() {
		// Add circles with a center for start
		for (int i = 1; i < 3; i++) {
			VertexComponent comp = new VertexComponent();
			comp.setCircleCenterLocation(new Point(i * 100, i * 100));
			this.circles.add(comp);
		}

		// Again, needs to negotiate with the draggable object
		this.setTransferHandler(new DragAndDropTransferHandler());

		// Create the listener to do the work when dropping on this object!
		this.setDropTarget(new DropTarget(this,
				new GraphPanelDropTargetListener(this)));

		this.setLayout(null);

		// Paint the circles
		repaintContent();
	}

	// End of Constructors
	/**
	 * <p>
	 * Removes all circles from the panel and re-adds them.
	 * </p>
	 * <p>
	 * This is important for reordering circles (user drags and drops a panel to
	 * acceptable drop target region)
	 * </p>
	 */
	public void repaintContent() {
		// Clear out all previously added items
		this.removeAll();

		// Add the panels, if any
		for (VertexComponent c : this.circles) {
			Dimension size = c.getPreferredSize();
			Point p = c.getLocation();
			c.setBounds(p.x, p.y, size.width, size.height);
			this.add(c);
			c.validate();
			c.repaint();
		}
		this.validate();
		this.repaint();
	}

	/**
	 * <p>
	 * Returns (creating, if necessary) the DataFlavor representing
	 * RandomDragAndDropPanel
	 * </p>
	 * 
	 * @return
	 */
	public static DataFlavor getVertexComponentDataFlavor()
			throws Exception {
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
	static class GraphPanelDropTargetListener implements
			DropTargetListener {
		private final GraphPanel graphPanel;
		/**
		 * <p>
		 * Two cursors with which we are primarily interested while dragging:
		 * </p>
		 * <ul>
		 * <li>Cursor for droppable condition</li>
		 * <li>Cursor for non-droppable consition</li>
		 * </ul>
		 * <p>
		 * After drop, we manually change the cursor back to default, though
		 * does this anyhow -- just to be complete.
		 * </p>
		 */
		private static final Cursor droppableCursor = Cursor
				.getPredefinedCursor(Cursor.HAND_CURSOR),
				notDroppableCursor = Cursor
						.getPredefinedCursor(Cursor.CROSSHAIR_CURSOR);

		public GraphPanelDropTargetListener(
				GraphPanel graphPanel) {
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

			// Cast it to the VertexComponent. By this point, we have
			// verified it is
			// a VertexComponent.
			VertexComponent droppedVertexComponent = (VertexComponent) transferableObj;

			// Get the the point of the VertexComponent
			// for the drop option (the cursor on the drop)
			droppedVertexComponent.setCircleCenterLocation(dtde
					.getLocation());

			// Request repaint of contents, or else won't update GUI following
			// drop.
			// Will add back in the order to which we just sorted
			this.graphPanel.repaintContent();
		}
	}
	// End of Listeners
}
