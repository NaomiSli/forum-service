package telran.java52.forum.dto;

import java.util.Map;
import java.util.Set;

import lombok.Getter;

@Getter
public class AnswerDto {
	String id;
	String title;
	String content;
	String author;
	String dateCreated;
	Set<String> tags;
	Long likes;
	Map<String, String> comments;

}
