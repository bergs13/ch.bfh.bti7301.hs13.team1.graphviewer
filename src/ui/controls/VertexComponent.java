package ui.controls;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.Transferable;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.geom.Ellipse2D;

import javax.swing.BorderFactory;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.TransferHandler;
import logic.extlib.Vertex;
import logic.DragAndDropTransferHandler;
import defs.FormatHelper;
import defs.GraphFormat;
import defs.VertexFormat;

@SuppressWarnings("serial")
public class VertexComponent<V> extends JComponent implements Transferable {
	// Members
	private Vertex<V> vertex = null;
	private GraphFormat graphFormat = null;
	boolean isSelected = false;

	// End of members

	// Constructors
	public VertexComponent(Vertex<V> vertex, GraphFormat graphFormat) {
		// set members
		this.vertex = vertex;
		this.graphFormat = graphFormat;

		// set vertex specific format
		VertexFormat format = FormatHelper.getFormat(VertexFormat.class,
				this.vertex);
		if (null == format) {
			// Default-Format
			format = new VertexFormat();
		}
		// default label if Vertex<String>
		if ((null == format.getLabel() || format.getLabel().equals(""))
				&& String.class.isInstance(this.vertex.element())) {
			format.setLabel((String) this.vertex.element());
		}
		this.vertex.set(FormatHelper.FORMAT, format);

		// set component size
		this.setPreferredSize(new Dimension(GraphFormat.OUTERCIRCLEDIAMETER,
				GraphFormat.OUTERCIRCLEDIAMETER));
	}

	// End of constructors

	// PaintComponent method
	@Override
	public void paintComponent(Graphics g) {
		Graphics2D g2 = (Graphics2D) g; // Cast g to Graphics2D

		// Definierbare Sachen aus Vertex-Format (Muss vorhanden sein!)
		VertexFormat format = FormatHelper.getFormat(VertexFormat.class,
				this.vertex);
		// // Definierbare Sachen aus Vertex-Format (�berschreiben wenn
		// // Vertex-Format implementiert)
		// Color inactiveColor = new Color(0, 0, 255);
		// Color activeColor = new Color(255, 0, 0);
		// boolean active = true;
		// boolean textVisible = true;
		// String displayText = "V";

		// Vertex mit innerem und �usserem Kreis
		Ellipse2D outer = new Ellipse2D.Double(0, 0,
				GraphFormat.OUTERCIRCLEDIAMETER,
				GraphFormat.OUTERCIRCLEDIAMETER);
		g2.setColor(this.graphFormat.getVertexColor(format));
		g2.fill(outer);
		Ellipse2D inner = new Ellipse2D.Double(outer.getCenterX()
				- GraphFormat.INNERCIRCLEDIAMETER / 2, outer.getCenterY()
				- GraphFormat.INNERCIRCLEDIAMETER / 2,
				GraphFormat.INNERCIRCLEDIAMETER,
				GraphFormat.INNERCIRCLEDIAMETER);
		g2.setColor(this.graphFormat.getUnvisitedColor());
		g2.fill(inner);

		// Label Vertex
		g2.setColor(this.graphFormat.getVisitedColor());
		JLabel label = new JLabel(format.getLabel());
		// Verschieben zu Punkt
		label.setLocation(this.getCircleCenterLocation());

		// Selektionsrahmen
		if (this.isSelected) {
			this.setBorder(BorderFactory.createDashedBorder(this.graphFormat
					.getUnvisitedColor()));
		} else {
			this.setBorder(null);
		}

		// Drag & Drop
		// Add the listener which will export this VertexComponent for
		// dragging
		this.addMouseListener(new DraggableMouseListener());
		// Add the handler, which negotiates between drop target and this
		// draggable VertexComponent
		this.setTransferHandler(new DragAndDropTransferHandler());
		// End of Drag & Drop
	}

	// End of paintComponent method

