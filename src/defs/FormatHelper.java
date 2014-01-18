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
				// Active
				if (decorable.has(DecorableConstants.ACTIVE)) {
					if (VertexFormat.class.isInstance(format)) {
						VertexFormat vformat = (VertexFormat) format;
						vformat.setActive();
					}
					if (EdgeFormat.class.isInstance(format)) {
						EdgeFormat eformat = (EdgeFormat) format;
						eformat.setActive();
					}
				} else {
					if (VertexFormat.class.isInstance(format)) {
						VertexFormat vformat = (VertexFormat) format;
						vformat.setInactive();
					}
					if (EdgeFormat.class.isInstance(format)) {
						EdgeFormat eformat = (EdgeFormat) format;
						eformat.setInactive();
					}
				}
				// Distance
				if (decorable.has(DecorableConstants.DISTANCE)) {
					if (VertexFormat.class.isInstance(format)) {
						VertexFormat vformat = (VertexFormat) format;
						vformat.setDistance(decorable
								.get(DecorableConstants.DISTANCE));
					}
				} else {
					if (VertexFormat.class.isInstance(format)) {
						VertexFormat vformat = (VertexFormat) format;
						vformat.setDistance(null);
					}
				}
				// Visited
				if (decorable.has(DecorableConstants.VISITED)) {
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
