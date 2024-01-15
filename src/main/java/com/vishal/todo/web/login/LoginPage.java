package com.vishal.todo.web.login;

import com.vishal.todo.service.UserDetailService;
import lombok.extern.slf4j.Slf4j;
import org.apache.wicket.markup.head.CssHeaderItem;
import org.apache.wicket.markup.head.IHeaderResponse;
import org.apache.wicket.markup.head.JavaScriptHeaderItem;
import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.markup.html.basic.Label;

public class LoginPage extends WebPage {
    private static final long serialVersionUID = 1L;

    private static final UserDetailService USER_DETAIL_SERVICE = new UserDetailService();

    public LoginPage() {
        LoginForm.loggedInUser = "";
        loadLoginPage("");
    }

    public LoginPage(String loginMessage) {
        loadLoginPage(loginMessage);
    }

    private void loadLoginPage(String label) {
        add(new Label("loginMsg", label));
        LoginForm loginForm = new LoginForm("loginForm");
        add(loginForm);
        NewUserRegistrationForm userRegistrationForm = new NewUserRegistrationForm("registerNewUser");
        add(userRegistrationForm);
    }

    @Override
    public void renderHead(IHeaderResponse response) {
        response.render(JavaScriptHeaderItem.forUrl("https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/js/bootstrap.bundle.min.js"));
        response.render(CssHeaderItem.forUrl("https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/css/bootstrap.min.css"));
    }
}
