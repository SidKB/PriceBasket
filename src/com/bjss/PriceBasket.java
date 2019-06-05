package com.bjss;

import com.bjss.Interfaces.PriceCalculator;
import com.bjss.beans.Cart;
import com.bjss.beans.Item;
import com.bjss.services.*;
import com.bjss.exception.ItemNotFoundException;

import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class PriceBasket {

    //injecting price service
    private static final PriceManagerService pc = new PriceManagerService();
    private static final ResourceBundle resource = ResourceBundle.getBundle("Messages");
    private static final String APPLE = "Apple";
    private static final String BREAD = "Bread";
    private static final String SOUP = "Soup";
    private static final int FIFTY = 50;
    private static final int TEN = 10;


    public static void main(String[] args) {

        //initiate the cartItem list
        List<Item> cartItems = new ArrayList<>(args.length);

        //if no arg then throw error
        if(args.length ==0){
            System.err.println(resource.getString("err_empty_cart"));
            System.exit(1);

        }


        for (String arg:args) {
            try {
                //check if the item passed as an argument exist in our catalogue
                if(pc.getPrice(new Item(arg)) == null){
                    String errorMessage = resource.getString("err_invalid_item");
                    throw new ItemNotFoundException(String.format(errorMessage, arg));
                }
                cartItems.add(new Item(arg));

            } catch (ItemNotFoundException e) {
               System.err.println(e.getMessage());
               System.exit(1);
            }
        }

        //populate the cart
        Cart cart = new Cart(cartItems);
        PriceCalculator pc = new SubTotalCalculatorService();

        //Apples have 10% off their normal price this week
        pc = new PercentDiscountService(pc, new Item(APPLE), TEN);

        //Buy 2 tins of soup and get a loaf of bread for half price
        pc = new SecondDiscountService(pc, new Item(SOUP), 2, new Item(BREAD), FIFTY);


        System.out.println("************** PRICE BASKET ************* ");
        //calculate the total price.
        Double totalPrice = pc.calculatePrice(cart);
        if(cart.getDiscountedItemCount() == 0){
            System.out.println(resource.getString("info_no_offer"));
        }
        System.out.println("Total: £"+ String.format("%.2f",totalPrice));
        System.out.println(" ");
        System.out.println("************** THANK YOU ************* ");




    }
}
