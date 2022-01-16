package com.example.esa_lab1.dao;

import com.example.esa_lab1.models.Dancer;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import java.util.List;

@Stateless

public class DancerDaoImpl implements DancerDao{
    @PersistenceContext(unitName = "default")
    private EntityManager entMan;

    @Override
    public Dancer get(Integer id){
        return entMan.find(Dancer.class, id);
    }

    @Override
    public List<Dancer> getAll(){
        TypedQuery<Dancer> getAllQuery = entMan.createQuery("select distinct c from Dancer c", Dancer.class);
        List<Dancer> result = getAllQuery.getResultList();
        return result;
    }

    @Override
    public void save(Dancer dancer) {
        entMan.persist(dancer);
    }

    @Override
    public void update(Dancer dancer) {
        entMan.merge(dancer);
    }

    @Override
    public void delete(Dancer dancer) {
        entMan.remove(entMan.contains(dancer) ? dancer : entMan.merge(dancer));
    }

}
