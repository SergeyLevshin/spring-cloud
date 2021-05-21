package com.levshin.APIService;

import com.levshin.APIService.domain.Order;
import com.levshin.APIService.domain.OrderStatus;
import org.springframework.http.MediaType;
import org.springframework.retry.annotation.Backoff;
import org.springframework.retry.annotation.Retryable;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Component
public class APIRestClient {

   private final WebClient.Builder webClientBuilder;

    public APIRestClient(WebClient.Builder webClientBuilder) {
        this.webClientBuilder = webClientBuilder;
    }


    @Retryable(value = Exception.class, maxAttempts = 10, backoff = @Backoff(delay = 60))
    public Order sendOrderToCooking(Order order) {
        order.setStatus(OrderStatus.NEW);
        sendOrderToService(order, "http://COOKINGSERVICE/cooking");

        return order;
    }

    @Retryable(value = Exception.class, maxAttempts = 10, backoff = @Backoff(delay = 60))
    public Order sendOrderToDelivery(Order order) {
        order.setStatus(OrderStatus.NEW);
        sendOrderToService(order, "http://DELIVERYSERVICE/cooking");

        return order;
    }

    private Order sendOrderToService(Order order, String uri) {

       return webClientBuilder.build()
                .post()
                .uri(uri)
                .accept(MediaType.APPLICATION_JSON)
                .body(Mono.just(order), String.class)
                .retrieve()
//               .bodyToMono(Order.class)
//               .block();
                .toEntity(Order.class)
                .block().getBody();

    }

}