	// Drag & Drop
	// Transferable
	/**
	 * <p>
	 * One of three methods defined by the Transferable interface.
	 * </p>
	 * <p>
	 * If multiple DataFlavor's are supported, can choose what Object to return.
	 * </p>
	 * <p>
	 * In this case, we only support one: the actual VertexComponent.
	 * </p>
	 * <p>
	 * Note we could easily support more than one. For example, if supports text
	 * and drops to a JTextField, could return the label's text or any arbitrary
	 * text.
	 * </p>
	 * 
	 * @param flavor
	 * @return
	 */
	public Object getTransferData(DataFlavor flavor) {
		DataFlavor thisFlavor = this.getTransferDataFlavors()[0];
		// For now, assume wants this class... see loadDnD
		if (thisFlavor != null && flavor.equals(thisFlavor)) {
			return VertexComponent.this;
		}
		return null;
	}

	/**
	 * <p>
	 * One of three methods defined by the Transferable interface.
	 * </p>
	 * <p>
	 * Returns supported DataFlavor. Again, we're only supporting this actual
	 * Object within the JVM.
	 * </p>
	 * <p>
	 * For more information, see the JavaDoc for DataFlavor.
	 * </p>
	 * 
	 * @return
	 */
	public DataFlavor[] getTransferDataFlavors() {
		DataFlavor[] flavors = { null };
		try {
			flavors[0] = VertexComponent
					.getDragAndDropVertexComponentDataFlavor();
		} catch (Exception ex) {
			System.err.println("Problem lazy loading: " + ex.getMessage());
			ex.printStackTrace(System.err);
			return null;
		}
		return flavors;
	}

	/**
	 * <p>
	 * One of three methods defined by the Transferable interface.
	 * </p>
	 * <p>
	 * Determines whether this object supports the DataFlavor. In this case,
	 * only one is supported: for this object itself.
	 * </p>
	 * 
	 * @param flavor
	 * @return True if DataFlavor is supported, otherwise false.
	 */
	public boolean isDataFlavorSupported(DataFlavor flavor) {
		DataFlavor[] thisFlavors = getTransferDataFlavors();
		for (DataFlavor tF : thisFlavors) {
			if (null != tF && tF.equals(flavor)) {
				return true;
			}
		}
		return false;
	}

	// End of Transferable
	// Own Stuff
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
	private static DataFlavor dragAndDropVertexComponentDataFlavor = null;

	// End of Members
	// Listeners
	/**
	 * <p>
	 * Listener that make source draggable.
	 * </p>
	 */
	class DraggableMouseListener extends MouseAdapter {
		@Override()
		public void mousePressed(MouseEvent e) {
			JComponent c = (JComponent) e.getSource();
			TransferHandler handler = c.getTransferHandler();
			handler.exportAsDrag(c, e, TransferHandler.COPY);
		}
	}

	// End of Listeners
	// End of Drag & Drop

	// Other Methods
	/**
	 * <p>
	 * Returns (creating, if necessary) the DataFlavor representing Vertex
	 * </p>
	 * 
	 * @return
	 */
	public static DataFlavor getDragAndDropVertexComponentDataFlavor()
			throws Exception {
		// Lazy load/create the flavor
		if (dragAndDropVertexComponentDataFlavor == null) {
			dragAndDropVertexComponentDataFlavor = new DataFlavor(
					DataFlavor.javaJVMLocalObjectMimeType + ";class=\""
							+ VertexComponent.class.getName() + "\"");
		}
		return dragAndDropVertexComponentDataFlavor;
	}

	public void setCircleCenterLocation(Point p) {
		// Standard setLocation - Constant value for the circleCenter
		this.setLocation(new Point(p.x - GraphFormat.LOCATIONCENTERMODIFIER,
				p.y - GraphFormat.LOCATIONCENTERMODIFIER));
	}

	public Point getCircleCenterLocation() {
		// Standard getLocation + Constant value for the circleCenter
		Point p = this.getLocation();
		return new Point(p.x + GraphFormat.LOCATIONCENTERMODIFIER, p.y
				+ GraphFormat.LOCATIONCENTERMODIFIER);
	}

	public void setGraphFormat(GraphFormat graphFormat) {
		this.graphFormat = graphFormat;
	}

	public boolean isSelected() {
		return isSelected;
	}

	public void setSelected(boolean isSelected) {
		this.isSelected = isSelected;
	}
	// End of other Methods
}
