package authorization;

import lombok.extern.java.Log;

import javax.inject.Inject;
import javax.security.enterprise.SecurityContext;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Log
@WebFilter("/authorized/*")
public class SecurityFilter extends HttpFilter {

    @Inject
    private SecurityContext securityContext;

    @Override
    protected void doFilter(HttpServletRequest req, HttpServletResponse res, FilterChain chain) throws IOException, ServletException {
        if (securityContext.getCallerPrincipal() == null && !isLoginPage(req)) {
            res.sendRedirect(req.getContextPath().concat("/authorization.xhtml"));
        } else {
            chain.doFilter(req, res);
        }
    }

    private boolean isLoginPage(HttpServletRequest request) {
        return request.getRequestURI().endsWith("authorization.xhtml");
    }
}
