package com.vishal.todo.web.login;

import com.vishal.todo.model.UserDetail;
import com.vishal.todo.service.UserDetailService;
import com.vishal.todo.web.home.HomePage;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.form.EmailTextField;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.PasswordTextField;
import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.model.Model;

public class LoginForm extends Form<Model<String>> {
    public static String loggedInUser = "";
    private EmailTextField usernameField;
    private PasswordTextField passwordField;
    private Label loginStatus;

    private static UserDetailService userDetailService = new UserDetailService();

    public LoginForm(String id) {
        super(id);

        usernameField = new EmailTextField("username", Model.of(""));
        passwordField = new PasswordTextField("password", Model.of(""));
        loginStatus = new Label("loginStatus", Model.of(""));

        add(usernameField);
        add(passwordField);
        add(loginStatus);
    }

    public final void onSubmit() {
        String username = (String) usernameField.getDefaultModelObject();
        String password = (String) passwordField.getDefaultModelObject();

        try {
            UserDetail userDetail = userDetailService.getUserById(username);

            if (password.equals(userDetail.getPassword())) {
                loggedInUser = userDetail.getFirstName() + " " + userDetail.getLastName();
                setResponsePage(HomePage.class);
            } else {
                loginStatus.setDefaultModelObject("Wrong username or password!");
                usernameField.setDefaultModelObject("");
                add(usernameField);
            }
        } catch (Exception exp) {
            loginStatus.setDefaultModelObject("Invalid Username " + username);
            usernameField.setDefaultModelObject("");
        }
    }
}
