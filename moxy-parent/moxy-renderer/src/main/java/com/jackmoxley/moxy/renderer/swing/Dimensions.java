/**
 * Copyright (C) 2013  John Orlando Keleshian Moxley
 * 
 * Unless otherwise stated by the license provided by the copyright holder.
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 * 
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package com.jackmoxley.moxy.renderer.swing;

import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;

public class Dimensions {

	public final Point2D size;
	public final Rectangle2D bounds;

	public final Point2D stubLeft;
	public final Point2D stubRight;
	
	public final double localStubY;

	public Dimensions(Point2D size, Rectangle2D bounds, Point2D stubLeft,
			Point2D stubRight, double localStubY) {
		super();
		this.size = size;
		this.bounds = bounds;
		this.stubLeft = stubLeft;
		this.stubRight = stubRight;
		this.localStubY = localStubY;
	}

}
