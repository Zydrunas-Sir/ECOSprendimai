package sample.JPA;

import sample.controller.DashboardController;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.TypedQuery;
import java.util.List;

public class ProductDescriptionDAO {

    public static List<ProductDescription> searchByCatalogNo(int catalogNo) {

        EntityManager entityManager;
        EntityTransaction entityTransaction;
        List<ProductDescription> productDescription = null;
        TypedQuery<ProductDescription> query;
        try {
            entityManager = JPAUtil.getEntityManagerFactory().createEntityManager();
            entityTransaction = entityManager.getTransaction();
            entityTransaction.begin();

            query = entityManager.createQuery("Select e From ProductDescription e WHERE e.catalogNo = ?1", ProductDescription.class);
            productDescription = query.setParameter(1, catalogNo).getResultList();

            entityManager.getTransaction().commit();
            entityManager.close();
        } catch (NullPointerException e ) {
            System.out.println("ProductDescriptionDAO.searchByCatalogNo() NullPointerExecption");
        } catch (RuntimeException e) {
            JPAUtil.infoBox("NEPAVYKO PRISIJUNGTI PRIE DUOMENŲ BAZĖS", "RuntimeException");
        }

        return productDescription;
    }


}
