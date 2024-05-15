package telran.java52.post.dao;

import java.time.LocalDateTime;
import java.util.List;
import org.springframework.data.mongodb.repository.MongoRepository;
import telran.java52.post.dto.PostDto;
import telran.java52.post.model.Post;

public interface PostRepository extends MongoRepository<Post, String> {
	
	Iterable<PostDto> findPostsByAuthorIgnoreCase(String author);

	Iterable<PostDto> findByTags(List<String> tags);

	Iterable<PostDto> findFromToByDateCreated(LocalDateTime dateFrom, LocalDateTime dateTo);
}
