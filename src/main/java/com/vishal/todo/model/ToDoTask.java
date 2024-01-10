package com.vishal.todo.model;

import com.vishal.todo.util.TaskStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "TO_DO_TASK")
public class ToDoTask implements Serializable {

    @Id
    @Column(name = "ID")
    private String id;

    @Column(name = "TASK")
    private String task;

    @Column(name = "EMP_NAME")
    private String empName;

    @Column(name = "BUILDING_NAME")
    private String buildingName;

    @Column(name = "STATUS")
    private String status;

}
