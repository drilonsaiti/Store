package com.example.store.model;


import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;



@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "Products")
public class Products {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(name = "name", length = 999)
	private String name;

	@Column(name = "price", length = 999)
	private double price;

	@Column(name = "purchase_price", length = 999)
	private double purchase_price;

	@Column(name = "quantity", length = 999)
	private int quantity;

	@Column(name = "barCode", length = 999)
	private Long barCode;

	public Products(String name, double price, double purchase_price, int quantity, Long barCode) {
		this.name = name;
		this.price = price;
		this.purchase_price = purchase_price;
		this.quantity = quantity;
		this.barCode = barCode;
	}
}
