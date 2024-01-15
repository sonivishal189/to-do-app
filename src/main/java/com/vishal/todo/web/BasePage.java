package com.vishal.todo.web;

import com.vishal.todo.web.home.HomePage;
import com.vishal.todo.web.login.LoginForm;
import com.vishal.todo.web.login.LoginPage;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.markup.html.AjaxLink;
import org.apache.wicket.markup.head.CssHeaderItem;
import org.apache.wicket.markup.head.IHeaderResponse;
import org.apache.wicket.markup.head.JavaScriptHeaderItem;
import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.request.mapper.parameter.PageParameters;

public abstract class BasePage extends WebPage {

    public BasePage(PageParameters params) {
        super(params);
        initPage();
    }

    public BasePage() {
        initPage();
    }

    private void initPage() {
        add(new Form<>("logout") {
            @Override
            protected void onSubmit() {
                LoginForm.loggedInUser = "LoggedOut";
                setResponsePage(new LoginPage());
            }
        });

        add(new AjaxLink<Void>("brandName") {
            @Override
            public void onClick(AjaxRequestTarget ajaxRequestTarget) {
                setResponsePage(HomePage.class);
            }
        });
    }


    @Override
    public void renderHead(IHeaderResponse response) {
        super.renderHead(response);

        response.render(JavaScriptHeaderItem.forReference(getApplication().getJavaScriptLibrarySettings().getJQueryReference()));
        response.render(JavaScriptHeaderItem.forReference(getApplication().getJavaScriptLibrarySettings().getWicketAjaxReference()));

        response.render(JavaScriptHeaderItem.forUrl("https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/js/bootstrap.bundle.min.js"));
        response.render(CssHeaderItem.forUrl("https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/css/bootstrap.min.css"));
    }

}