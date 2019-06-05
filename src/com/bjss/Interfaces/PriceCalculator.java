package com.bjss.Interfaces;

import com.bjss.beans.Cart;

public interface PriceCalculator {


     /**
      * @param cart
      * Calculate total price of the items added to the cart
      * @return total amount.
      */
     Double calculatePrice(Cart cart);
}
