package com.onlineStore.service;

import java.util.Map;

import com.onlineStore.domain.Cart;
import com.onlineStore.domain.CartItem;
import com.onlineStore.domain.Product;

public class CartService {

	public Cart addProductToCart(Cart cart, Product product, int buyNum) {
		if (cart == null) {
			cart = new Cart();
		}
		CartItem cartItem = addProductToCartItem(cart, product, buyNum);
		cart.getCartItemMap().put(product.getPid(), cartItem);
		cart.setTotal(cart.getTotal() + product.getShop_price() * buyNum);		
		return cart;
	}

	private CartItem addProductToCartItem(Cart cart, Product product, int buyNum) {
		Map<String, CartItem> cartItemMap = cart.getCartItemMap();
		CartItem cartItem = null;
		if (cartItemMap.containsKey(product.getPid())) {
			cartItem = cartItemMap.get(product.getPid());
		} else {
			cartItem = new CartItem();
			cartItem.setProduct(product);
		}
		cartItem.setNum(cartItem.getNum() + buyNum);
		cartItem.setSubtotal(cartItem.getSubtotal() + product.getShop_price() * buyNum);
		return cartItem;
	}

	public void deleteItemFromCart(Cart cart, Product product) {
		Map<String, CartItem> cartItemMap = cart.getCartItemMap();
		double deleteItemValue = 0.0;
		if (cartItemMap.containsKey(product.getPid())) {
			CartItem cartItem = cartItemMap.get(product.getPid());
			deleteItemValue = cartItem.getSubtotal();
			cartItemMap.remove(product.getPid());
		}
		cart.setTotal(cart.getTotal() - deleteItemValue);
	}

	public void changeItemQuantityInCart(Cart cart, Product product, String numberStr) {
		Map<String, CartItem> cartItemMap = cart.getCartItemMap();
		int number = Integer.parseInt(numberStr);
		double changedValue = 0.0;
		if (cartItemMap.containsKey(product.getPid())) {
			CartItem cartItem = cartItemMap.get(product.getPid());
			if (number > 0) {
				int orgNumber = cartItem.getNum();
				changedValue = (number - orgNumber) * product.getShop_price();
				cartItem.setNum(number);
				cartItem.setSubtotal(cartItem.getSubtotal() + changedValue);
			} else {
				changedValue = -cartItem.getSubtotal();
				cartItemMap.remove(product.getPid());
			}
		}
		cart.setTotal(cart.getTotal() + changedValue);
	}
	
	

}
