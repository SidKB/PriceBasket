package com.bjss.services;

import com.bjss.beans.Item;

import java.util.*;

/**
 * Price Service handles all the query with items.
 * It would act as a repository once connected to a ORM.
 * This class pull latest catalogue available in the db(in-future).
 */
public class PriceManagerService {

    private final Map<Item, Double> basePrice = new HashMap<>();
    private static final ResourceBundle resource = ResourceBundle.getBundle("Messages");

    /**
     * Catalogue details and price per unit
     */
    public PriceManagerService() {

        //populate items list from properties
        List<String> items = Arrays.asList(resource.getString("data_item_catalogue").split(","));

        List<String> itemCosts = Arrays.asList(resource.getString("data_item_catalogue_price").split(","));

        for(int i=0; i< items.size(); i++){
            basePrice.put(new Item(items.get(i)), Double.parseDouble(itemCosts.get(i)));
        }

    }

    /**
     *
     * @param item
     * @return
     */
    public Double getPrice(Item item){
        return basePrice.get(item);
    }

    /**
     * Method calculates discounted value on a item given the quantity and discount %
     * @param item
     * @param count
     * @param percentDiscount
     * @return
     */
    public Double getDiscountedPrice(Item item, Integer count, Integer percentDiscount){
         return (basePrice.get(item) * count  * percentDiscount)/100;
    }


}
