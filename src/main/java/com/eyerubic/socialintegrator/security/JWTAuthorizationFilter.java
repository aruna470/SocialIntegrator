package com.eyerubic.socialintegrator.security;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import com.eyerubic.socialintegrator.helpers.Jwt;

public class JWTAuthorizationFilter extends OncePerRequestFilter {

	private static final String HEADER = "Authorization";
	private static final String PREFIX = "Bearer ";

    private String jwtSecret;

    public void setJwtSecret(String jwtSecret) {
        this.jwtSecret = jwtSecret;
    }
    
	@Override
	protected void doFilterInternal(HttpServletRequest request, 
		HttpServletResponse response, FilterChain chain) 
			throws ServletException, IOException {
		
        Jwt jwt = new Jwt();
		String token = getToken(request);
    
        if (!token.equals("")) {
			try {
				HashMap<String, String> userInfo = jwt.verifyToken(token, this.jwtSecret);
				UsernamePasswordAuthenticationToken authentication 
					= new UsernamePasswordAuthenticationToken(userInfo.get("email"), null, 
						new ArrayList<>());
				SecurityContextHolder.getContext().setAuthentication(authentication);

				request.setAttribute("userInfo", userInfo);

				chain.doFilter(request, response);
			} catch (Exception e) {
				SecurityContextHolder.clearContext();
				chain.doFilter(request, response);
			}
        } else {
			SecurityContextHolder.clearContext();
			chain.doFilter(request, response);
		}
	}	

	private String getToken(HttpServletRequest request) {
		String authenticationHeader = request.getHeader(HEADER);
		if (authenticationHeader == null || !authenticationHeader.startsWith(PREFIX)) {
			return "";
		} else {
			return authenticationHeader.replace(PREFIX, "");
		}
	}
}