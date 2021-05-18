package com.levshin.APIService;

import com.levshin.APIService.domain.Order;
import com.levshin.APIService.domain.OrderStatus;
import org.springframework.retry.annotation.Backoff;
import org.springframework.retry.annotation.Retryable;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import javax.transaction.Transactional;
import java.time.Duration;

@Component
public class APIRestClient {

   private final WebClient webClient;

    public APIRestClient(WebClient webClient) {
        this.webClient = webClient;
    }

    @Retryable(value = Exception.class, maxAttempts = 10, backoff = @Backoff(delay = 60))
    public Order sendOrderToCooking(Order order) {
        order.setStatus(OrderStatus.NEW);
        sendOrderToService(order, "http://localhost:8901/cooking");//"http://COOKINGSERVICE/cooking");

        return order;
    }

    @Retryable(value = Exception.class, maxAttempts = 10, backoff = @Backoff(delay = 60))
    public Order sendOrderToDelivery(Order order) {

        sendOrderToService(order, "http://DELIVERYSERVICE/cooking");

        return order;
    }

    @Transactional
    private Order sendOrderToService(Order order, String uri) {
        return webClient.post()
                .uri(uri)
                .body(Mono.just(order), Order.class)
                .retrieve()
                .bodyToMono(Order.class).timeout(Duration.ofMillis(10000))
                .block();

    }


}
