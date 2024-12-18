package com.vmd.vmdwebshop.service;

import com.vmd.vmdwebshop.DTO.OrderDto;
import com.vmd.vmdwebshop.exception.order.*;
import com.vmd.vmdwebshop.exception.wine.ProductsNotInStock;
import com.vmd.vmdwebshop.model.OrderLine;
import com.vmd.vmdwebshop.model.Orders;
import com.vmd.vmdwebshop.repository.OrderRepository;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.servlet.View;
import java.util.Date;
import java.util.List;

@Service
@Transactional
public class OrderService {

    private final View error;

    private final OrderRepository orderRepository;
    private final OrderLineService orderLineService;
    private final WineService wineService;

    // til test
    // Constructor
    public OrderService(View error, OrderRepository orderRepository, OrderLineService orderLineService, WineService wineService){
        this.error = error;
        this.orderRepository = orderRepository;
        this.orderLineService = orderLineService;
        this.wineService = wineService;
    }

    /**
     * Denne classe returnere alle odrene i systemet
     * @return List<Orders>
     */
    public List<Orders> getAllOrders() {
        List<Orders> orders = orderRepository.findAll();

        if (orders.isEmpty()) {
            throw new OrdersNotFound();
        }

        return orders;
    }

    /**
     * finder en specifik order based on the order id
     * @param orderID
     * @return
     */
    public Orders getOrderById(Long orderID) {
        Orders order = orderRepository.findById(orderID).orElse(null);

        if (order == null) {
            throw new OrderNotFoundInDatbase(orderID);
        }

        return order;
    }


    /**
     * finder en specifik order based on the session id
     * @param sessionID
     * @return
     */
    public Orders getOrderBySessionID(String sessionID) {
        Orders order = orderRepository.findBySessionID(sessionID);

        if (order == null) {
            throw new OrderNotFoundInDatbase(Long.parseLong(sessionID));
        }

        return order;
    }


    /**
     * Creates an order based on the information given by the customer
     * @param orderDto
     * @param orderLineList
     * @param sessionID
     * @return Orders
     */
    public Orders createOrderFromInfo(OrderDto orderDto, List<OrderLine> orderLineList, String sessionID) {
        try {
            orderLineService.canBePurchased(orderLineList);
        } catch (RuntimeException e) {
            throw new ProductsNotInStock(e.getMessage());
        }

        Orders order = orderDto.createOrderFromInfo();
        order.setState(Orders.State.REGISTERED);
        order.setDate(new Date());
        order.setSessionID(sessionID);
        order.setPrice(OrderLineService.calculateOrderLines(orderLineList));

        orderRepository.save(order);

        if (order.getID() == null) {
            throw new OrderNotSaved(order.getFullName(), order.getMail());
        }

        for (OrderLine orderLine : orderLineList) {
            order.addOrderLine(orderLine);
            orderLine.setOrders(order);

            if (!orderLine.getOrderID().equals(order.getID())) {
                throw new OrderlineNotAdded("Orderline with id: " + orderLine.getID() +
                                            " did not add the order ID of: " + order.getID());
            }
        }

        //Updates Stock
        try {
            wineService.updateStockFromOrder(orderLineList);
        } catch (DataAccessException e) {
            throw new StockNotUpdatedFromOrder("The order could not update the stock");
        }

        return order;
    }

    /**
     * allows admins to change the state of an order
     * This is where we would add emails
     * @param orderID
     * @param state, new state
     * @throws StateChangeFailedException if the object is not properly updated in the database.
     */
    public void changeState(Long orderID, int state) {
        Orders order = getOrderById(orderID);  //retrieves an order object
        Orders.State state1 = order.getState(); //retrives the state
        Orders.State newState = Orders.State.values()[state]; //defines the new state
        order.setState(newState); //sets the new state of the existing object
        System.out.println("Order state changed from: " + state1 + " to: " + newState);

        if (order.getState() != newState) {
            throw new StateChangeFailedException(state1, newState);
        }
    }

    /**
     *Method to delete a specific order
     * @param order
     */
    public void deleteOrder(Orders order) {
        //before deleting an order, all orderlines referenced by the order must be deleted.
        for (OrderLine orderLine: order.getOrderLines()) {
            orderLineService.deleteOrderlineByID(orderLine);
        }

        orderRepository.deleteById(order.getID());
    }
}
