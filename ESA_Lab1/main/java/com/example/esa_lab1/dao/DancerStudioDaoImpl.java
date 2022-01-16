package com.example.esa_lab1.dao;

import com.example.esa_lab1.models.DancerStudio;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import java.util.List;

@Stateless
public class DancerStudioDaoImpl implements DancerStudioDao{
    @PersistenceContext(unitName = "default")
    private EntityManager entMan;

    @Override
    public DancerStudio get(Integer id) {
        return entMan.find(DancerStudio.class, id);
    }

    @Override
    public List<DancerStudio> getAll() {
        TypedQuery<DancerStudio> getAllQuery = entMan.createQuery("select d from DancerStudio d", DancerStudio.class);
        List<DancerStudio> result = getAllQuery.getResultList();
        return result;
    }

    @Override
    public void save(DancerStudio dancerStudio) {
        entMan.persist(dancerStudio);
    }

    @Override
    public void update(DancerStudio dancerStudio) {
        entMan.merge(dancerStudio);
    }

    @Override
    public void delete(DancerStudio dancerStudio) {
        entMan.remove(entMan.contains(dancerStudio) ? dancerStudio : entMan.merge(dancerStudio));
    }
}
