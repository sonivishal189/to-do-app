package com.vishal.todo.web.task.view;

import com.vishal.todo.model.ToDoTask;
import com.vishal.todo.service.ToDoService;
import com.vishal.todo.util.Constants;
import com.vishal.todo.web.BasePage;
import com.vishal.todo.web.login.LoginForm;
import com.vishal.todo.web.login.LoginPage;
import com.vishal.todo.web.task.create.CreateTaskForm;
import com.vishal.todo.web.task.edit.EditTaskPage;
import lombok.extern.slf4j.Slf4j;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.markup.html.form.AjaxCheckBox;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.form.Button;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.list.ListItem;
import org.apache.wicket.markup.html.list.ListView;
import org.apache.wicket.model.Model;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
public class ViewAllTask extends BasePage {

    private static final ToDoService service = new ToDoService();
    private List<ToDoTask> allTasks = new ArrayList<>();
    private List<ToDoTask> selectedTasks;

    Label viewPageMsgLabel = new Label("viewPageMsg", "");

    public ViewAllTask() {

        if (LoginForm.loggedInUser.isBlank() || LoginForm.loggedInUser.equals("LoggedOut")) {
            setResponsePage(new LoginPage("Please login to proceed."));
            return;
        }

        selectedTasks = new ArrayList<>();
        allTasks = service.getAllTasks();
        selectedTasks.clear();

        Form<Void> form = new Form<>("form");
        add(form);
        add(viewPageMsgLabel);

        loadTasksByStatus();

        viewSelectedTasks(form);

        deleteSelectedTasks(form);

        editSelectedTask(form);

        addCreateTaskForm();
    }

    private void loadTasksByStatus() {
        add(new Label("totalTaskCount", allTasks.size()));

        Form<Void> newTaskForm = new Form<>("newTask");
        add(newTaskForm);
        List<ToDoTask> newTaskList = getTaskList(Constants.NEW);
        loadTasksToView(newTaskForm, "newTaskList", newTaskList);
        add(new Label("newTaskCount", newTaskList.size()));

        Form<Void> assignedTaskForm = new Form<>("assignedTask");
        add(assignedTaskForm);
        List<ToDoTask> assignedTaskList = getTaskList(Constants.ASSIGNED);
        loadTasksToView(assignedTaskForm, "assignedTaskList", assignedTaskList);
        add(new Label("assignedTaskCount", assignedTaskList.size()));

        Form<Void> inProgressTaskForm = new Form<>("inProgress");
        add(inProgressTaskForm);
        List<ToDoTask> inProgressTaskList = getTaskList(Constants.IN_PROGRESS);
        loadTasksToView(inProgressTaskForm, "inProgressList", inProgressTaskList);
        add(new Label("inProgressTaskCount", inProgressTaskList.size()));

        Form<Void> completedTaskForm = new Form<>("completed");
        add(completedTaskForm);
        List<ToDoTask> completedTaskList = getTaskList(Constants.COMPLETED);
        loadTasksToView(completedTaskForm, "completedList", completedTaskList);
        add(new Label("completedTaskCount", completedTaskList.size()));

        Form<Void> onHoldTaskForm = new Form<>("onHold");
        add(onHoldTaskForm);
        List<ToDoTask> onHoldTaskList = getTaskList(Constants.ON_HOLD);
        loadTasksToView(onHoldTaskForm, "onHoldList", onHoldTaskList);
        add(new Label("onHoldTaskCount", onHoldTaskList.size()));
    }

    private List<ToDoTask> getTaskList(String status) {
        return allTasks.stream().filter(task -> task.getStatus().equals(status)).collect(Collectors.toList());
    }

    private void addCreateTaskForm() {
        CreateTaskForm createTaskForm = new CreateTaskForm("createTask");
        add(createTaskForm);
    }

    private void editSelectedTask(Form<Void> form) {
        form.add(new Button("editTaskButton") {
            @Override
            public void onSubmit() {
                viewPageMsgLabel.setDefaultModel(Model.of(""));
                log.info("Selected tasks for Edit {}", selectedTasks);
                if (null == selectedTasks || selectedTasks.isEmpty()) {
                    viewPageMsgLabel.setDefaultModel(Model.of("Select a Task to Edit"));
                    return;
                }
                if (selectedTasks.size() > 1) {
                    viewPageMsgLabel.setDefaultModel(Model.of("You cannot Edit more than 1 task at a time."));
                    selectedTasks.clear();
                    return;
                }
                EditTaskPage.taskId = selectedTasks.get(0).getId();
                setResponsePage(EditTaskPage.class);
                viewPageMsgLabel.setDefaultModel(Model.of(""));
                selectedTasks.clear();
            }
        });
    }

    private void viewSelectedTasks(Form<Void> form) {
        List<Integer> selectedTaskIds = new ArrayList<>();
        form.add(new Button("viewTaskButton") {
            @Override
            public void onSubmit() {
                viewPageMsgLabel.setDefaultModel(Model.of(""));
                log.info("Selected tasks for Edit {}", selectedTasks);
                if (null == selectedTasks || selectedTasks.isEmpty()) {
                    viewPageMsgLabel.setDefaultModel(Model.of("Select 1 or more Task to View"));
                    return;
                }
                for (ToDoTask selectedTask : selectedTasks) {
                    selectedTaskIds.add(selectedTask.getId());
                }
                ViewSelectedTask.selectedTaskId.addAll(selectedTaskIds);
                viewPageMsgLabel.setDefaultModel(Model.of(""));
                selectedTasks.clear();
                setResponsePage(ViewSelectedTask.class);
            }
        });
    }

    private void deleteSelectedTasks(Form<Void> form) {
        Button deleteTaskBtn = new Button("deleteTaskButton") {
            @Override
            public void onSubmit() {
                log.info("Selected tasks for Delete {}", selectedTasks);
                if (null == selectedTasks || selectedTasks.isEmpty()) {
                    viewPageMsgLabel.setDefaultModel(Model.of("Select 1 or more Task to Delete"));
                    return;
                }
                for (ToDoTask selTask : selectedTasks) {
                    service.deleteTask(selTask);
                }
                selectedTasks.clear();
                setResponsePage(ViewAllTask.class);
                viewPageMsgLabel.setDefaultModel(Model.of(""));
            }
        };
        form.add(deleteTaskBtn);
    }

    private void loadTasksToView(Form<Void> form, String taskListName, List<ToDoTask> taskList) {
        ListView<ToDoTask> todoList = new ListView<ToDoTask>(taskListName, taskList) {
            @Override
            protected void populateItem(ListItem<ToDoTask> listItem) {

                ToDoTask toDoTask = listItem.getModelObject();
                listItem.add(new AjaxCheckBox("selected", Model.of(false)) {
                    @Override
                    protected void onUpdate(AjaxRequestTarget ajaxRequestTarget) {
                        log.info("Task clicked {}", toDoTask);
                        if (selectedTasks.contains(toDoTask)) {
                            selectedTasks.remove(toDoTask);
                        } else {
                            selectedTasks.add(toDoTask);
                        }
                    }
                });
                listItem.add(new Label("id", toDoTask.getId()));
                listItem.add(new Label("task", toDoTask.getTask()));
                listItem.add(new Label("empName", toDoTask.getEmpName()));
                listItem.add(new Label("buildingName", toDoTask.getBuildingName()));
            }
        };
        form.add(todoList);
    }
}
