package it.polito.tdp.nyc.model;

public class Vicini {
	
	String loc;
	int vicini;
	public Vicini(String loc, int vicini) {
		super();
		this.loc = loc;
		this.vicini = vicini;
	}
	public String getLoc() {
		return loc;
	}
	public void setLoc(String loc) {
		this.loc = loc;
	}
	public int getVicini() {
		return vicini;
	}
	public void setVicini(int vicini) {
		this.vicini = vicini;
	}
	
	

}
