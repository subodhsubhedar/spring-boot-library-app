package com.myapp.library.menu;

/**
 * 
 * @author Admin
 *
 */

public enum MainMenu {

	ADD_NEW_SUBJECT(1, "Add new Subject"),

	ADD_NEW_BOOK(2, "Add new Book"),

	DELETE_SUBJECT(3, "Delete Subject"),

	DELETE_BOOK(4, "Delete Book"),

	SEARCH_SUBJECT(5, "Search Subject"),

	SEARCH_BOOK(6, "Search Book"),

	LIST_ALL_BOOKS(7, "List all Books"), LIST_ALL_SUBJECTS(8, "List all Subjects"), 
	
	SEARCH_SUBJECTS_BY_DURATION(9, "Search Subject by duration"),
	
	QUIT(0, "Quit");

	private final int key;
	private final String value;

	MainMenu(int key, String value) {
		this.key = key;
		this.value = value;
	}

	public int getKey() {
		return key;
	}

	public String getValue() {
		return value;
	}

	@Override
	public String toString() {
		return "[key=" + key + " value=" + value + "]";
	}
}
