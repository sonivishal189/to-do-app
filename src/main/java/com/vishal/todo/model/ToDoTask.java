package com.vishal.todo.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;

@Data
@NoArgsConstructor
//@AllArgsConstructor
@Entity
@Table(name = "TO_DO_TASK")
public class ToDoTask implements Serializable {

    @Id
    @GeneratedValue
    @Column(name = "ID")
    private int id;

    @Column(name = "TASK")
    private String task;

    @Column(name = "EMP_NAME")
    private String empName;

    @Column(name = "BUILDING_NAME")
    private String buildingName;

    @Column(name = "STATUS")
    private String status;

    @Column(name = "CREATED_BY")
    private String createdBy;

    public ToDoTask(String task, String empName, String buildingName, String status, String createdBy) {
        this.task = task;
        this.empName = empName;
        this.buildingName = buildingName;
        this.status = status;
        this.createdBy = createdBy;
    }
}
