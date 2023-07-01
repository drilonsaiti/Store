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

}
