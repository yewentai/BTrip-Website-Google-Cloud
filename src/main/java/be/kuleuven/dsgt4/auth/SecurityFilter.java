package be.kuleuven.dsgt4.auth;

import be.kuleuven.dsgt4.User;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.filter.OncePerRequestFilter;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthException;
import com.google.firebase.auth.FirebaseToken;
import com.google.firebase.auth.UserRecord;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.*;
import org.json.JSONObject;
import java.nio.charset.StandardCharsets;

@Component
public class SecurityFilter extends OncePerRequestFilter {
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String authorizationHeader = request.getHeader("Authorization");
        
        if (authorizationHeader == null || !authorizationHeader.startsWith("Bearer ")) {
            // Token is missing or invalid
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            response.getWriter().write("Missing or invalid authorization header.");
            return;
        }
        
        String idToken = authorizationHeader.substring(7);
        if (!isProductionToken(idToken)) {
            try {
                JSONObject jsonObject = new JSONObject(HS256Decode(idToken));
                String email = jsonObject.getString("email");
                String role = jsonObject.optString("role");
                User user = new User(email, role);
                SecurityContextHolder.getContext().setAuthentication(new FirebaseAuthentication(user));
            } catch (Exception e) {
                response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                response.getWriter().write("Exception: " + e.getMessage());
                return;
            }
        }
        else {
            try {
                System.out.println("Token: " + idToken);
                FirebaseToken decodedToken = FirebaseAuth.getInstance().verifyIdToken(idToken);
                String uid = decodedToken.getUid();
                String email = decodedToken.getEmail();
                boolean isManager = false;
                Map<String, Object> claims = decodedToken.getClaims();
                if (claims.containsKey("role") && claims.get("role").equals("manager")) {
                    isManager = true;
                }
                if (email.equals("riot@lol.com")) {
                    isManager = true;
                }
                User user = new User(email, isManager ? "manager" : "user");
                SecurityContextHolder.getContext().setAuthentication(new FirebaseAuthentication(user));
            } catch (FirebaseAuthException e) {
                // Invalid or expired token
                response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                response.getWriter().write("Exception: " + e.getMessage());
                return;
            } catch (Exception e) {
                // Other exceptions
                response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
                response.getWriter().write("Internal server error: " + e.getMessage());
                return;
            }
        }
        filterChain.doFilter(request, response);
    }

    private boolean isProductionToken(String token) {
        String[] segments = token.split("\\.");
        return segments.length != 2;
    }

    private String HS256Decode(String token) {
        String[] chunks = token.split("\\.");
        Base64.Decoder decoder = Base64.getUrlDecoder();
        String header = new String(decoder.decode(chunks[0]), StandardCharsets.UTF_8);
        String payload = new String(decoder.decode(chunks[1]), StandardCharsets.UTF_8);

        return payload;
    }

    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) {
        String path = request.getRequestURI().substring(request.getContextPath().length());
        return !path.startsWith("/api");
    }

    private static class FirebaseAuthentication implements Authentication {
        private final User user;

        FirebaseAuthentication(User user) {
            this.user = user;
        }

        @Override
        public Collection<? extends GrantedAuthority> getAuthorities() {
            if (user.isManager()) {
                return List.of(new SimpleGrantedAuthority("manager"));
            } else {
                return new ArrayList<>();
            }
        }

        @Override
        public Object getCredentials() {
            return null;
        }

        @Override
        public Object getDetails() {
            return null;
        }

        @Override
        public User getPrincipal() {
            return this.user;
        }

        @Override
        public boolean isAuthenticated() {
            return true;
        }

        @Override
        public void setAuthenticated(boolean b) throws IllegalArgumentException {

        }

        @Override
        public String getName() {
            return null;
        }
    }
}

