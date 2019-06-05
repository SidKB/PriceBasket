package com.bjss.beans;

import java.util.ArrayList;
import java.util.List;

public class Cart {

    private List<Item> cartItems;

    private Integer DiscountedItemCount;

    public Cart(List<Item> carItems) {
        this.cartItems = carItems;
        this.DiscountedItemCount = 0;
    }

    public void setCartItems(List<Item> cartItems) {
        this.cartItems = cartItems;
    }

    public Integer getDiscountedItemCount() {
        return DiscountedItemCount;
    }

    public void setDiscountedItemCount(Integer discountedItemCount) {
        DiscountedItemCount = discountedItemCount;
    }

    public List<Item> getCartItems() {
        return cartItems;
    }

    public void addItem(Item item){
        if(cartItems == null){
            cartItems = new ArrayList<>();
        }
        cartItems.add(item);
    }
}
