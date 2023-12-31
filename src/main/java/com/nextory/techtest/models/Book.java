package com.nextory.techtest.models;

import java.time.LocalDateTime;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "books")
@Getter
@Setter
@NoArgsConstructor
public class Book {
	@Id
	@GeneratedValue
	private Long id;

	@Column(name = "title")
	private String title;

	@Column(name = "cover")
	private String cover;

	@Column(name = "boost_score")
	private Integer boostScore;

	@JsonBackReference
	@ManyToOne
	@JoinColumn(name = "author_id")
	private Author author;

	@JsonManagedReference
	@OneToMany(mappedBy = "book")
	private List<Comment> comments;

	private String authorName;

	@Column(name = "created_at")
	private LocalDateTime createdAt;

	@Column(name = "updated_at")
	private LocalDateTime updatedAt;

}
