package com.tnpsc.coaching.security;

import com.tnpsc.coaching.service.CustomUserDetailsService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final JwtUtil jwtUtil;
    private final CustomUserDetailsService userDetailsService;

    public JwtAuthenticationFilter(JwtUtil jwtUtil,
                                   CustomUserDetailsService userDetailsService) {
        this.jwtUtil = jwtUtil;
        this.userDetailsService = userDetailsService;
    }

   /* @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain)
            throws ServletException, IOException {

        final String authHeader = request.getHeader("Authorization");

        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            filterChain.doFilter(request, response);
            return;
        }

        String token = authHeader.substring(7);
        String username = jwtUtil.extractUsername(token);

        if (username != null &&
                SecurityContextHolder.getContext().getAuthentication() == null) {

            UserDetails userDetails =
                    userDetailsService.loadUserByUsername(username);

            if (jwtUtil.isTokenValid(token, userDetails)) {

                UsernamePasswordAuthenticationToken authToken =
                        new UsernamePasswordAuthenticationToken(
                                userDetails,
                                null,
                                userDetails.getAuthorities()
                        );

                authToken.setDetails(
                        new WebAuthenticationDetailsSource()
                                .buildDetails(request)
                );

                SecurityContextHolder.getContext()
                        .setAuthentication(authToken);
            }
        }

        filterChain.doFilter(request, response);
    }*/
   @Override
   protected void doFilterInternal(HttpServletRequest request,
                                   HttpServletResponse response,
                                   FilterChain filterChain)
           throws ServletException, IOException {

       final String authHeader = request.getHeader("Authorization");

       // Debug logging
       System.out.println("Request URI: " + request.getRequestURI());
       System.out.println("Auth Header: " + authHeader);

       if (authHeader == null || !authHeader.startsWith("Bearer ")) {
           System.out.println("No valid auth header found");
           filterChain.doFilter(request, response);
           return;
       }

       String token = authHeader.substring(7);
       System.out.println("Token extracted: " + token.substring(0, Math.min(20, token.length())) + "...");

       try {
           String username = jwtUtil.extractUsername(token);
           System.out.println("Username from token: " + username);

           String role = jwtUtil.extractRole(token);
           System.out.println("Role from token: " + role);

           if (username != null &&
                   SecurityContextHolder.getContext().getAuthentication() == null) {

               UserDetails userDetails =
                       userDetailsService.loadUserByUsername(username);

               System.out.println("UserDetails authorities: " + userDetails.getAuthorities());

               if (jwtUtil.isTokenValid(token, userDetails)) {
                   System.out.println("Token is valid");

                   UsernamePasswordAuthenticationToken authToken =
                           new UsernamePasswordAuthenticationToken(
                                   userDetails,
                                   null,
                                   userDetails.getAuthorities()
                           );

                   authToken.setDetails(
                           new WebAuthenticationDetailsSource()
                                   .buildDetails(request)
                   );

                   SecurityContextHolder.getContext()
                           .setAuthentication(authToken);
                   System.out.println("Authentication set in context");
               } else {
                   System.out.println("Token is invalid or expired");
               }
           }
       } catch (Exception e) {
           System.out.println("Error processing token: " + e.getMessage());
           e.printStackTrace();
       }

       filterChain.doFilter(request, response);
   }

}