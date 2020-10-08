
package ulbra.hard.model.dao;

import java.util.List;
import javax.swing.JOptionPane;
import ulbra.hard.connection.ConnectionFactory;
import ulbra.hard.model.bean.Fornecedor;

/**
 *
 * @author @andre_ments
 */
public class FornecedorDAO {

    public void save(Fornecedor fornecedor) {
        EntityManager em = new ConnectionFactory().getConnection();
        try {
            em.getTransaction().begin();
            if (fornecedor.getId() == -1) { 
                em.persist(fornecedor);
            } else {
                em.merge(fornecedor);
            }
            em.getTransaction().commit();
        } catch (Exception e) {
            em.getTransaction().rollback();
        } finally {
            em.close();
        }
    }

    public Fornecedor remove(Integer id) {
        EntityManager em = new ConnectionFactory().getConnection();
        Fornecedor fornecedor = null;
        try {
            fornecedor = em.find(Fornecedor.class, id);
            em.getTransaction().begin();
            em.remove(fornecedor);
            em.getTransaction().commit();
            JOptionPane.showMessageDialog(null, "Removido");
        } catch (Exception e) {
            System.err.println(e);
            em.getTransaction().rollback();
        } finally {
            em.close();
        }
        return fornecedor;
    }

    public List<Fornecedor> findAll() {
        EntityManager em = new ConnectionFactory().getConnection();
        List<Fornecedor> fornecedores = null;
        try {
            fornecedores = em.createQuery("from Fornecedor f").getResultList();
        } catch (Exception e) {
            System.err.println(e);
        } finally {
            em.close();
        }
        return fornecedores;
    }

    public List<Fornecedor> findAllByFilter(String filter, String filterValue) {
        EntityManager em = new ConnectionFactory().getConnection();
        List<Fornecedor> fornecedores = null;
        try {
            fornecedores = em.createQuery("select f from Fornecedor f where f." + filter + " like '%" + filterValue + "%'")
                    .getResultList();
        } catch (Exception e) {
            System.err.println(e);
        } finally {
            em.close();
        }
        return fornecedores;
    }

}
