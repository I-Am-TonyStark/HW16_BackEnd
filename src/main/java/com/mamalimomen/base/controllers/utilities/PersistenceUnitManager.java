package com.mamalimomen.base.controllers.utilities;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.util.HashMap;
import java.util.Map;

public final class PersistenceUnitManager {
    private static final Map<PersistenceUnits, EntityManagerFactory> mapper = new HashMap<>();

    private PersistenceUnitManager(){}

    public static synchronized EntityManager getEntityManager(PersistenceUnits unit) {
        EntityManagerFactory emf = mapper.get(unit);

        if (emf == null) {
            mapper.put(unit, Persistence.createEntityManagerFactory(unit.getUnitName()));
            return mapper.get(unit).createEntityManager();
        } else return emf.createEntityManager();
    }

    public static synchronized void closeAllPersistenceProviders() {
        for (EntityManagerFactory emf : mapper.values()) {
            emf.close();
        }
    }
}
