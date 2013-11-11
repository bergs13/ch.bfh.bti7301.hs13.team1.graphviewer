package logic;

import java.awt.datatransfer.Transferable;
import java.awt.dnd.DragSourceDragEvent;
import java.awt.dnd.DragSourceMotionListener;
import javax.swing.JComponent;
import javax.swing.TransferHandler;

import ui.controls.VertexComponent;

/**
 * <p>
 * Used by both the draggable class and the target for negotiating data.
 * </p>
 * <p>
 * Note that this should be set for both the draggable object and the drop
 * target.
 * </p>
 * 
 * @author bergs13
 */
@SuppressWarnings("serial")
public class DragAndDropTransferHandler extends TransferHandler implements
		DragSourceMotionListener {
	public DragAndDropTransferHandler() {
		super();
	}

	/**
	 * <p>
	 * This creates the Transferable object if it the JComponent implements
	 * Transferable
	 * </p>
	 * 
	 * @param c
	 * @return
	 */
	@Override()
	public Transferable createTransferable(JComponent c) {
		// Only components who implement Transferable
		if (c instanceof Transferable) {
			Transferable tip = (Transferable) c;
			return tip;
		}
		// Not found
		return null;
	}

	public void dragMouseMoved(DragSourceDragEvent dsde) {
	}

	/**
	 * <p>
	 * This is queried to see whether the component can be copied, moved, both
	 * or neither. We are only concerned with copying.
	 * </p>
	 * 
	 * @param c
	 * @return
	 */
	@Override()
	public int getSourceActions(JComponent c) {
		if (c instanceof VertexComponent) {
			return TransferHandler.COPY;
		}
		return TransferHandler.NONE;
	}
}
