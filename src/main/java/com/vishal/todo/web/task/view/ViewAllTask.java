package com.vishal.todo.web.task.view;

import com.vishal.todo.model.ToDoTask;
import com.vishal.todo.service.ToDoService;
import com.vishal.todo.web.BasePage;
import lombok.extern.slf4j.Slf4j;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.markup.html.form.AjaxCheckBox;
import org.apache.wicket.markup.head.CssHeaderItem;
import org.apache.wicket.markup.head.IHeaderResponse;
import org.apache.wicket.markup.head.JavaScriptHeaderItem;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.form.Button;
import org.apache.wicket.markup.html.form.CheckBox;
import org.apache.wicket.markup.html.form.CheckBoxMultipleChoice;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.list.ListItem;
import org.apache.wicket.markup.html.list.ListView;
import org.apache.wicket.model.Model;

import java.util.ArrayList;
import java.util.List;

@Slf4j
public class ViewAllTask extends BasePage {

    public static final ToDoService service = new ToDoService();
    private List<ToDoTask> allTasks = new ArrayList<>();
    private List<ToDoTask> selectedTasks;

    public ViewAllTask() {

        selectedTasks = new ArrayList<>();

        allTasks = service.getAllTasks();

        Form<Void> form = new Form<>("form");
        add(form);

        ListView<ToDoTask> todoList = new ListView<ToDoTask>("todoList", allTasks) {
            @Override
            protected void populateItem(ListItem<ToDoTask> listItem) {

                /*CheckBoxMultipleChoice myCheck = new CheckBoxMultipleChoice("transport", new Model(chosen), choices));*/

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
//                listItem.add(new Label("status", () -> listItem.getModelObject().getStatus()));
//                listItem.add(new Label("createdBy", () -> listItem.getModelObject().getCreatedBy()));
            }
        };
        form.add(todoList);
        form.add(new Button("deleteButton") {
            @Override
            public void onSubmit() {
                log.info("Selected tasks {}", selectedTasks);
                for (ToDoTask selTask : selectedTasks) {
                    service.deleteTask(selTask);
                }
                setResponsePage(ViewAllTask.class);
            }
        });
    }

    @Override
    public void renderHead(IHeaderResponse response) {
        response.render(JavaScriptHeaderItem
                .forUrl("https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/js/bootstrap.bundle.min.js"));
        response.render(
                CssHeaderItem.forUrl("https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/css/bootstrap.min.css"));
    }
}
