package logic.extlib;
import java.util.HashMap;
import java.util.Observable;
import defs.FormatHelper;

public  class IGLDecorable extends Observable implements Decorable {
	private HashMap<Object,Object> attrs = new HashMap<Object,Object>(2);
	private final Object DUMMY = new Object();
	@Override
	public Object get(Object attr) {
		Object ret = attrs.get(attr);
		if (ret==null) throw new RuntimeException("no attribute "+attr);
		if (ret==DUMMY) ret=null;
		return ret;
	}

	@Override
	public boolean has(Object attr) {
		Object o = attrs.get(attr);
		return (o!=null);
	}

	@Override
	public void set(Object attr, Object val) {
		Object value = DUMMY;
		if (val != null) value = val;
		attrs.put(attr, value);
		FormatHelper.updateFormat(this);
		this.setChanged();
		this.notifyObservers(this);
	}

	@Override
	public Object destroy(Object attr) {
		Object ret = attrs.get(attr);
		attrs.remove(attr);
		FormatHelper.updateFormat(this);
		this.setChanged();
		this.notifyObservers(this);
		return ret;
	}

	@Override
	public void clearAll() {
		attrs.clear();
		//Set event decorable null if all are affected
		Decorable all = null;
		this.setChanged();
		this.notifyObservers(all);
	}
}
