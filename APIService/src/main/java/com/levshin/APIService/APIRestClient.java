package com.levshin.APIService;

import com.levshin.APIService.domain.Order;
import com.levshin.APIService.domain.OrderStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import javax.transaction.Transactional;

@Component
public class APIRestClient {

    private final WebClient webClient;

    public APIRestClient(WebClient webClient) {
        this.webClient = webClient;
    }

    @Transactional
    public Order sendOrderToCooking(Order order) {
        order.setStatus(OrderStatus.COOKING);
        return sendOrderToService(order, "http://localhost:8901/cooking/");
    }

    @Transactional
    public Order sendOrderToDelivery(Order order) {
        order.setStatus(OrderStatus.DELIVERING);
        return sendOrderToService(order, "http://DELIVERYSERVICE/cooking");
    }

    private Order sendOrderToService(Order order, String uri) {
        return webClient.post()
                .uri(uri)
                .body(Mono.just(order), Order.class)
                .retrieve()
                .bodyToMono(Order.class)
                .block();
    }


}
