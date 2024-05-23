package telran.java52.security.filter;

import java.io.IOException;
import java.security.Principal;

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
import telran.java52.accounting.dao.UserAccountRepository;
import telran.java52.accounting.model.UserAccount;
@Component
@RequiredArgsConstructor
@Order(20)
public class AdminManagingRolesFilter implements Filter {
	
	final UserAccountRepository userAccountRepository;

	    @Override
	    public void doFilter(ServletRequest req, ServletResponse resp, FilterChain chain)
	            throws IOException, ServletException {
	        HttpServletRequest request = (HttpServletRequest) req;
	        HttpServletResponse response = (HttpServletResponse) resp;

	        if (checkEndpoint(request.getMethod(), request.getServletPath())) {
	            Principal principal = request.getUserPrincipal();
	            if (principal != null) {
	                UserAccount userAccount = userAccountRepository.findById(principal.getName())
	                        .orElse(null);
	                if (userAccount == null || !userAccount.getRoles().contains("ADMINISTRATOR")) {
	                	response.sendError(403);
	                    return;
	                }
	            } else {
	                response.sendError(401);
	                return;
	            }
	        }

	        chain.doFilter(request, response);
	    }

	    private boolean checkEndpoint(String method, String path) {
	        return ((HttpMethod.PUT.matches(method) || HttpMethod.DELETE.matches(method)) && path.matches("/account/user/{login}/role/{role}")) ||
	        		 HttpMethod.DELETE.matches(method) && path.matches("/account/user/{login})");
	    }
	}



