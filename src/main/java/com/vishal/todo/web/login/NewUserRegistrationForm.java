package com.vishal.todo.web.login;

import com.vishal.todo.model.UserDetail;
import com.vishal.todo.service.UserDetailService;
import org.apache.wicket.markup.html.form.EmailTextField;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.PasswordTextField;
import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.model.Model;

public class NewUserRegistrationForm extends Form<Model<String>> {

    private static final UserDetailService USER_DETAIL_SERVICE = new UserDetailService();
    private EmailTextField userEmail;
    private TextField<String> firstName;
    private TextField<String> lastName;
    private PasswordTextField userPassword;

    public NewUserRegistrationForm(String id) {
        super(id);

        userEmail = new EmailTextField("userEmail", Model.of(""));
        firstName = new TextField<String>("firstName", Model.of(""));
        lastName = new TextField<String>("lastName", Model.of(""));
        userPassword = new PasswordTextField("userPassword", Model.of(""));

        add(userEmail);
        add(firstName);
        add(lastName);
        add(userPassword);
    }

    public final void onSubmit() {
        String userEmailVal = (String) userEmail.getDefaultModelObject();
        String firstNameVal = (String) firstName.getDefaultModelObject();
        String lastNameVal = (String) lastName.getDefaultModelObject();
        String passVal = (String) userPassword.getDefaultModelObject();

        UserDetail userDetail = new UserDetail(userEmailVal, firstNameVal, lastNameVal, passVal);
        USER_DETAIL_SERVICE.createUser(userDetail);

        userEmail.setDefaultModelObject("");
        firstName.setDefaultModelObject("");
        lastName.setDefaultModelObject("");
        userPassword.setDefaultModelObject("");

        setResponsePage(new LoginPage("User added successfully."));
    }
}
