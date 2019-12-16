package ch.heigvd.amt.projectTwo.security;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class JwtTokenFilter extends OncePerRequestFilter {

    private JwtTokenProvider jwtTokenProvider;

    private Logger logger= LoggerFactory.getLogger(JwtTokenFilter.class);

    public JwtTokenFilter(JwtTokenProvider jwtTokenProvider) {
        this.jwtTokenProvider = jwtTokenProvider;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, FilterChain filterChain) throws ServletException, IOException {
        String token = jwtTokenProvider.extractToken(httpServletRequest);

        logger.info(token);
        if (token == null || !jwtTokenProvider.validateToken(token)) {
            logger.error("token invalid");
            httpServletResponse.sendError(HttpStatus.INTERNAL_SERVER_ERROR.value(), "Missing, invalid or expired Authorisation");
            return;
        }

        httpServletRequest.setAttribute("user_mail", jwtTokenProvider.getEmail(token));
        httpServletRequest.setAttribute("user_admin", jwtTokenProvider.isAdmin(token));
        httpServletRequest.setAttribute("user_id", jwtTokenProvider.getId(token));
        filterChain.doFilter(httpServletRequest, httpServletResponse);
    }

}
