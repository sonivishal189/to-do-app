package com.vishal.todo.web.task.edit;

import com.vishal.todo.web.BasePage;
import com.vishal.todo.web.task.view.ViewAllTask;
import lombok.extern.slf4j.Slf4j;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.markup.html.AjaxLink;
import org.apache.wicket.markup.html.basic.Label;

@Slf4j
public class EditTaskPage extends BasePage {

    public static int taskId;

    public EditTaskPage() {
        log.info("Edit task execution starts.......");
        EditTaskForm editTaskForm = new EditTaskForm("editSelectedTask", taskId);
        add(editTaskForm);
        editTaskForm.add(new AjaxLink<Void>("cancelEdit") {
            @Override
            public void onClick(AjaxRequestTarget ajaxRequestTarget) {
                log.info("Edit cancelled ... ");
                setResponsePage(ViewAllTask.class);
            }
        });

        add(new Label("editTaskMsg", "Edit Task with ID: " + taskId));
    }
}
