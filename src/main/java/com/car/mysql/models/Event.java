package com.car.mysql.models;

import java.io.Serializable;
import java.util.Collections;
import java.util.Date;
import java.util.Set;
import java.util.TreeSet;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
@Table(name = "event")
public class Event implements Serializable {
	private static final long serialVersionUID = 3368363855328388120L;
	
	@Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
	
	@Column(name = "title", nullable = false)
	private String title;
	
	@Column(name = "type", nullable = false)
	private long type;
	
	@Lob
	@Column(name = "shortdescription", nullable = true, length = 1000)
	private String shortDescription;
	
	@Lob
	@Column(name = "fulldescription", nullable = true, length = 10000)
	private String fullDescription;
	
	@Column(name = "author", nullable = true)
	private String author;
	
    @OneToMany(fetch = FetchType.EAGER, mappedBy = "event", cascade = CascadeType.ALL)
	Set<ImageEvent> imageEvents;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "create_date", nullable = false, columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
    private Date createDate;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "modified_date", nullable = true)
    private Date modifiedDate;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getShortDescription() {
		return shortDescription;
	}

	public void setShortDescription(String shortDescription) {
		this.shortDescription = shortDescription;
	}

	public String getFullDescription() {
		return fullDescription;
	}

	public void setFullDescription(String fullDescription) {
		this.fullDescription = fullDescription;
	}

	public String getAuthor() {
		return author;
	}

	public void setAuthor(String author) {
		this.author = author;
	}

	public Date getCreateDate() {
		return createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	public Date getModifiedDate() {
		return modifiedDate;
	}

	public void setModifiedDate(Date modifiedDate) {
		this.modifiedDate = modifiedDate;
	}

	public Set<ImageEvent> getImageEvents() {
		Set<ImageEvent> results = new TreeSet<ImageEvent>();
		results.addAll(imageEvents);
		return results;
	}

	public void setImageEvents(Set<ImageEvent> imageEvents) {
		this.imageEvents = imageEvents;
	}

	public long getType() {
		return type;
	}

	public void setType(long type) {
		this.type = type;
	}

	@Override
	public String toString() {
		return "Event [id=" + id + ", title=" + title + ", type=" + type + ", shortDescription=" + shortDescription
				+ ", fullDescription=" + fullDescription + ", author=" + author + ", imageEvents=" + imageEvents
				+ ", createDate=" + createDate + ", modifiedDate=" + modifiedDate + "]";
	}
}
