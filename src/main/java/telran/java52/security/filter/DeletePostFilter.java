package telran.java52.security.filter;

import java.io.IOException;
import java.security.Principal;
import java.util.Set;

import org.springframework.core.annotation.Order;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Component;

import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
//import telran.java52.accounting.dao.UserAccountRepository;
//import telran.java52.accounting.model.Role;
//import telran.java52.accounting.model.UserAccount;
import telran.java52.post.dao.PostRepository;
import telran.java52.post.model.Post;

@Component
@RequiredArgsConstructor
@Order(60)
public class DeletePostFilter implements Filter {

	final PostRepository postRepository;
//    final UserAccountRepository userAccountRepository;

	@Override
	public void doFilter(ServletRequest req, ServletResponse resp, FilterChain chain)
			throws IOException, ServletException {
		HttpServletRequest request = (HttpServletRequest) req;
		HttpServletResponse response = (HttpServletResponse) resp;
		if (checkEndPoint(request.getMethod(), request.getServletPath())) {
			Principal principal = request.getUserPrincipal();

			String[] parts = request.getServletPath().split("/");
			String postId = parts[parts.length - 1];
			Post post = postRepository.findById(postId).orElse(null);
			if (post == null) {
				response.sendError(404, "Not found");
				return;
			}
			if (principal instanceof telran.java52.security.model.User) {
				Set<String> roles = ((telran.java52.security.model.User) principal).getRoles();
				if (!(principal.getName().equals(post.getAuthor()) || roles.contains("MODERATOR"))) {
					response.sendError(403, "You do not have permission to access this resource");
					return;
				}
			}
		}

//        	
//            String principal = request.getUserPrincipal().getName();
//            UserAccount userAccount = userAccountRepository.findById(principal).get();
//            String[] parts = request.getServletPath().split("/");
//            String postId = parts[parts.length - 1];
//            Post post = postRepository.findById(postId).orElse(null);
//            if (post == null) {
//                response.sendError(404, "Not found");
//                return;
//            }
//            if (!(principal.equals(post.getAuthor()) || userAccount.getRoles().contains(Role.MODERATOR))) {
//                response.sendError(403, "You do not have permission to access this resource");
//                return;
//            }
//        }
		chain.doFilter(request, response);
	}

	private boolean checkEndPoint(String method, String path) {
		return HttpMethod.DELETE.matches(method) && path.matches("/forum/post/\\w+");
	}

}
