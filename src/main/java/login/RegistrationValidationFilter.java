package login;

import lombok.extern.java.Log;

import javax.inject.Inject;
import javax.servlet.FilterChain;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Log
@WebFilter("/authorize/*")
public class RegistrationValidationFilter extends HttpFilter {

    @Inject
    private PendingAccountBean pendingAccountBean;

    @Override
    protected void doFilter(HttpServletRequest req, HttpServletResponse res, FilterChain chain) throws IOException {
        String registrationHash = parseRegistrationHash(req.getRequestURI());
        pendingAccountBean.activate(registrationHash);
        res.sendRedirect(req.getContextPath().concat("/login.xhtml"));

        // TODO: redirect to confirmation page to show your account is complete.
    }

    private String parseRegistrationHash(String uri) {
        String registrationHash = uri.substring(uri.lastIndexOf("/") + 1);
        return registrationHash;
    }
}
