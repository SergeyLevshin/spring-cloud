package com.levshin.CookingService;

import com.levshin.CookingService.domain.Order;
import com.levshin.CookingService.domain.OrderStatus;
import com.levshin.CookingService.domain.Pizza;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
public class PizzaMaker {

    private final CookingService service;

    public PizzaMaker(@Lazy CookingService service) {
        this.service = service;
    }

    public void process(Order order) {
        Map<Pizza, Integer> pizzas = order.getPizzas();
        if (pizzas == null) {
            System.out.println("No pizza to cook!!!");
            order.setDescription(order.getDescription() + " There was no pizza to cook!!!");
            order.setStatus(OrderStatus.CANCELED);
            service.finishOrder(order);
            return;
        }
        for (Map.Entry<Pizza, Integer> entry : pizzas.entrySet()) {
            for (int i = 0; i < entry.getValue(); i++) {
                cook(entry.getKey());
            }
        }
        order.setStatus(OrderStatus.COOKED);
        service.finishOrder(order);
    }

    //    pretty stupid logic, but for now it will be so
    private void cook(Pizza pizza) {
        try {
            Thread.sleep(1000);
            System.out.println(pizza.getName() + " is cooking!");
        } catch (InterruptedException e) {
            System.out.println("Something wrong was happened at a cuisine!");
            e.printStackTrace();
        }
    }
}
