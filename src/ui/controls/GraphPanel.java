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
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Map.Entry;
import java.util.Observable;
import java.util.Observer;
import javax.swing.JComponent;
import javax.swing.JMenuItem;
import javax.swing.JPopupMenu;
import defs.EdgeFormat;
import defs.FormatHelper;
import defs.GraphFormat;
import defs.ModelEventConstants;
import defs.VertexFormat;
import ui.painters.EdgePainter;
import logic.extlib.Decorable;
import logic.extlib.Edge;
import logic.extlib.IncidenceListGraph;
import logic.extlib.Vertex;
import logic.DragAndDropTransferHandler;
import logic.GraphPanelModel;
import logic.VertexComponentModel;
import logic.VisualizationCalculator;

@SuppressWarnings("serial")
public class GraphPanel<V, E> extends JComponent implements Observer {
	// Members
	private GraphPanelModel<V, E> model = null;
	private final Map<Vertex<V>, VertexComponent<V>> vertexVertexComponents = new HashMap<Vertex<V>, VertexComponent<V>>();
	private JMenuItem menuItemAddVertex = new JMenuItem("Add");
	private JMenuItem menuItemConnectVertices = new JMenuItem(
			"Connect vertices");
	private JMenuItem menuItemUpdGraphFormat = new JMenuItem(
			"Change graph format");
	private JMenuItem menuItemEditEdge = new JMenuItem("Edit edge");
	private JPopupMenu popupMenu = new JPopupMenu();

	// End of Members

