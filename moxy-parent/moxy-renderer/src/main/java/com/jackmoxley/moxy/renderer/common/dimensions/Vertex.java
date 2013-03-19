package com.jackmoxley.moxy.renderer.common.dimensions;

import java.awt.geom.Point2D;

public class Vertex{

	public final double x;
	public final double y;

	public static final Vertex ZERO = new Vertex(0, 0);

	public Vertex(double v) {
		super();
		this.x = v;
		this.y = v;
	}
	public Vertex(double x, double y) {
		super();
		this.x = x;
		this.y = y;
	}

	public static Vertex v(double v) {
		return new Vertex(v);
	}

	public static Vertex v(double x, double y) {
		return new Vertex(x, y);
	}
	
	public Point2D asPoint(){
		return new Point2D.Double(x, y);
	}
	
	public Vertex add(double x, double y) {
		return new Vertex(this.x + x, this.y + y);
	}

	public Vertex sub(double x, double y) {
		return new Vertex(this.x - x, this.y - y);
	}

	public Vertex div(double x, double y) {
		return new Vertex(this.x / x, this.y / y);
	}

	public Vertex mul(double x, double y) {
		return new Vertex(this.x * x, this.y * y);
	}

	public Vertex add(Vertex p) {
		return add(p.x, p.y);
	}

	public Vertex sub(Vertex p) {
		return sub(p.x, p.y);
	}

	public Vertex div(Vertex p) {
		return div(p.x, p.y);
	}

	public Vertex mul(Vertex p) {
		return mul(p.x, p.y);
	}

	public Vertex add(double p) {
		return add(p, p);
	}

	public Vertex sub(double p) {
		return sub(p, p);
	}

	public Vertex div(double p) {
		return div(p, p);
	}

	public Vertex mul(double p) {
		return mul(p, p);
	}

	public Vertex addX(double p) {
		return add(p, 0);
	}

	public Vertex subX(double p) {
		return sub(p, 0);
	}

	public Vertex divX(double p) {
		return div(p, 1);
	}

	public Vertex mulX(double p) {
		return mul(p, 1);
	}

	public Vertex addY(double p) {
		return add(0, p);
	}

	public Vertex subY(double p) {
		return sub(0, p);
	}

	public Vertex divY(double p) {
		return div(1, p);
	}

	public Vertex mulY(double p) {
		return mul(1, p);
	}

	public Vertex setX(double x) {
		return v(x, this.y);
	}
	
	public Vertex setY(double y) {
		return v(this.x, y);
	}

	public double mul(){
		return this.x*this.y;
	}
	
	public double add(){
		return this.x+this.y;
	}
	

	public double distance(){
		return Math.sqrt((this.x*this.x)+(this.y*this.y));
	}
	
	public Vertex changeLength(double change){
		final double distance = distance();
		final double newDistance = distance + change;
		return mul(newDistance/distance);
	}
	

	public Vertex flip() {
		return new Vertex(y,x);
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("Vertex [x=").append(x).append(", y=").append(y)
				.append("]");
		return builder.toString();
	}
	public Vertex max(Vertex v) {
		return new Vertex(Math.max(this.x, v.x),Math.max(this.y,v.y));
	}
	public Vertex min(Vertex v) {
		return new Vertex(Math.min(this.x, v.x),Math.min(this.y,v.y));
	}

	public static Vertex max(Vertex v1, Vertex v2) {
		return v1.max(v2);
	}
	public static Vertex min(Vertex v1, Vertex v2) {
		return v1.min(v2);
	}

}
