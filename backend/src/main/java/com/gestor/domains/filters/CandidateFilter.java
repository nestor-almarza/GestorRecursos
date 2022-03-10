package com.gestor.domains.filters;

import java.util.Date;

public class CandidateFilter {
	private String partname;
	private Date updatedAtStartRange;
	private Date updatedAtEndRange;

	public String getPartname() {
		return partname;
	}

	public void setPartname(String partname) {
		this.partname = partname;
	}


	public Date getUpdatedAtStartRange() {
		return updatedAtStartRange;
	}

	public void setUpdatedAtStartRange(Date updatedAtStartRange) {
		this.updatedAtStartRange = updatedAtStartRange;
	}

	public Date getUpdatedAtEndRange() {
		return updatedAtEndRange;
	}

	public void setUpdatedAtEndRange(Date updatedAtEndRange) {
		this.updatedAtEndRange = updatedAtEndRange;
	}
}
