package com.vishal.todo.web.login;

import lombok.NoArgsConstructor;
import org.apache.wicket.markup.head.CssHeaderItem;
import org.apache.wicket.markup.head.IHeaderResponse;
import org.apache.wicket.markup.head.JavaScriptHeaderItem;
import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.request.mapper.parameter.PageParameters;

public class LoginPage extends WebPage {
    private static final long serialVersionUID = 1L;
    private Label loginMsg;
    public LoginPage() {
        loginMsg = new Label("loginMsg", "");
        add(loginMsg);
        add(new LoginForm("loginForm"));
    }

    public LoginPage(String loginMessage) {
        loginMsg = new Label("loginMsg", loginMessage);
        add(loginMsg);
        add(new LoginForm("loginForm"));
    }

//    public LoginPage(final PageParameters parameters, String msg) {
//        super(parameters);
//
//        // TODO Add your page's components here
//        add(new LoginForm("loginForm"));
//        add(new Label("loginMsg", "msg"));
//
//    }

    @Override
    public void renderHead(IHeaderResponse response) {
        response.render(JavaScriptHeaderItem.forUrl("https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/js/bootstrap.bundle.min.js"));
        response.render(CssHeaderItem.forUrl("https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/css/bootstrap.min.css"));
    }
}
