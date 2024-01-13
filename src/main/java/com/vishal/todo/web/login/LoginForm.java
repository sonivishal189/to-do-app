package com.vishal.todo.web.login;

import com.vishal.todo.web.home.HomePage;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.PasswordTextField;
import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.model.Model;

public class LoginForm extends Form<Model<String>> {
    public static String loggedInUser = "";
    private TextField<String> usernameField;
    private PasswordTextField passwordField;
    private Label loginStatus;

    public LoginForm(String id) {
        super(id);

        usernameField = new TextField<String>("username", Model.of(""));
        passwordField = new PasswordTextField("password", Model.of(""));
        loginStatus = new Label("loginStatus", Model.of(""));

        add(usernameField);
        add(passwordField);
        add(loginStatus);
    }

    public final void onSubmit() {
        String username = (String) usernameField.getDefaultModelObject();
        String password = (String) passwordField.getDefaultModelObject();

        if (username.equals("v") && password.equals("v")) {
            loggedInUser = username;
            setResponsePage(HomePage.class);
        } else {
            loginStatus.setDefaultModelObject("Wrong username or password!");
            usernameField.setDefaultModelObject("");
            add(usernameField);
        }
    }
}
