package com.vishal.todo.dao;

import com.vishal.todo.model.ToDoTask;
import com.vishal.todo.util.HibernateUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.wicket.util.thread.Task;
import org.hibernate.Session;
import org.hibernate.Transaction;

import javax.persistence.Query;

@Slf4j
public class ToDoTaskDao {

    public ToDoTask save(ToDoTask task) {
        log.info("Task to create is {}", task);
        Transaction transaction = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            session.persist(task);
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

    public ToDoTask getTaskById(String taskId) {
        log.info("Fetch task for id: {}", taskId);
        ToDoTask taskInDb = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            String hql = "FROM ToDoList t where t.id= :taskId";
            Query query = session.createQuery(hql, ToDoTask.class);
            query.setParameter("taskId", taskId);
            taskInDb = (ToDoTask) query.getSingleResult();
            log.info("Task fetched from DB is: {}", taskInDb);
        } catch (Exception exp) {
            log.error("Error while saving task: {}", exp.getMessage());
        }
        return taskInDb;
    }


    public int deleteAll() {
        log.info("Delete all records");
        Transaction transaction = null;
        int count = 0;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            transaction = session.getTransaction();
            String hql = "DELETE FROM ToDoList";
            Query query = session.createQuery(hql);
            count = query.executeUpdate();
            transaction.commit();
            log.info("Number of records deleted: {}", count);
        } catch (Exception exp) {
            log.error("Error while deleting all records {}", exp.getMessage());
            if (null != transaction) {
                transaction.rollback();
            }
        }
        return count;
    }

    public ToDoTask deleteById(String taskId) {
        log.info("Delete Task with id {}", taskId);
        Transaction transaction = null;
        ToDoTask deletedTask = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            transaction = session.getTransaction();
            deletedTask = getTaskById(taskId);
            String hql = "DELETE FROM ToDoList t where t.id= :taskId";
            Query query = session.createQuery(hql);
            query.setParameter("taskId", taskId);
            query.executeUpdate();
            transaction.commit();
            log.info("Successfully deleted task for id: {}", taskId);
        } catch (Exception exp) {
            log.error("Error while deleting task for id: {} {}", taskId, exp.getMessage());
            if (null != transaction) {
                transaction.rollback();
            }
            deletedTask = null;
        }
        return deletedTask;
    }
}
