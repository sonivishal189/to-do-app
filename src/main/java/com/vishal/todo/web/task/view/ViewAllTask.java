package com.vishal.todo.web.task.view;

import com.vishal.todo.model.ToDoTask;
import com.vishal.todo.service.ToDoService;
import com.vishal.todo.web.BasePage;
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

@Slf4j
public class ViewAllTask extends BasePage {

    private static final ToDoService service = new ToDoService();
    private List<ToDoTask> allTasks = new ArrayList<>();
    private List<ToDoTask> selectedTasks;

    Label viewPageMsgLabel = new Label("viewPageMsg", "");

    public ViewAllTask() {

//        Label selectedTaskDetail = new Label("taskDetail", "");

        selectedTasks = new ArrayList<>();
        allTasks = service.getAllTasks();
        selectedTasks.clear();

        Form<Void> form = new Form<>("form");
        add(form);
        form.add(viewPageMsgLabel);
//        form.add(selectedTaskDetail);

        loadAllTaskToView(form);

        viewSelectedTasks(form);

        deleteSelectedTasks(form);

        editSelectedTask(form);

        addCreateTaskForm(form);
    }

    private void addCreateTaskForm(Form<Void> form) {
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

    private void loadAllTaskToView(Form<Void> form) {
        ListView<ToDoTask> todoList = new ListView<ToDoTask>("todoList", allTasks) {
            @Override
            protected void populateItem(ListItem<ToDoTask> listItem) {

                ToDoTask toDoTask = listItem.getModelObject();
                listItem.add(new AjaxCheckBox("selected", Model.of(false)) {
                    @Override
                    protected void onUpdate(AjaxRequestTarget ajaxRequestTarget) {
                        log.info("update happened {}", toDoTask);
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
