package com.bjss.services;

import com.bjss.Interfaces.PriceCalculator;
import com.bjss.beans.Cart;

public class SubTotalCalculatorService implements PriceCalculator {


    //With the use of Dependency this could be injected. Avoiding tight Coupling
    PriceManagerService priceCalculatorService = new PriceManagerService();


    @Override
    public Double calculatePrice(Cart cart) {
        //calculate the total by adding all items multiplied by there unit cost.
        //This will calculate the subtotal without any discount.
        Double sum =cart.getCartItems().stream().mapToDouble(value -> priceCalculatorService.getPrice(value)).sum();
        System.out.println(" ");
        System.out.println("Subtotal: £"+ String.format("%.2f", sum));
       return sum ;
    }
}
