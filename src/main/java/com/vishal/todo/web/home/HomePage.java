package com.vishal.todo.web.home;

import com.vishal.todo.web.login.LoginForm;
import com.vishal.todo.web.login.LoginPage;
import org.apache.wicket.markup.head.CssHeaderItem;
import org.apache.wicket.markup.head.IHeaderResponse;
import org.apache.wicket.markup.head.JavaScriptHeaderItem;
import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.request.mapper.parameter.PageParameters;

public class HomePage extends WebPage {
    private static final long serialVersionUID = 1L;

    public HomePage() {

        if(LoginForm.loggedInUser.isBlank()) {
            setResponsePage(new LoginPage("Please login to proceed."));
        }

        // TODO Add your page's components here
        add(new Label("welcomeMsg", "Welcome " + LoginForm.loggedInUser));

        add(new Form<>("logout") {
            @Override
            protected void onSubmit() {
                LoginForm.loggedInUser = "";
                setResponsePage(new LoginPage());
            }
        });
    }

    @Override
    public void renderHead(IHeaderResponse response) {
        response.render(JavaScriptHeaderItem
                .forUrl("https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/js/bootstrap.bundle.min.js"));
        response.render(
                CssHeaderItem.forUrl("https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/css/bootstrap.min.css"));
    }
}
