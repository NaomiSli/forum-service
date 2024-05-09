package telran.java52.forume.service;

import java.util.List;
import java.util.Set;

import telran.java52.forum.dto.AnswerDto;
import telran.java52.forum.dto.ComentAddDto;
import telran.java52.forum.dto.UserAddDto;
import telran.java52.forum.dto.PeriodDto;

public interface ForumService {
	AnswerDto addPost(UserAddDto user);

	AnswerDto findPostById(String id);

	Long addLike(String id);

	List<AnswerDto> findPostByAuthor(UserAddDto user);

	AnswerDto addComent(String id,ComentAddDto coment, UserAddDto user);

	AnswerDto deletePost(String id);

	AnswerDto findPostByTags(Set<String> tags);

	List<AnswerDto> findPostByPeriod(PeriodDto period);

	AnswerDto updatePost(String id, UserAddDto user);
}
