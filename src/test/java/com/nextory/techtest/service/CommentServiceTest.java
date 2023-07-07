package com.nextory.techtest.service;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.nextory.techtest.models.Comment;
import com.nextory.techtest.repositories.CommentRepository;
import com.nextory.techtest.services.CommentService;

@ExtendWith(MockitoExtension.class)
public class CommentServiceTest {

	@Mock
	private CommentRepository commentRepository;

	@InjectMocks
	private CommentService commentService;

	@Test
	public void testGetAllComments() {
		List<Comment> comments = new ArrayList<>();
		when(commentRepository.findAll()).thenReturn(comments);

		List<Comment> result = commentService.getAllComments();

		assertEquals(comments, result);
	}

	@Test
	public void testSaveComment() {
		Comment comment = new Comment();
		when(commentRepository.save(comment)).thenReturn(comment);

		Comment result = commentService.saveComment(comment);

		assertEquals(comment, result);
	}
}