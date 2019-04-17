package com.myapp.library.controller.validation;

public class SubjectSearchCriteria {

	private int durationStart;
	private int durationEnd;

	public int getDurationStart() {
		return durationStart;
	}

	public void setDurationStart(int durationStart) {
		this.durationStart = durationStart;
	}

	public int getDurationEnd() {
		return durationEnd;
	}

	public void setDurationEnd(int durationEnd) {
		this.durationEnd = durationEnd;
	}

	@Override
	public String toString() {
		return "SubjectSearchCriteria [durationStart=" + durationStart + ", durationEnd=" + durationEnd + "]";
	}

	public SubjectSearchCriteria(int durationStart, int durationEnd) {
		super();
		this.durationStart = durationStart;
		this.durationEnd = durationEnd;
	}

	public SubjectSearchCriteria() {
	}

}
