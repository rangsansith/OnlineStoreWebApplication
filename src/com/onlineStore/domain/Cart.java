package com.onlineStore.domain;

import java.util.HashMap;
import java.util.Map;

public class Cart {
	
	private Map<String, CartItem> cartItemMap = new HashMap<>();
	private double total;
	
	public Map<String, CartItem> getCartItemMap() {
		return cartItemMap;
	}
	public void setCartItemMap(Map<String, CartItem> cartItemMap) {
		this.cartItemMap = cartItemMap;
	}
	public double getTotal() {
		return total;
	}
	public void setTotal(double total) {
		this.total = total;
	}
		
}
