package sample.JPA;


import net.bytebuddy.pool.TypePool;

import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.swing.*;

public class JPAUtil {
    private static final String PERSISTENCE_UNIT_NAME = "PERSISTENCE";
    private static EntityManagerFactory factory = null;

    public static EntityManagerFactory getEntityManagerFactory() {
        if (factory == null) {
            try {
                factory = Persistence.createEntityManagerFactory(PERSISTENCE_UNIT_NAME);
            } catch (RuntimeException exception) {
                System.out.println("JPAUtil RuntimeException ");
                JPAUtil.infoBox("NEPAVYKO PRISIJUNGTI PRIE DUOMENŲ BAZĖS", "SQLException");
            }
        }
        return factory;
    }

    public static void shutdown() {
        if (factory != null) {
            factory.close();
        }
    }
    public static void infoBox(String infoMessage, String titleBar)
    {
        JOptionPane.showMessageDialog(null, infoMessage, titleBar, JOptionPane.INFORMATION_MESSAGE);
    }
}

