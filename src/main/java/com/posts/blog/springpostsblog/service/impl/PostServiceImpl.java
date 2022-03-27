package com.posts.blog.springpostsblog.service.impl;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import com.posts.blog.springpostsblog.exception.ResourceNotFoundException;
import com.posts.blog.springpostsblog.model.Post;
import com.posts.blog.springpostsblog.payload.PostDto;
import com.posts.blog.springpostsblog.repository.PostRepository;
import com.posts.blog.springpostsblog.service.PostService;

@Service
public class PostServiceImpl implements PostService {

	private PostRepository postRepository;
	private ModelMapper mapper;
	
	
    public PostServiceImpl(PostRepository postRepository,ModelMapper mapper) {
        this.postRepository = postRepository;
        this.mapper = mapper; 
    }
	
	@Override
	public PostDto createPost(PostDto postDto) {
		// Convert DTO to Model Entity
		Post post = mapToEntityModel(postDto);
		
		Post newPost = postRepository.save(post);
		
		// Convert Model Entity to DTO
		PostDto postResponse = mapToDTO(newPost); 
			
		return postResponse ;
	}
	
	@Override
	public List<PostDto> getAllPosts(){
		List<Post> posts = postRepository.findAll();
        return posts.stream().map(post -> mapToDTO(post)).collect(Collectors.toList());
	}
	
	@Override
	public PostDto getPostById(long id) {
		Post post = postRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Post", "id" , id));
		return mapToDTO(post);
	}
	
	@Override
	public PostDto updatePost(PostDto postDto , long id) {
		Post post = postRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Post", "id" , id));
		
		post.setTitle(postDto.getTitle());
		post.setDescription(postDto.getDescription());
		post.setContent(postDto.getContent());
		
		Post updatedPost = postRepository.save(post);
		return mapToDTO(updatedPost);
	}
	
	@Override
	public void deletePostById(long id) {
		Post post = postRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Post", "id" , id));
		postRepository.delete(post);
	}
	
	
	
	// Convert DTO to Entity Model 
	private Post mapToEntityModel(PostDto postDto) {
		Post post = mapper.map(postDto, Post.class);
//		Post post = new Post(); 
//		post.setTitle(postDto.getTitle());
//		post.setDescription(postDto.getDescription());
//		post.setContent(postDto.getContent());
		return post ;
	}
	// Convert Model Entity to DTO 
	private PostDto mapToDTO(Post post) {
		PostDto postDto = mapper.map(post, PostDto.class);
//		PostDto postDto = new PostDto();
//		postDto.setId(post.getId());
//		postDto.setTitle(post.getTitle());
//		postDto.setDescription(post.getDescription());
//		postDto.setContent(post.getContent());
		return postDto ;
	}
	
}
