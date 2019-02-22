package com.dawid.ems.dao;

import com.dawid.ems.entity.Result;
import com.dawid.ems.entity.Seamstress;
import org.hibernate.Session;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Repository
public class SeamstressDAOImpl implements SeamstressDAO {


    private final EntityManager entityManager;

    @Autowired
    public SeamstressDAOImpl(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @Override
    public List<Seamstress> getAll() {
        Session session = entityManager.unwrap(Session.class);
        Query<Seamstress> query = session.createQuery("from Seamstress", Seamstress.class);
        return new ArrayList<>(query.getResultList());
    }

    @Override
    public Seamstress getSingle(int id) {
        Session session = entityManager.unwrap(Session.class);
        return session.get(Seamstress.class, id);
    }

    @Override
    public List<Result> getAllResults(int id) {
        Session session = entityManager.unwrap(Session.class);
        return session.get(Seamstress.class, id).getResults();
    }

    @Override
    public List<Result> getAllResultsFromDateInterval(int seamstressId, LocalDate from, LocalDate to) {
        Session session = entityManager.unwrap(Session.class);
        Query<Result> query = session.createQuery("from Result r where r.seamstress=:id and r.date between :from and :to");
        query.setParameter("id", getSingle(seamstressId));
        query.setParameter("from", from);
        query.setParameter("to", to);
        return query.getResultList();
    }

}
