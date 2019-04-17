package com.myapp.library.entity;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

/**
 * 
 * @author Admin
 *
 */
@Entity
@Table(name = "subject_new")
public class Subject implements Serializable {

	/**
	 * Generated serial version id.
	 */
	private static final long serialVersionUID = 9025558978911065102L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name = "subjectId", nullable = false)
	private long subjectId;

	@Column(name = "subjectTitle")
	private String subtitle;

	@Column(name = "durationInHrs")
	private int durationInHrs;

	@OneToMany(mappedBy = "subject", fetch = FetchType.EAGER)
	private Set<Book> references;

	public Subject(long subjectId, String subtitle, int durationInHrs, Set<Book> references) {
		super();

		this.subtitle = subtitle;
		this.durationInHrs = durationInHrs;
		this.references = references;
	}

	public Subject() {
	
	}


	@Override
	public String toString() {
		return "Subject [subjectId=" + subjectId + ", subtitle=" + subtitle + ", durationInHrs=" + durationInHrs + "]";
	}

	public long getSubjectId() {
		return subjectId;
	}

	public void setSubjectId(long subjectId) {
		this.subjectId = subjectId;
	}

	public String getSubtitle() {
		return subtitle;
	}

	public void setSubtitle(String subtitle) {
		this.subtitle = subtitle;
	}

	public int getDurationInHrs() {
		return durationInHrs;
	}

	public void setDurationInHrs(int durationInHrs) {
		this.durationInHrs = durationInHrs;
	}

	public Set<Book> getReferences() {
		return references;
	}

	public void setReferences(Set<Book> references) {
		this.references = references;
	}

	public void addReferences(Book book) {
		if(this.references==null) {
			this.references =  new HashSet<Book>();
		}
		this.references.add(book);
	}
}
