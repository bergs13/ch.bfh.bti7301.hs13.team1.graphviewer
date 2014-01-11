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
	public static void updateDecorableFormat(Decorable decorable) {
		if (null == decorable) {
			return;
		}

		Object format = null;
		if (decorable.has(FORMAT)) {
			format = decorable.get(FORMAT);
			if (null != format) {
				if (decorable.has(DecorableConstants.VISITED)
						|| decorable.has(DecorableConstants.MSF)) {
					if (VertexFormat.class.isInstance(format)) {
						VertexFormat vformat = (VertexFormat) format;
						vformat.setVisited();
					}
					if (EdgeFormat.class.isInstance(format)) {
						EdgeFormat eformat = (EdgeFormat) format;
						eformat.setVisited();
					}
				} else {
					if (VertexFormat.class.isInstance(format)) {
						VertexFormat vformat = (VertexFormat) format;
						vformat.setUnvisited();
					}
					if (EdgeFormat.class.isInstance(format)) {
						EdgeFormat eformat = (EdgeFormat) format;
						eformat.setUnvisited();
					}
				}
			}
		}
	}
}
