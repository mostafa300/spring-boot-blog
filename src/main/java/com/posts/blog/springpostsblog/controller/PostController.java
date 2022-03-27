package com.posts.blog.springpostsblog.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.posts.blog.springpostsblog.payload.PostDto;
import com.posts.blog.springpostsblog.service.PostService;

import lombok.Delegate;

@RestController
@RequestMapping("/api/posts")
public class PostController {

	private PostService postservice ;
	// constructor
	public PostController(PostService postservice) {
		 this.postservice = postservice ;
	}
	
	
	// create blog post 
	@PostMapping
	public ResponseEntity<PostDto> createPost(@RequestBody PostDto postDto){
		return new ResponseEntity<PostDto>(postservice.createPost(postDto),HttpStatus.CREATED);
	}
	
	// Get All Requests 
	@GetMapping
	public List<PostDto> getAllPosts(){
		return postservice.getAllPosts();
	}
	
	
	// Get Post By ID 
	@GetMapping("/{id}")
	public ResponseEntity<PostDto> getPostById(@PathVariable(name = "id") long id) {
		return new ResponseEntity<PostDto>(postservice.getPostById(id),HttpStatus.OK);
		//return ResponseEntity.ok(postservice.getPostById(id));
	}
	
	// Update Post By ID
	@PutMapping("/{id}")
	public ResponseEntity<PostDto> updatePost(@RequestBody PostDto postDto , @PathVariable(name = "id") long id){
		PostDto postResponse = postservice.updatePost(postDto, id);
		return new ResponseEntity<>(postResponse,HttpStatus.OK);
	}
	
	// Delete Post By ID
	@DeleteMapping("/{id}")
	public ResponseEntity<String> deletePostById (@PathVariable(name = "id") long id) {
		postservice.deletePostById(id);
		return new ResponseEntity<>("Post Deleted Sucessfully",HttpStatus.OK);
	}
}
