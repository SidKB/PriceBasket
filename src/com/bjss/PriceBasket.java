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

                    String errorMessage = resource.getString("data_item_catalogue");
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
        pc = new PercentDiscountService(pc, new Item("Apple"), 10);

        //Buy 2 tins of soup and get a loaf of bread for half price
        pc = new SecondDiscountService(pc, new Item("Soup"), 2, new Item("Bread"), 50);


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
