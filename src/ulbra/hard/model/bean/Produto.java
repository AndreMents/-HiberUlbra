
package ulbra.hard.model.bean;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

/**
 *
 * @author @andre_ments
 */
@Entity
public class Produto {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id = -1;
    private String description;
    private int quantity;
    private double price;
    @ManyToOne
    private Categoria category;
    @ManyToOne
    private Fornecedor supplier;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public Categoria getCategory() {
        return category;
    }

    public void setCategory(Categoria category) {
        this.category = category;
    }

    public Fornecedor getSupplier() {
        return supplier;
    }

    public void setSupplier(Fornecedor supplier) {
        this.supplier = supplier;
    }
    
}
