package com.vishal.todo.dao;

import com.vishal.todo.model.ToDoTask;
import com.vishal.todo.util.HibernateUtil;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.util.List;

@Slf4j
public class ToDoTaskDao {

    public void saveOrUpdate(ToDoTask task) {
        Transaction transaction = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            session.saveOrUpdate(task);
            transaction.commit();
            log.info("New To-Do task created successfully...");
        } catch (Exception exp) {
            log.error("Error while task creation: {}", exp.getMessage());
            if (null != transaction) {
                transaction.rollback();
            }
        }
    }

    public ToDoTask getTaskById(int taskId) {
        ToDoTask taskInDb = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            taskInDb = session.byId(ToDoTask.class).getReference(taskId);
            log.info("Task fetched from DB is: {}", taskInDb);
        } catch (Exception exp) {
            log.error("Error while getting task: {}", exp.getMessage());
        }
        return taskInDb;
    }

    public List<ToDoTask> getAllTasks() {
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
