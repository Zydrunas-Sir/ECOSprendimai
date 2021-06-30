package sample.JPA;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.TypedQuery;
import java.util.List;

public class CategoryParametersDAO {

    /*public static List<CategoryParameters> searchByCatalogNo(String catalogNo) {

        EntityManager entityManager;
        EntityTransaction entityTransaction;
        List<CategoryParameters> categoryParameters = null;
        TypedQuery<CategoryParameters> query;
        try {
            entityManager = JPAUtil.getEntityManagerFactory().createEntityManager();
            entityTransaction = entityManager.getTransaction();
            entityTransaction.begin();

            query = entityManager.createQuery("", CategoryParameters.class);
            categoryParameters = query.setParameter(1, catalogNo).getResultList();


            entityManager.getTransaction().commit();
            entityManager.close();
        } catch (NullPointerException e ) {
            System.out.println("ProductDescriptionDAO.searchByCatalogNo() NullPointerExecption");
        } catch (RuntimeException e) {
//            JPAUtil.infoBox("NEPAVYKO PRISIJUNGTI PRIE DUOMENŲ BAZĖS", "RuntimeException");
//            JPAUtil.infoBox();

        }
        return categoryParameters;
    }*/


}
