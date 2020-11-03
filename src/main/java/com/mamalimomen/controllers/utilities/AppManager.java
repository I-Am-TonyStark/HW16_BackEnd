package com.mamalimomen.controllers.utilities;

import com.mamalimomen.base.controllers.utilities.PersistenceUnitManager;
import com.mamalimomen.base.domains.BaseEntity;
import com.mamalimomen.base.services.BaseService;
import com.mamalimomen.base.controllers.utilities.PersistenceUnits;
import com.mamalimomen.services.impl.PersonServiceImpl;

import javax.persistence.EntityManager;
import java.io.FileNotFoundException;
import java.io.PrintStream;
import java.util.*;

public final class AppManager {
    private static final List<EntityManager> emList = new ArrayList<>();
    private static final Map<Services, BaseService<? extends String, ? extends BaseEntity>> serviceMapper = new HashMap<>();

    private AppManager() {
    }

    public synchronized static void startApp() {
        try {
            System.setErr(new PrintStream("D:\\عزم راسخ\\جاوا مکتب\\کلاس\\46\\HW16_BackEnd\\src\\main\\resources\\log.txt"));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        EntityManager em = PersistenceUnitManager.getEntityManager(PersistenceUnits.UNIT_ONE);

        emList.add(em);

        serviceMapper.put(Services.PERSON_SERVICE, new PersonServiceImpl(em));
    }

    public static <PK extends String, E extends BaseEntity, S extends BaseService<PK, E>> S getService(Services service) {
        return (S) serviceMapper.get(service);
    }

    public static synchronized void endApp() {
        for (EntityManager em : emList) {
            em.close();
        }

        PersistenceUnitManager.closeAllPersistenceProviders();
    }
}
