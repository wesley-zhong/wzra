package com.wd.erp.zra.bean;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

public class CollectNoteList {

	@JsonProperty("collect_note_list")
	private List<CollectNote> collectNoteList;

	public void setCollectNoteList(List<CollectNote> collectNoteList) {
		this.collectNoteList = collectNoteList;
	}

	public List<CollectNote> getCollectNoteList() {
		return collectNoteList;
	}
}
