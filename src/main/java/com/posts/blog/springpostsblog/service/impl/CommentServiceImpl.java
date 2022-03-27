package com.posts.blog.springpostsblog.service.impl;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.posts.blog.springpostsblog.exception.BlogAPIException;
import com.posts.blog.springpostsblog.exception.ResourceNotFoundException;
import com.posts.blog.springpostsblog.model.Comment;
import com.posts.blog.springpostsblog.model.Post;
import com.posts.blog.springpostsblog.payload.CommentDto;
import com.posts.blog.springpostsblog.repository.CommentRepository;
import com.posts.blog.springpostsblog.repository.PostRepository;
import com.posts.blog.springpostsblog.service.CommentService;

@Service
public class CommentServiceImpl implements CommentService {
	
	private CommentRepository commentRepository ;
	private PostRepository  postRepository ;
	private ModelMapper mapper;
	
	public CommentServiceImpl(CommentRepository commentRepository, PostRepository  postRepository ,ModelMapper mapper) {
		this.commentRepository = commentRepository ;
		this.postRepository    =  postRepository ;
		this.mapper            = mapper ;
	}
	
	
	@Override
	public CommentDto createComment (long postId , CommentDto commentDto) {
	
		Comment comment = mapToEntity(commentDto);
		// Retrive Post Model by ID
		Post post = postRepository.findById(postId).orElseThrow(
                () -> new ResourceNotFoundException("Post", "id", postId));
		
		// Set Post To Comment Entity 
		comment.setPost(post) ;	
		//comment to DB 
		Comment newComment = commentRepository.save(comment);
		return mapToDTO(newComment) ;
	}

	@Override
	public List<CommentDto> getCommentsByPostId(long postId){
		// retrieve comments by postId
		List<Comment> comments = commentRepository.findByPostId(postId);
		
		// convert list of comment entities to list of comment dto's
        return comments.stream().map(comment -> mapToDTO(comment)).collect(Collectors.toList());		
	}
	
	@Override
	public CommentDto getCommentById (Long postId, Long commentId) {
		// Retrive Post Model by ID
		Post post = postRepository.findById(postId).orElseThrow(
                () -> new ResourceNotFoundException("Post", "id", postId));
				
		 // retrieve comment by id
        Comment comment = commentRepository.findById(commentId).orElseThrow(() -> new ResourceNotFoundException("Comment", "id", commentId));
        
        if (!comment.getPost().getId().equals(post.getId())) {
			throw new BlogAPIException(HttpStatus.BAD_REQUEST, "Comment dosn't belong to post");
		}
		return mapToDTO(comment) ;
	}
	
	@Override
	public CommentDto updateComment(Long postId, Long commentId, CommentDto commentDto) {
		// Retrive Post Model by ID
		Post post = postRepository.findById(postId).orElseThrow(
	            () -> new ResourceNotFoundException("Post", "id", postId));
						
		 // retrieve comment by id
        Comment comment = commentRepository.findById(commentId).orElseThrow(() -> new ResourceNotFoundException("Comment", "id", commentId));
        if (!comment.getPost().getId().equals(post.getId())) {
			throw new BlogAPIException(HttpStatus.BAD_REQUEST, "Comment dosn't belong to post");
		}
        
        comment.setName(commentDto.getName());
        comment.setEmail(commentDto.getEmail());
        comment.setBody(commentDto.getBody());
        
        Comment updateComment = commentRepository.save(comment);
        
		return mapToDTO(updateComment) ;
	}
	
	
	@Override
	public void deleteComment(Long postId, Long commentId) {
		// Retrive Post Model by ID
		Post post = postRepository.findById(postId).orElseThrow(
	            () -> new ResourceNotFoundException("Post", "id", postId));
						
		 // retrieve comment by id
        Comment comment = commentRepository.findById(commentId).orElseThrow(() -> new ResourceNotFoundException("Comment", "id", commentId));
        if (!comment.getPost().getId().equals(post.getId())) {
			throw new BlogAPIException(HttpStatus.BAD_REQUEST, "Comment dosn't belong to post");
		}
        
        commentRepository.delete(comment);
	}
	
	 private CommentDto mapToDTO(Comment comment){
	     CommentDto commentDto = mapper.map(comment, CommentDto.class);
//        CommentDto commentDto = new CommentDto();
//        commentDto.setId(comment.getId());
//        commentDto.setName(comment.getName());
//        commentDto.setEmail(comment.getEmail());
//        commentDto.setBody(comment.getBody());
        return  commentDto;
	 }
	
    private Comment mapToEntity(CommentDto commentDto){
        Comment comment = mapper.map(commentDto, Comment.class);
		/*
		 * Comment comment = new Comment(); comment.setId(commentDto.getId());
		 * comment.setName(commentDto.getName());
		 * comment.setEmail(commentDto.getEmail());
		 * comment.setBody(commentDto.getBody());
		 */
        return  comment;
    }
}
