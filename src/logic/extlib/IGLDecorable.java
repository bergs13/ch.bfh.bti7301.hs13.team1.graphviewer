package logic.extlib;

import java.util.HashMap;
import java.util.Observable;

import defs.DecorableConstants;
import defs.FormatHelper;

public class IGLDecorable extends Observable implements Decorable {
	private HashMap<Object, Object> attrs = new HashMap<Object, Object>(2);
	private final Object DUMMY = new Object();

	@Override
	public Object get(Object attr) {
		Object ret = attrs.get(attr);
		if (ret == null)
			if (Vertex.class.isInstance(attr)) {
				return null;
			} else {
				throw new RuntimeException("no attribute " + attr);
			}
		if (ret == DUMMY)
			ret = null;
		return ret;
	}

	@Override
	public boolean has(Object attr) {
		Object o = attrs.get(attr);
		return (o != null);
	}

	@Override
	public void set(Object attr, Object val) {
		Object value = DUMMY;
		if (val != null) {
			value = val;
		}
		attrs.put(attr, value);
		
		// active clears visited and vice versa
		// the last application of visited or activ clears the other
		if (null != attr) {
			if (attr.equals(DecorableConstants.VISITED)
					&& this.has(DecorableConstants.ACTIVE)) {
				this.destroy(DecorableConstants.ACTIVE);
			}

			if (attr.equals(DecorableConstants.ACTIVE)
					&& this.has(DecorableConstants.VISITED)) {
				this.destroy(DecorableConstants.VISITED);
			}
		}
		//format updates depending on decorable
		FormatHelper.updateDecorableFormat(this);
		//inform the gui
		this.setChanged();
		this.notifyObservers(this);
	}

	@Override
	public Object destroy(Object attr) {
		Object ret = attrs.get(attr);
		attrs.remove(attr);
		FormatHelper.updateDecorableFormat(this);
		this.setChanged();
		this.notifyObservers(this);
		return ret;
	}

	@Override
	public void clearAll() {
		attrs.clear();
		// Set event decorable null if all are affected
		Decorable all = null;
		this.setChanged();
		this.notifyObservers(all);
	}
}
