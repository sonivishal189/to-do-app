package com.vishal.todo.web.task.edit;

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
public class EditTaskForm extends Form<Model<String>> {
    private static final ToDoService service = new ToDoService();
    private TextField<String> editTask;
    private TextField<String> editEmpName;
    private TextField<String> editBuildingName;

    private static int taskIdToEdit;

    public EditTaskForm(String id, int taskId) {
        super(id);
        log.info("Edit task {} for form {}", taskId, id);
        taskIdToEdit = taskId;

        ToDoTask taskToEdit = service.getTaskById(taskId);
        editTask = new TextField<String>("editTask", Model.of(taskToEdit.getTask()));
        editEmpName = new TextField<String>("editEmpName", Model.of(taskToEdit.getEmpName()));
        editBuildingName = new TextField<String>("editBuildingName", Model.of(taskToEdit.getBuildingName()));

        log.info("Add text fields to page");
        add(editTask);
        add(editEmpName);
        add(editBuildingName);
    }

    public final void onSubmit() {
        String taskMsgVal = (String) editTask.getDefaultModelObject();
        String assignedToVal = (String) editEmpName.getDefaultModelObject();
        String buildingNameVal = (String) editBuildingName.getDefaultModelObject();

        ToDoTask toDoTask = new ToDoTask();
        toDoTask.setId(taskIdToEdit);
        toDoTask.setTask(taskMsgVal);
        toDoTask.setEmpName(assignedToVal);
        toDoTask.setBuildingName(buildingNameVal);
        toDoTask.setUpdatedBy(LoginForm.loggedInUser);
        toDoTask.setUpdatedOn(new Date());

        log.info("Got Request to update task {}", toDoTask);
        service.saveOrUpdate(toDoTask);

        editTask.setDefaultModelObject("");
        editEmpName.setDefaultModelObject("");
        editBuildingName.setDefaultModelObject("");
        setResponsePage(ViewAllTask.class);
    }
}
