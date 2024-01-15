package com.vishal.todo.web.task.create;

import com.vishal.todo.model.ToDoTask;
import com.vishal.todo.service.ToDoService;
import com.vishal.todo.web.login.LoginForm;
import com.vishal.todo.web.task.view.ViewAllTask;
import lombok.extern.slf4j.Slf4j;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.model.Model;

import java.util.Date;

@Slf4j
public class CreateTaskForm extends Form<Model<String>> {
    private static final ToDoService service = new ToDoService();
    private TextField<String> task;
    private TextField<String> empName;
    private TextField<String> buildingName;

    public CreateTaskForm(String id) {
        super(id);

        task = new TextField<String>("task", Model.of(""));
        empName = new TextField<String>("empName", Model.of(""));
        buildingName = new TextField<String>("buildingName", Model.of(""));

        add(task);
        add(empName);
        add(buildingName);
    }

    public final void onSubmit() {
        String taskMsgVal = (String) task.getDefaultModelObject();
        String assignedToVal = (String) empName.getDefaultModelObject();
        String buildingNameVal = (String) buildingName.getDefaultModelObject();

        ToDoTask toDoTask = new ToDoTask();
        toDoTask.setTask(taskMsgVal);
        toDoTask.setEmpName(assignedToVal);
        toDoTask.setBuildingName(buildingNameVal);
        toDoTask.setCreatedBy(LoginForm.loggedInUser);
        toDoTask.setCreatedOn(new Date());

        log.info("Create new task {}", toDoTask);
        service.saveOrUpdate(toDoTask);

        task.setDefaultModelObject("");
        empName.setDefaultModelObject("");
        buildingName.setDefaultModelObject("");

        setResponsePage(ViewAllTask.class);
    }
}
