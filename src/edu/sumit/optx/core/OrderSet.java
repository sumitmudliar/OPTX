/*
 * File     : OrderSet.java
 *
 * Author   : Zoltan Feledy
 * 
 * Contents : This class is a Set of Order objects with a utility 
 *            methods to access the individual orders.
 */

package edu.sumit.optx.core;

import java.util.ArrayList;
import java.util.Iterator;

import edu.sumit.optx.core.OPTXEngine;
import edu.sumit.optx.core.Order;
import edu.sumit.optx.ui.OrderTableModel;

public class OrderSet {
    private ArrayList<Order> orders = new ArrayList<Order>();
    private ArrayList<Order> ordersToFill = new ArrayList<Order>();
    private OrderTableModel orderTableModel = null;

    public OrderSet() {}
    
    public void add ( Order order, boolean toFill ) {
        orders.add( order );
        if ( toFill ) ordersToFill.add(order);
        int limit = 50;
        try {
            limit = (int)OPTXEngine.getApplication().getSettings()
                    .getLong("OPTXEngineCachedObjects");
        } catch ( Exception e ) {}
        while ( orders.size() > limit ) {
            orders.remove(0);
        }
        orderTableModel.update();
    }
    
    public void update () {
        orderTableModel.update();
    }
    
    public void addCallback(OrderTableModel orderTableModel){
        this.orderTableModel = orderTableModel;
    }
        
    public int getCount() {
        return orders.size();
    }

    public Order getOrder( int i ) {
        return orders.get( i );
    }

    public Order getOrder( String id ) {
        Iterator<Order> iterator = orders.iterator();
        while ( iterator.hasNext() ){
            Order order = iterator.next();
            if ( order.getID().equals(id) || order.getClientID().equals(id))
                return order;
        }
        return null;
    }
    
    public boolean haveOrdersToFill() {
        if ( ordersToFill.size() > 0 ) return true;
        return false;
    }
    
    public Order getOrderToFill() {
        return ordersToFill.remove(0);
    }
}
