
package ulbra.hard.model.dao;

import java.util.List;
import javax.persistence.EntityManager;
import javax.swing.JOptionPane;
import ulbra.hard.connection.ConnectionFactory;
import ulbra.hard.model.bean.Categoria;

/**
 *
 * @author @andre_ments
 */
public class CategoriaDAO {

    public void save(Categoria categoria) {
        EntityManager em = new ConnectionFactory().getConnection();
        try {
            em.getTransaction().begin();
            if (categoria.getId() == -1) { 
                em.persist(categoria);
            } else { 
                em.merge(categoria);
            }
            em.getTransaction().commit();
        } catch (Exception e) {
            em.getTransaction().rollback();
        } finally {
            em.close();
        }
    }

    public Categoria remove(Integer id) {
        EntityManager em = new ConnectionFactory().getConnection();
        Categoria categoria = null;
        try {
            categoria = em.find(Categoria.class, id);
            em.getTransaction().begin();
            em.remove(categoria);
            em.getTransaction().commit();
            JOptionPane.showMessageDialog(null, "Removido");
        } catch (Exception e) {
            System.err.println(e);
            em.getTransaction().rollback();
        } finally {
            em.close();
        }
        return categoria;
    }

    public List<Categoria> findAll() {
        EntityManager em = new ConnectionFactory().getConnection();
        List<Categoria> categorias = null;
        try {
            categorias = em.createQuery("from Categoria c").getResultList();
        } catch (Exception e) {
            System.err.println(e);
        } finally {
            em.close();
        }
        return categorias;
    }
    
    public List<Categoria> findAllByFilter(String filter, String filterValue) {
        EntityManager em = new ConnectionFactory().getConnection();
        List<Categoria> categorias = null;
        try {
            categorias = em.createQuery("select c from Categoria c where c." + filter + " like '%" + filterValue + "%'")
                    .getResultList();
        } catch (Exception e) {
            System.err.println(e);
        } finally {
            em.close();
        }
        return categorias;
    }

}
