package com.levshin.APIService;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.levshin.APIService.domain.Order;
import com.levshin.APIService.domain.OrderStatus;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.retry.annotation.Backoff;
import org.springframework.retry.annotation.Retryable;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import javax.transaction.Transactional;

@Component
public class APIRestClient {

    private final RestTemplate restTemplate;

    public APIRestClient(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    @Retryable(value = Exception.class, maxAttempts = 3, backoff = @Backoff(delay = 3))
    public Order sendOrderToCooking(Order order) {
        order.setStatus(OrderStatus.NEW);
        sendOrderToService(order, OrderStatus.COOKING, "http://COOKINGSERVICE/cooking");

        return order;
    }

    @Retryable(value = Exception.class, maxAttempts = 3, backoff = @Backoff(delay = 3))
    public Order sendOrderToDelivery(Order order) {

        sendOrderToService(order, OrderStatus.DELIVERING, "http://DELIVERYSERVICE/cooking");

        return order;
    }

    @Transactional
    private void sendOrderToService(Order order, OrderStatus statusIfSuccess, String uri) {

        String json = getJsonString(order);

        HttpEntity<?> entity = new HttpEntity<>(json);

        ResponseEntity<String> responseEntity = restTemplate.exchange(uri, HttpMethod.POST, entity, String.class);

        HttpStatus statusCode = responseEntity.getStatusCode();
        if (statusCode.is2xxSuccessful()) {
            order.setStatus(OrderStatus.DELIVERING);
        }
        else {
            order.setStatus(OrderStatus.SUSPEND);
        }

    }

    private String getJsonString(Order order) {
        String json = "";
        try {
            json = new ObjectMapper().writeValueAsString(order);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return json;
    }
}
