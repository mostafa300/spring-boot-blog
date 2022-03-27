package com.posts.blog.springpostsblog.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.posts.blog.springpostsblog.model.Comment;
import java.util.List;

public interface CommentRepository extends JpaRepository<Comment, Long>{
	// consider format of nameing finction && pram
	// find by (PostId) ==> pram postID
	List<Comment> findByPostId(long postId); 
}
