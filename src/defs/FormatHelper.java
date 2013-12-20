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
		if (null == decorable) {
			return;
		}

		Object format = null;
		if (decorable.has(FORMAT)) {
			format = decorable.get(FORMAT);
		}
		if (decorable.has(DecorableConstants.WEIGHT)) {
			if (null != format) {
				if (format.getClass().isInstance(EdgeFormat.class)) {
					EdgeFormat f = (EdgeFormat) format;
					if (null != f) {
						f.setIsWeighted(true);
						Object weight = decorable
								.get(DecorableConstants.WEIGHT);
						if (null != weight && Double.class.isInstance(weight)) {
							f.setLabel("" + (Double) weight);
						}
					}
				}
			}
		}
		if (decorable.has(DecorableConstants.VISITED)
				|| decorable.has(DecorableConstants.MSF)) {
			if (null != format) {
				if (format.getClass().isInstance(VertexFormat.class)) {
					VertexFormat vformat = (VertexFormat) format;
					vformat.setVisited();
				}
				if (format.getClass().isInstance(EdgeFormat.class)) {
					EdgeFormat eformat = (EdgeFormat) format;
					eformat.setInMSF();
				}
			}
		} else {
			if (null != format) {
				if (format.getClass().isInstance(VertexFormat.class)) {
					VertexFormat vformat = (VertexFormat) format;
					vformat.setUnvisited();
				}
				if (format.getClass().isInstance(EdgeFormat.class)) {
					EdgeFormat eformat = (EdgeFormat) format;
					eformat.setNotInMSF();
				}
			}
		}
	}
}
