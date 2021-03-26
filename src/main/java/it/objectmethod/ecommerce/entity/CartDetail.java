package it.objectmethod.ecommerce.entity;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table(name = "carrello_dettaglio")
public class CartDetail {

	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Id
	@Column(name = "id_carrello_dettaglio")
	private Integer cartDetailId;

	@Column(name = "quantita")
	private Integer quantity;

	@OneToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "id_articolo")
	private Item item;

	public Integer getCartDetailId() {
		return cartDetailId;
	}

	public void setCartDetailId(Integer cartDetailId) {
		this.cartDetailId = cartDetailId;
	}

	public Integer getQuantity() {
		return quantity;
	}

	public void setQuantity(Integer quantity) {
		this.quantity = quantity;
	}

	public Item getItem() {
		return item;
	}

	public void setItem(Item item) {
		this.item = item;
	}
}
