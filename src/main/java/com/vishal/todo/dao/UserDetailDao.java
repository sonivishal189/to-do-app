package com.vishal.todo.dao;

import com.vishal.todo.model.UserDetail;
import com.vishal.todo.util.HibernateUtil;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.Session;
import org.hibernate.Transaction;

@Slf4j
public class UserDetailDao {

    public void save(UserDetail userDetail) {
        Transaction transaction = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            session.saveOrUpdate(userDetail);
            transaction.commit();
            log.info("New User created successfully...");
        } catch (Exception exp) {
            log.error("Error while user creation: {}", exp.getMessage());
            if (null != transaction) {
                transaction.rollback();
            }
        }
    }

    public UserDetail findById(String userId) {
        UserDetail userDetailInDb = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            userDetailInDb = session.byId(UserDetail.class).getReference(userId);
            log.info("Used fetched from DB is: {}", userDetailInDb);
        } catch (Exception exp) {
            log.error("Error while getting user: {}", exp.getMessage());
        }
        return userDetailInDb;
    }
}
