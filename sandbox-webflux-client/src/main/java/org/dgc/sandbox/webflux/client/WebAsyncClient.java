package org.dgc.sandbox.webflux.client;

import org.dgc.sandbox.webflux.client.domain.Message;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;


@Component
public class WebAsyncClient
{
    public void connectAndWait()
    {
        WebClient webClient = WebClient.create("http://localhost:8082");

        Flux<String> messages = webClient.get()
                .uri("/message")
                .accept(MediaType.TEXT_PLAIN)
                .header("Authorization", "Bearer eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJkYW5pZWwiLCJleHAiOjE2MDkyMzM5NzJ9.tUTFSSjAJ2DeSNdkNXgEMD90d0MgbQOwAf56B29a_InBMn3ECmMk8zAYSn0n9EnceYnsdj-PjNDnTCfAWRlYbQ")
                .retrieve()
                .bodyToFlux(String.class);

        messages.doOnNext(m -> System.out.print(m));
        messages.blockLast();
    }
}
