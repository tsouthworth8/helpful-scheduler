package com.techelevator.pojo;

import java.time.LocalDate;

public class TimeOff {
	
	private long id;
	
	private LocalDate dateRequested;
	
	private LocalDate dateOff;
	
	private int timeStart;
	
	private int timeEnd;

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public LocalDate getDateRequested() {
		return dateRequested;
	}

	public void setDateRequested(LocalDate dateRequested) {
		this.dateRequested = dateRequested;
	}

	public LocalDate getDateOff() {
		return dateOff;
	}

	public void setDateOff(LocalDate dateOff) {
		this.dateOff = dateOff;
	}

	public int getTimeStart() {
		return timeStart;
	}

	public void setTimeStart(int timeStart) {
		this.timeStart = timeStart;
	}

	public int getTimeEnd() {
		return timeEnd;
	}

	public void setTimeEnd(int timeEnd) {
		this.timeEnd = timeEnd;
	}
	
	
	
	

}
