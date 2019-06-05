package com.bjss.services;

import com.bjss.Interfaces.PriceCalculator;
import com.bjss.beans.Cart;
import com.bjss.beans.Item;

import java.util.Collections;

public class SecondDiscountService implements PriceCalculator {



    private PriceCalculator priceCalculator;
    /**
     * Item on which discount condition is applied
     */
    private Item  firstItem;
    /**
     * Minimum quantity to satisfy the condition
     */
    private Integer minQuantity;
    /**
     * Item on which discount would be calulated.
     */
    private Item secondItem;
    /**
     * Discount in percentage %
     */
    private Integer discountPercent;

    //With the use of Dependency this could be injected. Avoiding tight Coupling
    private PriceManagerService priceCalculatorService = new PriceManagerService();

    public SecondDiscountService(PriceCalculator priceCalculator, Item firstItem, Integer minQuantity, Item secondItem, Integer discountPercent) {
        this.priceCalculator = priceCalculator;
        this.firstItem = firstItem;
        this.minQuantity = minQuantity;
        this.secondItem = secondItem;
        this.discountPercent = discountPercent;
    }

    @Override
    public Double calculatePrice(Cart cart) {

        Double discountAmount = null;

        //firstItemCount holds the quantity of firstItems in the cart
        int firstItemCount = Collections.frequency(cart.getCartItems(), firstItem);
        //secondItemCount holds the quantity of secondItem in the cart
        int secondItemCount = Collections.frequency(cart.getCartItems(), secondItem);
        int count =0;
        //Enter if the firstItem frequency satisfies our condition and
        // the item on which discount will be applied is also available in the cart
        if(firstItemCount >= minQuantity && secondItemCount > 0 ){
            //calculate eligibility, if second items added in card is more than eligible count,
            //then discount only on eligible quantity
            count = firstItemCount/minQuantity >= secondItemCount ? secondItemCount :  (firstItemCount/minQuantity);
            //calculate discounted item
            discountAmount =  priceCalculatorService.getDiscountedPrice(secondItem, count, discountPercent);

        }
        //calulate the subtotal using chain process,
        // based on the implementation class added in the chain, it will call its corresponding calulate method.
        Double subTotal =  priceCalculator.calculatePrice(cart);

        if (discountAmount!= null && discountAmount > 0) {
            //Print message for user
            System.out.println(count+" "+ secondItem.getName() +" "+discountPercent+"% off: -"+String.format("%.2f", discountAmount)+  (discountAmount< 1.0 ? "P": ""));
            //keep a track of the number of discounted item in the session for messaging purpose.
            cart.setDiscountedItemCount(cart.getDiscountedItemCount()+count);
            return subTotal-discountAmount;
        }

        return  subTotal;

    }


}
