package com.vishal.todo.web.task.edit;

import com.vishal.todo.web.BasePage;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class EditTaskPage extends BasePage {

    public static int taskId;
    public EditTaskPage() {
        log.info("Edit task execution starts.......");
        EditTaskForm editTaskForm = new EditTaskForm("editSelectedTask", taskId);
        add(editTaskForm);
    }
}
