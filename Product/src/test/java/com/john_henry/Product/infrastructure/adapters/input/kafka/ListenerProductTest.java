package com.john_henry.Product.infrastructure.adapters.input.kafka;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.concurrent.CompletableFuture;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

@ExtendWith(MockitoExtension.class)
class ListenerProductTest {

    private ListenerProduct listenerProduct;

    @BeforeEach
    public void setUp() {
        listenerProduct = new ListenerProduct();
    }

    @Test
    public void registerFuture_ShouldAddFutureToMap() {
        Integer key = 4;

        CompletableFuture<Integer> future = listenerProduct.registerFuture(key);

        assertNotNull(future);
        CompletableFuture<Integer> storedFuture = listenerProduct.getResponseMap().get(key);
        assertNotNull(storedFuture);
        assertEquals(future, storedFuture);
    }

    @Test
    public void listener_ShouldCompleteFuture_WhenFutureExists() {
        Integer key = 4;
        Integer response = 4;
        CompletableFuture<Integer> future = listenerProduct.registerFuture(key);

        listenerProduct.listener(response);

        assertTrue(future.isDone());
        assertEquals(response, future.join());
    }

    @Test
    public void listener_ShouldLogError_WhenFutureDoesNotExist() {
        Integer response = 42;

        ListenerProduct spyListener = Mockito.spy(listenerProduct);

        spyListener.listener(response);

    }

}