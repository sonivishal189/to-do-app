package com.vishal.todo.dao;

import com.vishal.todo.model.ToDoTask;
import com.vishal.todo.util.HibernateUtil;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.util.List;

@Slf4j
public class ToDoTaskDao {

    public ToDoTask saveOrUpdate(ToDoTask task) {
        log.info("Task to create is {}", task);
        Transaction transaction = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            session.saveOrUpdate(task);
            transaction.commit();
        } catch (Exception exp) {
            log.error("Error while task creation: {}", exp.getMessage());
            if (null != transaction) {
                transaction.rollback();
            }
            return null;
        }
        log.info("New To-Do task created successfully...");
        return task;
    }

    public ToDoTask getTaskById(int taskId) {
        log.info("Fetch task for id: {}", taskId);
        ToDoTask taskInDb = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            taskInDb = session.byId(ToDoTask.class).getReference(taskId);
            log.info("Task fetched from DB is: {}", taskInDb);
        } catch (Exception exp) {
            log.error("Error while saving task: {}", exp.getMessage());
        }
        return taskInDb;
    }

    public List<ToDoTask> getAllTasks() {
        log.info("Fetch all tasks");
        List<ToDoTask> allTasks = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            String hql = "FROM ToDoTask";
            allTasks = session.createQuery(hql, ToDoTask.class).list();
            log.info("Task fetched from DB is: {}", allTasks);
        } catch (Exception exp) {
            log.error("Error while fetching all task: {}", exp.getMessage());
        }
        return allTasks;
    }

    public void deleteTask(ToDoTask task) {
        log.info("Delete Task {}", task);
        Transaction transaction = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            session.delete(task);
            transaction.commit();
            log.info("Successfully deleted task {}", task);
        } catch (Exception exp) {
            log.error("Error while deleting task for id: {} {}", task, exp.getMessage());
            if (null != transaction) {
                transaction.rollback();
            }
        }
    }
}
