package com.dawid.ems.dao;

import com.dawid.ems.entity.User;
import org.hibernate.Session;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;

@Repository
public class UserDAOImpl implements UserDAO {

    private final EntityManager entityManager;

    @Autowired
    public UserDAOImpl(EntityManager entityManager){
        this.entityManager = entityManager;
    }

    @Override
    public void addUser(User user) {
        Session session = entityManager.unwrap(Session.class);
        session.saveOrUpdate(user);

    }

    @Override
    public User findByUsername(String name) {
        Session session = entityManager.unwrap(Session.class);
        Query<User> query = session.createQuery("from User where username=:name", User.class);
        query.setParameter("name", name);
        return query.getSingleResult();
    }
}
