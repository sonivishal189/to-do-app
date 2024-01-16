package com.vishal.todo.web.home;

import com.vishal.todo.web.BasePage;
import com.vishal.todo.web.login.LoginForm;
import com.vishal.todo.web.login.LoginPage;
import com.vishal.todo.web.task.view.ViewAllTask;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.markup.html.AjaxLink;
import org.apache.wicket.markup.html.basic.Label;

public class HomePage extends BasePage {
    private static final long serialVersionUID = 1L;

    public HomePage() {

        if (LoginForm.loggedInUser.isBlank() || LoginForm.loggedInUser.equals("LoggedOut")) {
            setResponsePage(new LoginPage("Please login to proceed."));
            return;
        }

        addWelcomeMessage();
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

    private void addWelcomeMessage() {
        add(new Label("welcomeMsg", "Hi " + LoginForm.loggedInUser + "! Welcome in To-Do Application."));
    }
}
