package com.vishal.todo.web.task.edit;

import com.vishal.todo.model.ToDoTask;
import com.vishal.todo.service.ToDoService;
import com.vishal.todo.util.Constants;
import com.vishal.todo.web.login.LoginForm;
import com.vishal.todo.web.task.view.ViewAllTask;
import lombok.extern.slf4j.Slf4j;
import org.apache.wicket.markup.html.form.DropDownChoice;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.model.Model;

import java.util.Date;
import java.util.List;

@Slf4j
public class EditTaskForm extends Form<Model<String>> {
    private static final ToDoService service = new ToDoService();
    private TextField<String> editTask;
    private DropDownChoice<String> empDropdownChoice;
    private DropDownChoice<String> buildingDropdownChoice;
    private DropDownChoice<String> statusDropdownChoice;
    private static ToDoTask taskToEdit;

    public EditTaskForm(String id, int taskId) {
        super(id);
        log.info("Edit task {} for form {}", taskId, id);

        taskToEdit = service.getTaskById(taskId);
        List<String> statusList = List.of(Constants.ASSIGNED, Constants.IN_PROGRESS, Constants.ON_HOLD, Constants.COMPLETED);

        editTask = new TextField<String>("task", Model.of(taskToEdit.getTask()));
        empDropdownChoice = new DropDownChoice<>("empName", Model.of(taskToEdit.getEmpName()), Constants.empNameList);
        buildingDropdownChoice = new DropDownChoice<>("buildingName", Model.of(taskToEdit.getBuildingName()), Constants.buildingList);
        statusDropdownChoice = new DropDownChoice<>("status", Model.of(taskToEdit.getStatus()), statusList);

        add(empDropdownChoice);
        add(buildingDropdownChoice);
        add(statusDropdownChoice);
        add(editTask);
    }

    public final void onSubmit() {
        String taskMsgVal = (String) editTask.getDefaultModelObject();
        String assignedToVal = (String) empDropdownChoice.getDefaultModelObject();
        String buildingNameVal = (String) buildingDropdownChoice.getDefaultModelObject();
        String statusVal = (String) statusDropdownChoice.getDefaultModelObject();

        taskToEdit.setTask(taskMsgVal);
        taskToEdit.setEmpName(assignedToVal);
        taskToEdit.setBuildingName(buildingNameVal);
        if (null != assignedToVal && null == statusVal) {
            taskToEdit.setStatus(Constants.ASSIGNED);
        }
        taskToEdit.setStatus(statusVal);
        taskToEdit.setUpdatedBy(LoginForm.loggedInUser);
        taskToEdit.setUpdatedOn(new Date());

        log.info("Got Request to update task {}", taskToEdit);
        service.saveOrUpdate(taskToEdit);

        setResponsePage(ViewAllTask.class);
    }
}
