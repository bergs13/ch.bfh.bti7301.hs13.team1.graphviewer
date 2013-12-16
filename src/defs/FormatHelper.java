package defs;

import logic.extlib.Decorable;

public class FormatHelper {
	public static final Object FORMAT = new Object();

	@SuppressWarnings("unchecked")
	public static <F> F getFormat(Class<F> fClass, Decorable decorable) {
		F returnValue = null;
		if (decorable.has(FORMAT)) {
			Object objectValue = decorable.get(FORMAT);
			if (null != objectValue && fClass.isInstance(objectValue)) {
				returnValue = (F) objectValue;
			}
		}
		return returnValue;
	}

	public static void updateFormat(Decorable decorable) {
		if(null == decorable)
		{
			return;
		}
		
		if (decorable.has(DecorableConstants.WEIGHT)) 
		{
			EdgeFormat f = FormatHelper.getFormat(EdgeFormat.class, decorable);
			if (null != f) {
				f.setIsWeighted(true);
				Object weight = decorable.get(DecorableConstants.WEIGHT);
				if (null != weight && Double.class.isInstance(weight)) {
					f.setLabel("" + (Double) weight);
				}
			}
		}
		else if (decorable.has(DecorableConstants.VISITED)
				|| decorable.has(DecorableConstants.MSF)) {
			if (decorable.get(FORMAT).getClass().isInstance(VertexFormat.class)) {
				VertexFormat vformat = (VertexFormat) decorable.get(FORMAT);
				vformat.setVisited();
			}
			if (decorable.get(FORMAT).getClass().isInstance(EdgeFormat.class)) {
				EdgeFormat eformat = (EdgeFormat) decorable.get(FORMAT);
				eformat.setInMSF();
			}
		} else {
			if (decorable.get(FORMAT).getClass().isInstance(VertexFormat.class)) {
				VertexFormat vformat = (VertexFormat) decorable.get(FORMAT);
				vformat.setUnvisited();
				;
			}
			if (decorable.get(FORMAT).getClass().isInstance(EdgeFormat.class)) {
				EdgeFormat eformat = (EdgeFormat) decorable.get(FORMAT);
				eformat.setNotInMSF();
			}
		}
	}
}
