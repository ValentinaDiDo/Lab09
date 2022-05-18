package it.polito.tdp.borders.model;

import java.util.Objects;

public class Country {

	private String name;
	private int id;
	private String sigla;
	public Country(String name, int id, String sigla) {
		super();
		this.name = name;
		this.id = id;
		this.sigla = sigla;
	}
	public String getName() {
		return name;
	}
	public int getId() {
		return id;
	}
	public String getSigla() {
		return sigla;
	}
	@Override
	public int hashCode() {
		return Objects.hash(id);
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Country other = (Country) obj;
		return id == other.id;
	}
	@Override
	public String toString() {
		return name;
	}
	
	
}
