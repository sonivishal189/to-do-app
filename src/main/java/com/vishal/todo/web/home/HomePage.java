package com.vishal.todo.web.home;

import com.vishal.todo.web.BasePage;
import com.vishal.todo.web.login.LoginForm;
import com.vishal.todo.web.login.LoginPage;

import com.vishal.todo.web.task.create.CreateTaskForm;
import com.vishal.todo.web.task.view.ViewAllTask;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.markup.html.AjaxLink;
import org.apache.wicket.markup.head.CssHeaderItem;
import org.apache.wicket.markup.head.IHeaderResponse;
import org.apache.wicket.markup.head.JavaScriptHeaderItem;
import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.request.mapper.parameter.PageParameters;

public class HomePage extends BasePage {
    private static final long serialVersionUID = 1L;

    public HomePage() {

        if (LoginForm.loggedInUser.isBlank()) {
            setResponsePage(new LoginPage("Please login to proceed."));
        }

        addWelcomeMessage();
        addCreateTaskForm();
        onViewAllTaskBtnClick();
    }

    private void onViewAllTaskBtnClick() {
        AjaxLink<Void> viewTaskBtn = new AjaxLink<Void>("viewAllTasks") {
            @Override
            public void onClick(AjaxRequestTarget ajaxRequestTarget) {
                setResponsePage(new ViewAllTask());
            }
        };
        add(viewTaskBtn);
    }

    private void addCreateTaskForm() {
        CreateTaskForm createTaskForm = new CreateTaskForm("createTask");
        add(createTaskForm);
    }

    private void addWelcomeMessage() {
        add(new Label("welcomeMsg", "Welcome " + LoginForm.loggedInUser));
    }

    @Override
    public void renderHead(IHeaderResponse response) {
        response.render(JavaScriptHeaderItem
                .forUrl("https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/js/bootstrap.bundle.min.js"));
        response.render(
                CssHeaderItem.forUrl("https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/css/bootstrap.min.css"));
    }
}
