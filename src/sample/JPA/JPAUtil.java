package sample.JPA;

import org.hibernate.service.spi.ServiceException;

import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.swing.*;
import java.net.ConnectException;

public class JPAUtil {
    private static final String PERSISTENCE_UNIT_NAME = "PERSISTENCE";
    private static EntityManagerFactory factory;

    public static EntityManagerFactory getEntityManagerFactory() {
        try {
            if (factory == null) {
                factory = Persistence.createEntityManagerFactory(PERSISTENCE_UNIT_NAME);
            }
        } catch (ServiceException e) {
            System.out.println("NEPAVYKO PRISIJUNGTI PRIE DUOMENŲ BAZĖS");
            infoBox("NEPAVYKO PRISIJUNGTI PRIE DUOMENŲ BAZĖS", "ServiceException");
            shutdown();
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
