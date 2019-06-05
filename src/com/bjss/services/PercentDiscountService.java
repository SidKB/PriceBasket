package com.bjss.services;

import com.bjss.Interfaces.PriceCalculator;
import com.bjss.beans.Cart;
import com.bjss.beans.Item;

import java.util.Collections;

public class PercentDiscountService implements PriceCalculator {


    private PriceCalculator priceCalculator;
    /**
     *Item on which discounted.
     */
    private Item discountedItem;
    /**
     * Discount Percentage.
     */
    private Integer discountedPercent;

    //With the use of Dependency this could be injected. Avoiding tight Coupling
    private PriceManagerService priceCalculatorService = new PriceManagerService();


    public PercentDiscountService(PriceCalculator priceCalculator, Item discountedItem, Integer discountedPercent) {
        this.priceCalculator = priceCalculator;
        this.discountedItem = discountedItem;
        this.discountedPercent = discountedPercent;
    }

    @Override
    public Double calculatePrice(Cart cart){

        //count holds the quantity of qualified discount items added in the cart
        int count = Collections.frequency(cart.getCartItems(), discountedItem);

        //populate discount amount based on the given params.
        Double discountAmount = priceCalculatorService.getDiscountedPrice(discountedItem, count, discountedPercent);

        //calculate the subtotal of
        Double subtotal = priceCalculator.calculatePrice(cart);

        if(discountAmount > 0) {
            System.out.println(discountedItem.getName() + " " + discountedPercent + "% off: -" + String.format("%.2f", discountAmount)+  (discountAmount< 1.0 ? "P": "") );
            cart.setDiscountedItemCount(cart.getDiscountedItemCount()+count);
        }

        return subtotal - discountAmount;

    }
}
