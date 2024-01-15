package com.vishal.todo.web.task.edit;

import com.vishal.todo.model.ToDoTask;
import com.vishal.todo.service.ToDoService;
import com.vishal.todo.util.TaskStatus;
import com.vishal.todo.web.login.LoginForm;
import com.vishal.todo.web.task.view.ViewAllTask;
import lombok.extern.slf4j.Slf4j;
import org.apache.wicket.markup.html.form.DropDownChoice;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.model.Model;
import org.apache.wicket.model.PropertyModel;

import java.util.Date;
import java.util.List;

@Slf4j
public class EditTaskForm extends Form<Model<String>> {
    private static final ToDoService service = new ToDoService();
    private TextField<String> editTask;
    private TextField<String> editEmpName;
    private TextField<String> editBuildingName;

    private DropDownChoice<String> statusDropdownChoice;
    private static ToDoTask taskToEdit;

    public EditTaskForm(String id, int taskId) {
        super(id);
        log.info("Edit task {} for form {}", taskId, id);

        taskToEdit = service.getTaskById(taskId);
        editTask = new TextField<String>("editTask", Model.of(taskToEdit.getTask()));
        editEmpName = new TextField<String>("editEmpName", Model.of(taskToEdit.getEmpName()));
        editBuildingName = new TextField<String>("editBuildingName", Model.of(taskToEdit.getBuildingName()));

        ToDoTask toDoTask = new ToDoTask();
        List<String> statusList = List.of(TaskStatus.IN_PROGRESS, TaskStatus.ON_HOLD, TaskStatus.COMPLETED);
        statusDropdownChoice = new DropDownChoice<>("status", new PropertyModel<String>(toDoTask, "status"), statusList);
        add(statusDropdownChoice);
        statusDropdownChoice.setLabel(Model.of("Select Status"));

        log.info("Add text fields to page");
        add(editTask);
        add(editEmpName);
        add(editBuildingName);
    }

    public final void onSubmit() {
        String taskMsgVal = (String) editTask.getDefaultModelObject();
        String assignedToVal = (String) editEmpName.getDefaultModelObject();
        String buildingNameVal = (String) editBuildingName.getDefaultModelObject();
        String statusVal = (String) statusDropdownChoice.getDefaultModelObject();

        taskToEdit.setTask(taskMsgVal);
        taskToEdit.setEmpName(assignedToVal);
        taskToEdit.setBuildingName(buildingNameVal);
        if (null != assignedToVal && null == statusVal) {
            taskToEdit.setStatus(TaskStatus.ASSIGNED);
        }
        taskToEdit.setStatus(statusVal);
        taskToEdit.setUpdatedBy(LoginForm.loggedInUser);
        taskToEdit.setUpdatedOn(new Date());

        log.info("Got Request to update task {}", taskToEdit);
        service.saveOrUpdate(taskToEdit);

        editTask.setDefaultModelObject("");
        editEmpName.setDefaultModelObject("");
        editBuildingName.setDefaultModelObject("");
        setResponsePage(ViewAllTask.class);
    }
}