	// Constructors
	public GraphPanel(final GraphPanelModel<V, E> model) {
		if (null == model) {
			throw new IllegalArgumentException("no model set for graph panel");
		}

		this.model = model;
		// gui is observer of model
		this.model.addObserver(this);

		// Helper variables
		Iterator<Vertex<V>> itV = null;
		Iterator<Edge<E>> itE = null;

		// initial layouting
		// count vertices/circles for alignment (n)
		int n = 0;
		itV = model.getGraph().vertices();
		while (itV.hasNext()) {
			itV.next();
			n++;
		}
		// calculate circular points
		Point[] centerPoints = VisualizationCalculator
				.getCircularAlignedPoints(n, GraphFormat.OUTERCIRCLEDIAMETER,
						GraphFormat.ARROWTRIANGLEHEIGHT * 2);
		// add the vertices as components
		int centerPointIndex = 0;
		itV = model.getGraph().vertices();
		while (itV.hasNext()) {
			addVertexComponent(itV.next(), centerPoints[centerPointIndex]);
			centerPointIndex++;
		}
		// calculate the edges for the components
		for (Vertex<V> vertex : this.vertexVertexComponents.keySet()) {
			itE = model.getGraph().incidentEdges(vertex);
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

		// clear selection when panel is selected
		this.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent e) {
				if (null != model.getSelectedVertex()) {
					// clears the selection in the model (Vertex)
					model.setSelectedVertex(null);
				}
			}
		});

		// Paint content
		repaintContent();

		// context menu and menu items
		this.popupMenu.add(this.menuItemAddVertex);
		this.menuItemAddVertex.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				VertexAddDialog<V> vAddDialog = new VertexAddDialog<V>(model
						.getGraph().vertices(), model.getGraphFormat()
						.isWeighted());
				vAddDialog.setVisible(true);
				if (vAddDialog.getSaved()) {
					VertexFormat f = new VertexFormat();
					f.setLabel(vAddDialog.getLabel());
					model.addVertex(vAddDialog.getSourceVertex(), f,
							vAddDialog.getWeight());
				}
			}
		});
		this.popupMenu.add(this.menuItemConnectVertices);
		this.menuItemConnectVertices.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				VertexConnectDialog<V, E> vConnectDialog = new VertexConnectDialog<V, E>(
						model.getGraph(), model.getGraphFormat().isWeighted(),
						true);
				vConnectDialog.setVisible(true);
				if (vConnectDialog.getSaved()) {
					model.connectVertices(vConnectDialog.getSourceVertex(),
							vConnectDialog.getTargetVertex(),
							vConnectDialog.getWeight());
					// System.out.println(vConnectDialog.getWeight());
				}
			}
		});
		this.popupMenu.add(this.menuItemUpdGraphFormat);
		this.menuItemUpdGraphFormat.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				GraphFormat format = FormatHelper.getFormat(GraphFormat.class,
						model.getGraph());
				if (null == format) {
					format = new GraphFormat();
				}
				model.getGraph().set(FormatHelper.FORMAT, format);
				GraphFormatDialog gFormatDialog = new GraphFormatDialog(format,
						false);
				gFormatDialog.setVisible(true);
				if (gFormatDialog.getSaved()) {
					model.updateFormat(gFormatDialog.getFormat());
				}
			}
		});
		this.popupMenu.add(this.menuItemEditEdge);
		this.menuItemEditEdge.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				EdgeEditDialog<V, E> edgeEditDialog = new EdgeEditDialog<V, E>(
						model.getGraph(), model.getGraphFormat().isWeighted());
				edgeEditDialog.setVisible(true);
				if (edgeEditDialog.getSaved()) {
					model.changeEdge(edgeEditDialog.getSourceVertex(),
							edgeEditDialog.getTargetVertex(),
							edgeEditDialog.getWeight(),
							edgeEditDialog.getClearEdge());
				}
			}
		});
		this.setComponentPopupMenu(this.popupMenu);

		addObserverToDecorables();
	}

	// End of Constructors

	// Painting methods
	private void repaintContent() {
		if (!model.isGUIRefreshDisabled()) {
			this.repaint();
		}
	}

	@Override
	public void paintComponent(Graphics g) {
		// clear all
		this.removeAll();

		// paint panel
		super.paintComponent(g);

		// paint own stuff
		// paint vertex components
		Dimension size;
		Point p;
		for (VertexComponent<V> comp : this.vertexVertexComponents.values()) {
			size = comp.getPreferredSize();
			p = comp.getCircleCenterLocation();
			comp.setBounds(p.x - GraphFormat.LOCATIONCENTERMODIFIER, p.y
					- GraphFormat.LOCATIONCENTERMODIFIER, size.width,
					size.height);
			comp.getVertexComponentModel().updateGraphFormat(
					model.getGraphFormat());
			this.add(comp);
		}
		// paint edges
		Graphics2D graphPanelGraphics = (Graphics2D) g;
		Iterator<Edge<E>> itE = model.getGraph().edges();
		while (itE.hasNext()) {
			if (null != graphPanelGraphics) {
				EdgePainter.paintEdge(model.getGraphFormat(), itE.next(),
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
	private VertexComponent<V> droppedVertexComponent = null;
	Point dropPoint = new Point();

	public void handleObjectDrop(Object transferableObj,
			DropTargetDropEvent dtde) {
		if (VertexComponent.class.isInstance(transferableObj)) {
			droppedVertexComponent = (VertexComponent<V>) transferableObj;
			if (null != droppedVertexComponent && null != dtde) {
				// refresh graphpanel (vertex gedroppt)
				// Get the the point of the VertexComponent
				// for the drop option (the cursor on the drop)
				dropPoint.setLocation(dtde.getLocation().x,
						dtde.getLocation().y);
				droppedVertexComponent.setCircleCenterLocation(dropPoint);
				// Change the format of incident edges
				Vertex<V> vertex = this
						.getVertexByComponent(droppedVertexComponent);
				// calculate new edge points after drop
				Iterator<Edge<E>> itE = model.getGraph().incidentEdges(vertex);
				while (itE.hasNext()) {
					reCalculateAndSetEdgeFormatPoints(vertex, itE.next());
				}
				repaintContent();
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
		DataFlavor vertexComponentDataFlavor = null;
		Object transferableObject = null;
		Transferable transferable = null;
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
			this.vertexComponentDataFlavor = null;
			this.transferableObject = null;
			this.transferable = null;
			try {
				// Grab expected flavor
				vertexComponentDataFlavor = GraphPanel
						.getVertexComponentDataFlavor();

				this.transferable = dtde.getTransferable();

				// What does the Transferable support
				if (this.transferable
						.isDataFlavorSupported(vertexComponentDataFlavor)) {
					this.transferableObject = dtde.getTransferable()
							.getTransferData(vertexComponentDataFlavor);
				}

			} catch (Exception ex) { /* nope, not the place */
			}

			// If didn't find an item, bail
			if (this.transferableObject == null) {
				return;
			}
			this.graphPanel.handleObjectDrop(this.transferableObject, dtde);
		}
	}

	// End of Listeners
	// End of Drag & Drop

	// Observer methods
	@Override
	public void update(Observable observable, Object objArgs) {
		// Argumente m�ssen bestimmte Form haben
		if (Observable.class.isInstance(objArgs)) {
			repaintContent();
		} else if (String.class.isInstance(objArgs)) {
			String eventConstant = (String) objArgs;
			if (eventConstant.equals(ModelEventConstants.VERTEXADDED)) {
				Vertex<V> changedV = this.model.getChangedVertex();
				if (null != changedV) {
					if (!this.vertexVertexComponents.containsKey(changedV)) {
						VertexFormat f = FormatHelper.getFormat(
								VertexFormat.class, changedV);
						if (null == f) {
							f = new VertexFormat();
						}
						addVertexComponent(changedV, f.getCenterPoint());
						// calculate the edges of the new vertex comp
						Iterator<Edge<E>> itE = model.getGraph().incidentEdges(
								changedV);
						while (itE.hasNext()) {
							reCalculateAndSetEdgeFormatPoints(changedV,
									itE.next());
						}
						repaintContent();
					}
				}
			} else if (eventConstant.equals(ModelEventConstants.EDGECHANGED)) {
				// changed V is the source vertex of the edge, for a bit more
				// performance (incident and not all edges)
				Vertex<V> changedV = this.model.getChangedVertex();
				if (null != changedV) {
					// calculate the edges of the source verte
					Iterator<Edge<E>> itE = model.getGraph().incidentEdges(
							changedV);
					while (itE.hasNext()) {
						reCalculateAndSetEdgeFormatPoints(changedV, itE.next());
					}
					repaintContent();
				}
			} else if (eventConstant
					.equals(ModelEventConstants.VERTEXCONNECTED)) {
				if (null != model.getChangedVertex()) {
					Iterator<Edge<E>> itE = model.getGraph().incidentEdges(
							model.getChangedVertex());
					while (itE.hasNext()) {
						reCalculateAndSetEdgeFormatPoints(
								model.getChangedVertex(), itE.next());
					}
					repaintContent();
				}
			} else if (eventConstant.equals(ModelEventConstants.VERTEXDELETED)) {
				Vertex<V> changedV = this.model.getChangedVertex();
				if (null != changedV
						&& this.vertexVertexComponents.containsKey(changedV)) {
					// Delete
					// Remove from list
					VertexComponent<V> comp = this.vertexVertexComponents
							.remove(changedV);
					changedV = null;
					// Remove from gui
					this.remove(comp);
					comp = null;
					repaintContent();
				}
			} else if (eventConstant
					.equals(ModelEventConstants.VERTEXSELECTION)) {
				// clear old selection
				clearVertexComponentSelection();
				if (null != this.model.getSelectedVertex()) {
					// set new selection if available
					if (vertexVertexComponents.containsKey(this.model
							.getSelectedVertex())) {
						selectVertexComponent(vertexVertexComponents
								.get(this.model.getSelectedVertex()));
					}
				}
			} else if (eventConstant
					.equals(ModelEventConstants.ADDVERTEXTOSELECTED)) {
				if (null != this.model.getSelectedVertex()) {
					VertexAddDialog<V> vAddDialog = new VertexAddDialog<V>(
							model.getGraph().vertices(), model.getGraphFormat()
									.isWeighted(), false);
					vAddDialog.setVisible(true);
					if (vAddDialog.getSaved()) {
						VertexFormat f = new VertexFormat();
						f.setLabel(vAddDialog.getLabel());
						model.addVertex(this.model.getSelectedVertex(), f,
								vAddDialog.getWeight());
						// System.out.print(vAddDialog.getWeight());
					}
				}
			} else if (eventConstant
					.equals(ModelEventConstants.CONNECTVERTEXTOSELECTED)) {
				if (null != this.model.getSelectedVertex()) {
					VertexConnectDialog<V, E> vConnectDialog = new VertexConnectDialog<V, E>(
							model.getGraph(), model.getGraphFormat()
									.isWeighted());
					vConnectDialog.setVisible(true);
					if (vConnectDialog.getSaved()) {
						model.connectVertices(this.model.getSelectedVertex(),
								vConnectDialog.getTargetVertex(),
								vConnectDialog.getWeight());
					}
				}
			} else if (eventConstant
					.equals(ModelEventConstants.DELETESELECTEDVERTEX)) {
				if (null != this.model.getSelectedVertex()) {
					this.model.deleteSelectedVertex();
				}
			} else if (eventConstant.equals(ModelEventConstants.GRAPHREPLACED)) {
				// clear old stuff
				this.removeAll();
				this.vertexVertexComponents.clear();
				this.model.deleteObservers();

				// add new stuff
				// observer for model
				this.model.addObserver(this);
				// vertices as components
				Iterator<Vertex<V>> itV = model.getGraph().vertices();
				Vertex<V> v;
				VertexFormat f;
				while (itV.hasNext()) {
					v = itV.next();
					f = FormatHelper.getFormat(VertexFormat.class, v);
					if (f == null) {
						f = new VertexFormat();
					}
					addVertexComponent(v, f.getCenterPoint());
				}
				// calculate the edges for the components
				Iterator<Edge<E>> itE;
				for (Vertex<V> vertex : this.vertexVertexComponents.keySet()) {
					itE = model.getGraph().incidentEdges(vertex);
					while (itE.hasNext()) {
						reCalculateAndSetEdgeFormatPoints(vertex, itE.next());
					}
				}
				// oberver for decorables
				addObserverToDecorables();

				// repaint
				repaintContent();
			}
		}
	}

	// End of observer methods

	// Helper methods
	private VertexComponent<V> addVertexComponent(final Vertex<V> v,
			Point circleCenterLocation) {
		VertexComponentModel<V> vModel = new VertexComponentModel<>(v,
				model.getGraphFormat());
		vModel.addObserver(this);
		final VertexComponent<V> vComp = new VertexComponent<V>(vModel);
		vComp.setCircleCenterLocation(circleCenterLocation);
		// component selection
		vComp.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent e) {
				if (model.getSelectedVertex() == null
						|| !model.getSelectedVertex().equals(v)) {
					// overwrites the selection in the model (Vertex)
					model.setSelectedVertex(v);
				}
				if (e.isMetaDown() && !e.isPopupTrigger()) {
					vComp.getComponentPopupMenu().show(vComp, e.getX(),
							e.getY());
				}
			}
		});
		this.vertexVertexComponents.put(v, vComp);
		return vComp;
	}

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

		// Umpolen wenn n�tig, wenn gerichtet
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
				GraphFormat.OUTERCIRCLEDIAMETER / 2);
		Point eFToPoint = VisualizationCalculator.getPointOnStraightLine(
				circleCenterTarget, circleCenterSource,
				GraphFormat.OUTERCIRCLEDIAMETER / 2);
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

	private void selectVertexComponent(VertexComponent<V> vComp) {
		VertexComponentModel<V> model = vComp.getVertexComponentModel();
		model.setSelected(true);
		repaintContent();
	}

	private void clearVertexComponentSelection() {
		for (VertexComponent<V> vComp : vertexVertexComponents.values()) {
			VertexComponentModel<V> model = vComp.getVertexComponentModel();
			if (model.isSelected()) {
				model.setSelected(false);
			}
		}
		repaintContent();
	}

	private void addObserverToDecorables() {
		// Add all decorables handled by this observer (graph/vertices/edges)
		// graph
		this.model.getGraph().addObserver(this);
		// vertices
		Iterator<Vertex<V>> itV = model.getGraph().vertices();
		while (itV.hasNext()) {
			((Observable) (Decorable) itV.next()).addObserver(this);
		}
		// edges
		Iterator<Edge<E>> itE = model.getGraph().edges();
		while (itE.hasNext()) {
			((Observable) (Decorable) itE.next()).addObserver(this);
		}
	}
	// End of Helper methods
}
