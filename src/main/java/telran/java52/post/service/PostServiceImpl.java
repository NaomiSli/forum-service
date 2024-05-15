package telran.java52.post.service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import telran.java52.post.dao.PostRepository;
import telran.java52.post.dto.DatePeriodDto;
import telran.java52.post.dto.NewCommentDto;
import telran.java52.post.dto.NewPostDto;
import telran.java52.post.dto.PostDto;
import telran.java52.post.dto.exceptions.PostNotFoundException;
import telran.java52.post.model.Comment;
import telran.java52.post.model.Post;

@Service
@RequiredArgsConstructor
public class PostServiceImpl implements PostService {

	final PostRepository postRepository;
	final ModelMapper modelMapper;

	@Override
	public PostDto addNewPost(String author, NewPostDto newPostDto) {
		Post post = modelMapper.map(newPostDto, Post.class);
		post.setAuthor(author);
		post = postRepository.save(post);
		return modelMapper.map(post, PostDto.class);
	}

	@Override
	public PostDto findPostById(String id) {
		Post post = postRepository.findById(id).orElseThrow(PostNotFoundException::new);
		return modelMapper.map(post, PostDto.class);
	}

	@Override
	public PostDto removePost(String id) {
		Post post = postRepository.findById(id).orElseThrow(PostNotFoundException::new);
		postRepository.delete(post);
		return modelMapper.map(post, PostDto.class);
	}

	@Override
	public PostDto updatePost(String id, NewPostDto newPostDto) {
		Post post = postRepository.findById(id).orElseThrow(PostNotFoundException::new);
		String content = newPostDto.getContent();
		if (content != null) {
			post.setContent(content);
		}
		String title = newPostDto.getTitle();
		if (title != null) {
			post.setTitle(title);
		}
		Set<String> tags = newPostDto.getTags();
		if (tags != null) {
			tags.forEach(post::addTag);
		}
		post = postRepository.save(post);
		return modelMapper.map(post, PostDto.class);
	}

	@Override
	public PostDto addComment(String id, String author, NewCommentDto newCommentDto) {
		Post post = postRepository.findById(id).orElseThrow(PostNotFoundException::new);
		Comment comment = new Comment(author, newCommentDto.getMessage());
		post.addComment(comment);
		post = postRepository.save(post);
		return modelMapper.map(post, PostDto.class);
	}

	@Override
	public void addLike(String id) {
		Post post = postRepository.findById(id).orElseThrow(PostNotFoundException::new);
		post.addLike();
		postRepository.save(post);

	}

	@Override
	public Iterable<PostDto> findPostsByAuthor(String author) {
		return postRepository.findPostsByAuthorIgnoreCase(author);
	}

	@Override
	public Iterable<PostDto> findPostsByTags(List<String> tags) {
		List<PostDto> result = new ArrayList<>();
		Iterable<PostDto> allPosts = postRepository.findByTags(tags);
		for (PostDto post : allPosts) {
			if (post.getTags().containsAll(tags)) {
				result.add(post);
			}
		}
		return result;

	}

	@Override
	public Iterable<PostDto> findPostsByPeriod(DatePeriodDto datePeriodDto) {
		LocalDate dateFrom = datePeriodDto.getDateFrom();
	    LocalDateTime fromDateTime = dateFrom.atStartOfDay();
	    
	    LocalDate dateTo = datePeriodDto.getDateTo();
	    LocalDateTime toDateTime = dateTo.atTime(LocalTime.MAX);
		return postRepository.findFromToByDateCreated(fromDateTime, toDateTime);
	}

}













