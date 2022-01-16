package com.example.esa_lab1.dao;

import com.example.esa_lab1.models.DancerClass;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import java.util.List;

@Stateless
public class DancerClassDaoImpl implements DancerClassDao {
    @PersistenceContext(unitName = "default")
    private EntityManager entMan;

    @Override
    public DancerClass get(Integer id) {
        return entMan.find(DancerClass.class, id);
    }

    @Override
    public List<DancerClass> getAll() {
        TypedQuery<DancerClass> getAllQuery = entMan.createQuery("select d from DancerClass d", DancerClass.class);
        List<DancerClass> result = getAllQuery.getResultList();
        return result;
    }

    @Override
    public void save(DancerClass dancerClass) {
        entMan.persist(dancerClass);
    }

    @Override
    public void update(DancerClass dancerClass) {
        entMan.merge(dancerClass);
    }

    @Override
    public void delete(DancerClass dancerClass) {
        entMan.remove(entMan.contains(dancerClass) ? dancerClass : entMan.merge(dancerClass));
    }
}

