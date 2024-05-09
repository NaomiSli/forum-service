package telran.java52.forum.controller;

import java.util.List;
import java.util.Set;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import telran.java52.forum.dto.AnswerDto;
import telran.java52.forum.dto.ComentAddDto;
import telran.java52.forum.dto.PeriodDto;
import telran.java52.forum.dto.UserAddDto;
import telran.java52.forume.service.ForumService;

@RestController
public class ForumController {
	ForumService forumService;

	@PostMapping("/forum/post/{user}")
	public AnswerDto addPost(@RequestBody UserAddDto user) {
		return forumService.addPost(user);
	}

	@GetMapping("/forum/post/{postId}")
	public AnswerDto findPostById(@PathVariable("postId") String id) {
		return forumService.findPostById(id);
	}

	@PutMapping("/forum/post/{postId}/like")
	public Long addLike(@PathVariable("postId") String id) {
		return forumService.addLike(id);
	}

	@GetMapping("/forum/posts/author/{user}")
	public List<AnswerDto> findPostByAuthor(@RequestBody UserAddDto user) {
		return forumService.findPostByAuthor(user);
	}

	@PutMapping("/forum/post/{postId}/comment/{user}")
	public AnswerDto addComent(@PathVariable("postId") String id, @RequestBody ComentAddDto coment, @RequestBody UserAddDto user) {
		return forumService.addComent(id, coment, user);
	}

	@DeleteMapping("/forum/post/{postId}")
	public AnswerDto deletePost(@PathVariable("postId") String id) {
		return forumService.deletePost(id);
	}

	@PostMapping("/forum/posts/tags")
	public AnswerDto findPostByTags(@RequestBody Set<String> tags) {
		return forumService.findPostByTags(tags);
	}

	@PostMapping("/forum/posts/period")
	public List<AnswerDto> findPostByPeriod(@RequestBody PeriodDto period) {
		return forumService.findPostByPeriod(period);
	}

	@PutMapping("/forum/post/{postId}")
	public AnswerDto updatePost(@PathVariable("postId") String id, @RequestBody UserAddDto user) {
		return forumService.updatePost(id, user);
	}

}
