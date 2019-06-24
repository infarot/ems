package com.dawid.ems.dao;

import com.dawid.ems.entity.Result;
import com.dawid.ems.entity.Seamstress;
import com.dawid.ems.exception.SeamstressNotFoundException;
import org.hibernate.Session;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

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
        return query.getResultList();
    }

    @Override
    public Optional<Seamstress> getSingle(int id) {
        Session session = entityManager.unwrap(Session.class);
        return Optional.ofNullable(session.get(Seamstress.class, id));
    }


    @Override
    public List<Result> getAllResultsFromDateInterval(int seamstressId, LocalDate from, LocalDate to) {
        Session session = entityManager.unwrap(Session.class);
        Query<Result> query = session.createQuery("from Result r where r.seamstress=:id and r.date between :from and :to");
        Seamstress seamstress = getSingle(seamstressId).orElseThrow(() -> new SeamstressNotFoundException("Seamstress with id: " + seamstressId + "not found"));
        query.setParameter("id", seamstress);
        query.setParameter("from", from);
        query.setParameter("to", to);
        return query.getResultList();
    }

    @Override
    public int save(Seamstress seamstress) {
        Session session = entityManager.unwrap(Session.class);
        return (int) session.save(seamstress);
    }

}
