package com.vishal.todo.web.task.view;

import com.vishal.todo.model.ToDoTask;
import com.vishal.todo.service.ToDoService;
import com.vishal.todo.web.BasePage;
import com.vishal.todo.web.login.LoginForm;
import com.vishal.todo.web.login.LoginPage;
import lombok.extern.slf4j.Slf4j;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.markup.html.AjaxLink;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.list.ListItem;
import org.apache.wicket.markup.html.list.ListView;

import java.util.ArrayList;
import java.util.List;

@Slf4j
public class ViewSelectedTask extends BasePage {
    private static final ToDoService service = new ToDoService();
    public static List<Integer> selectedTaskId = new ArrayList<>();

    public ViewSelectedTask() {

        if (LoginForm.loggedInUser.equals("LoggedOut")) {
            setResponsePage(new LoginPage("Please login to proceed."));
            return;
        }

        List<ToDoTask> selectedTaskToView = new ArrayList<>();

        for (int taskId : selectedTaskId) {
            selectedTaskToView.add(service.getTaskById(taskId));
        }
        selectedTaskId.clear();

        ListView<ToDoTask> selectedViewList = new ListView<ToDoTask>("selectedTaskViewList", selectedTaskToView) {
            @Override
            protected void populateItem(ListItem<ToDoTask> listItem) {
                ToDoTask toDoTask = listItem.getModelObject();
                listItem.add(new Label("id", toDoTask.getId()));
                listItem.add(new Label("task", toDoTask.getTask()));
                listItem.add(new Label("empName", toDoTask.getEmpName()));
                listItem.add(new Label("buildingName", toDoTask.getBuildingName()));
                listItem.add(new Label("status", toDoTask.getStatus()));
                listItem.add(new Label("createdBy", toDoTask.getCreatedBy()));
                listItem.add(new Label("createdOn", toDoTask.getCreatedOn()));
                listItem.add(new Label("updatedBy", toDoTask.getCreatedBy()));
                listItem.add(new Label("updatedOn", toDoTask.getUpdatedOn()));
            }
        };
        add(selectedViewList);

        add(new AjaxLink<Void>("backToViewAllTasks") {
            @Override
            public void onClick(AjaxRequestTarget ajaxRequestTarget) {
                log.info("Back button pressed........");
                setResponsePage(ViewAllTask.class);
            }
        });
    }
}
