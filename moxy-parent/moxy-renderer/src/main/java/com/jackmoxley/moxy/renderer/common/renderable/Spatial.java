package com.jackmoxley.moxy.renderer.common.renderable;

import com.jackmoxley.moxy.renderer.common.dimensions.Vertex;

public class Spatial {

	protected final Spatial parent;

	private Vertex size = Vertex.ZERO;
	private Vertex localTranslation = Vertex.ZERO;

	public Spatial(Spatial parent) {
		super();
		this.parent = parent;
	}

	public Vertex size() {
		return size;
	}

	public Vertex size(Vertex size) {
		final Vertex old = this.size;
		this.size = size;
		return old;
	}

	public void copyBounds(Spatial spatial) {
		this.size = spatial.size;
		this.localTranslation = spatial.localTranslation;
	}

	public Vertex worldTranslation() {
		if (parent == null) {
			return localTranslation;
		}
		return parent.worldTranslation().add(localTranslation);
	}

	public Vertex localTranslation() {
		return localTranslation;
	}

	public Vertex localTranslation(Vertex localTranslation) {
		final Vertex old = this.localTranslation;
		this.localTranslation = localTranslation;
		return old;
	}


	public void sizeFromSpatials(Iterable<? extends Spatial> spatials) {

		Vertex topLeft = new Vertex(Double.MAX_VALUE);
		Vertex bottomRight = new Vertex(Double.MIN_VALUE);
		for(Spatial spatial: spatials){
			topLeft = topLeft.min(spatial.localTranslation());
			bottomRight = bottomRight.max(spatial.localTranslation.add(spatial.size));
//			System.out.println(spatial.getClass().getSimpleName()+" boundsFromSpatials spatial:"+spatial);
		}
		this.size = bottomRight.sub(topLeft);
//		System.out.println("boundsFromSpatials offset:"+offset+" size:"+size+" topLeft:"+topLeft+" bottomRight:"+bottomRight);
	}

	
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append(getClass().getSimpleName()+" [size=").append(size)
				.append(", localTranslation=").append(localTranslation).append("]");
		return builder.toString();
	}

}
