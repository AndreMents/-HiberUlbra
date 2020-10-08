
package ulbra.hard.model.dao;

import java.util.List;
import javax.persistence.EntityManager;
import javax.swing.JOptionPane;
import ulbra.hard.connection.ConnectionFactory;
import ulbra.hard.model.bean.Produto;

/**
 *
 * @author @andre_ments
 */
public class ProdutoDAO {

    public void save(Produto produto) {
        EntityManager em = new ConnectionFactory().getConnection();
        try {
            em.getTransaction().begin();
            if (produto.getId() == -1) { 
                em.persist(produto);
            } else { 
                em.merge(produto);
            }
            em.getTransaction().commit();
        } catch (Exception e) {
            em.getTransaction().rollback();
        } finally {
            em.close();
        }
    }

    public Produto remove(Integer id) {
        EntityManager em = new ConnectionFactory().getConnection();
        Produto produto = null;
        try {
            produto = em.find(Produto.class, id);
            em.getTransaction().begin();
            em.remove(produto);
            em.getTransaction().commit();
            JOptionPane.showMessageDialog(null, "Removido");
        } catch (Exception e) {
            System.err.println(e);
            em.getTransaction().rollback();
        } finally {
            em.close();
        }
        return produto;
    }

    public List<Produto> findAll() {
        EntityManager em = new ConnectionFactory().getConnection();
        List<Produto> produtos = null;
        try {
            produtos = em.createQuery("from Produto p").getResultList();
        } catch (Exception e) {
            System.err.println(e);
        } finally {
            em.close();
        }
        return produtos;
    }
    
    public List<Produto> findAllByFilter(String filter, String filterValue) {
        EntityManager em = new ConnectionFactory().getConnection();
        List<Produto> produtos = null;
        try {
            produtos = em.createQuery("select p from Produto p where p." + filter + " like '%" + filterValue + "%'")
                    .getResultList();
        } catch (Exception e) {
            System.err.println(e);
        } finally {
            em.close();
        }
        return produtos;
    }

}
