package com.vishal.todo.web.task.create;

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
import org.apache.wicket.model.PropertyModel;

import java.util.Date;

@Slf4j
public class CreateTaskForm extends Form<Model<String>> {
    private static final ToDoService service = new ToDoService();
    private TextField<String> task;
    private DropDownChoice<String> empDropdownChoice;
    private DropDownChoice<String> buildingDropdownChoice;

    public CreateTaskForm(String id) {
        super(id);

        ToDoTask toDoTask = new ToDoTask();

        task = new TextField<String>("task", Model.of(""));

        empDropdownChoice = new DropDownChoice<>("empName", new PropertyModel<String>(toDoTask, "empName"), Constants.empNameList);

        buildingDropdownChoice = new DropDownChoice<>("buildingName", new PropertyModel<String>(toDoTask, "buildingName"), Constants.buildingList);

        add(empDropdownChoice);
        add(buildingDropdownChoice);
        add(task);
    }

    public final void onSubmit() {
        String taskMsgVal = (String) task.getDefaultModelObject();
        String assignedToVal = (String) empDropdownChoice.getDefaultModelObject();
        String buildingNameVal = (String) buildingDropdownChoice.getDefaultModelObject();

        ToDoTask toDoTask = createToDoTask(taskMsgVal, assignedToVal, buildingNameVal);

        log.info("Create new task {}", toDoTask);
        service.saveOrUpdate(toDoTask);

        task.setDefaultModelObject("");

        setResponsePage(ViewAllTask.class);
    }

    private ToDoTask createToDoTask(String taskMsgVal, String assignedToVal, String buildingNameVal) {
        ToDoTask toDoTask = new ToDoTask();
        toDoTask.setTask(taskMsgVal);
        if (null == assignedToVal) {
            toDoTask.setStatus(Constants.NEW);
        } else {
            toDoTask.setEmpName(assignedToVal);
            toDoTask.setStatus(Constants.ASSIGNED);
        }
        toDoTask.setBuildingName(buildingNameVal);
        toDoTask.setCreatedBy(LoginForm.loggedInUser);
        toDoTask.setCreatedOn(new Date());
        return toDoTask;
    }
}
