package it.polito.tdp.nyc.model;

import com.javadocmd.simplelatlng.LatLng;

public class Arco {
	
	private String l1;
	private LatLng pos1;
	private String l2;
	private LatLng pos2;
	
	public Arco(String l1, LatLng pos1, String l2, LatLng pos2) {
		super();
		this.l1 = l1;
		this.pos1 = pos1;
		this.l2 = l2;
		this.pos2 = pos2;
	}

	public String getL1() {
		return l1;
	}

	public void setL1(String l1) {
		this.l1 = l1;
	}

	public LatLng getPos1() {
		return pos1;
	}

	public void setPos1(LatLng pos1) {
		this.pos1 = pos1;
	}

	public String getL2() {
		return l2;
	}

	public void setL2(String l2) {
		this.l2 = l2;
	}

	public LatLng getPos2() {
		return pos2;
	}

	public void setPos2(LatLng pos2) {
		this.pos2 = pos2;
	}
	
	
	
	
	
	

}
