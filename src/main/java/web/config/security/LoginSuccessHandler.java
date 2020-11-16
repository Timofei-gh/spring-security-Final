package web.config.security;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.web.DefaultRedirectStrategy;
import org.springframework.security.web.RedirectStrategy;
import org.springframework.security.web.WebAttributes;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.Collection;
import java.util.Set;

@Component
public class LoginSuccessHandler implements AuthenticationSuccessHandler {

    private RedirectStrategy redirectStrategy = new DefaultRedirectStrategy();

    @Override
    public void onAuthenticationSuccess(HttpServletRequest httpServletRequest,
                                        HttpServletResponse httpServletResponse,
                                        Authentication authentication) throws IOException {
//        successHandler(httpServletRequest, httpServletResponse, authentication);
//        clearAuthenticationAttributes(httpServletRequest);
        Set<String> roles = AuthorityUtils.authorityListToSet(authentication.getAuthorities());
        if (roles.contains("ROLE_USER")) {
            httpServletResponse.sendRedirect("/user/user-list_.html");
        } else {
            httpServletResponse.sendRedirect("/admin/user-list.html");
        }
    }


//    protected void successHandler(HttpServletRequest httpServletRequest,
//                                  HttpServletResponse httpServletResponse,
//                                  Authentication authentication) throws IOException {
//
//        String targetUrl = determineTargetUrl(authentication);
//
//        redirectStrategy.sendRedirect(httpServletRequest, httpServletResponse, targetUrl);
//    }
//
//    protected String determineTargetUrl(Authentication authentication) {
//        boolean isUser = false;
//        boolean isAdmin = false;
//        Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();
//        for (GrantedAuthority grantedAuthority : authorities) {
//            if (grantedAuthority.getAuthority().equals("ROLE_USER")) {
//                isUser = true;
//                break;
//            } else if (grantedAuthority.getAuthority().equals("ROLE_ADMIN")) {
//                isAdmin = true;
//                break;
//            }
//        }
//
//        if (isUser) {
//            return "user/user-list_.html";
//        } else if (isAdmin) {
//            return "admin/user-list.html";
//        } else {
//            return "login.html";
//        }
//    }
//
//    protected void clearAuthenticationAttributes(HttpServletRequest httpServletRequest) {
//        HttpSession httpSession = httpServletRequest.getSession(false);
//        if (httpSession == null) {
//            return;
//        }
//        httpSession.removeAttribute(WebAttributes.AUTHENTICATION_EXCEPTION);
//    }
//
//    public void setRedirectStrategy(RedirectStrategy redirectStrategy) {
//        this.redirectStrategy = redirectStrategy;
//    }
//
//    protected RedirectStrategy getRedirectStrategy() {
//        return redirectStrategy;
//    }



}