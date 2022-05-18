package it.polito.tdp.borders.model;

import java.util.Objects;

public class Border {

	private Country c1;
	private Country c2;
	public Border(Country c1, Country c2) {
		super();
		this.c1 = c1;
		this.c2 = c2;
	}
	public Country getC1() {
		return c1;
	}
	public Country getC2() {
		return c2;
	}
	@Override
	public int hashCode() {
		return Objects.hash(c1, c2);
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Border other = (Border) obj;
		return Objects.equals(c1, other.c1) && Objects.equals(c2, other.c2);
	}
	
	
	
}
