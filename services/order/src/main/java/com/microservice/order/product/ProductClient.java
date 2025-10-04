package com.microservice.order.product;

import com.microservice.order.exception.BusinessException;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ProductClient {
    @Value("${application.config.product-url}")
    private String productUrl;
    private final RestTemplate restTemplate;

    public List<PurchaseResponse> purchaseProducts(List<PurchaseRequest> purchaseRequestsBody){
        HttpHeaders headers = new HttpHeaders();
        headers.set(HttpHeaders.CONTENT_TYPE,MediaType.APPLICATION_JSON_VALUE);

        // object of request entity and pass type that you give and headers if any
        HttpEntity<List<PurchaseRequest>> requestEntity = new HttpEntity<>(purchaseRequestsBody,headers);

        // object of response
        ParameterizedTypeReference<List<PurchaseResponse>> responseType= new ParameterizedTypeReference<>() {
        };

        // actual response
        ResponseEntity<List<PurchaseResponse>> responseEntity = restTemplate.exchange(
                productUrl + "/purchase",
                HttpMethod.POST,
                requestEntity,
                responseType
        );
            // check error in response
        if(responseEntity.getStatusCode().isError()){
            throw new BusinessException(" Error occurred while processing purchase request :: " + responseEntity.getStatusCode());
        }
        return responseEntity.getBody();

    }

}
