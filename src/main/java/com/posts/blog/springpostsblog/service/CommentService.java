package com.posts.blog.springpostsblog.service;

import java.util.List;

import com.posts.blog.springpostsblog.payload.CommentDto;

public interface CommentService {

	CommentDto createComment(long postId , CommentDto commentDto);
	
	List<CommentDto> getCommentsByPostId(long postId);
	
	CommentDto getCommentById(Long postId, Long commentId );
	
	CommentDto updateComment(Long postId, Long commentId, CommentDto commentDto);
	
	void deleteComment(Long postId, Long commentId);
}
