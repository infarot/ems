package com.dawid.ems.dao;

import com.dawid.ems.entity.ShiftProduction;
import org.hibernate.Session;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.util.List;
import java.util.Optional;

@Repository
public class ShiftProductionDAOImpl implements ShiftProductionDAO {

    private final EntityManager entityManager;

    @Autowired
    public ShiftProductionDAOImpl(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @Override
    public Optional<List<ShiftProduction>> getAll() {
        Session session = entityManager.unwrap(Session.class);
        Query<ShiftProduction> query = session.createQuery("from ShiftProduction", ShiftProduction.class);
        return Optional.of(query.getResultList());
    }

}
