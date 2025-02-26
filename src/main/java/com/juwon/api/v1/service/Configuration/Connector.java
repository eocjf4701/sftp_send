package com.juwon.api.v1.service.Configuration;

@Component
@RequiredArgsConstructor
public class Connector {
    private final ApiWebClientBuilder webClientBuilder;
    private final ApiStatics statics;


    /**
     * Google Chat WebHook 메세지 전송
     * @param requestBody {@link WebHook}
     */
    public Mono<String> callGoogleChat(WebHook requestBody) {
        ApiStatics.GoogleChat googleChat = statics.getGoogleChat();

        return webClientBuilder.request()
                .post(googleChat.getUrl(), requestBody)
                .connectSubscribe();
    }
    HttpClient httpClient = HttpClient.create()
            .responseTimeout(Duration.ofSeconds(5)); // ⏱️ 5초 Timeout 설정

    WebClient webClient = WebClient.builder()
            .clientConnector(new reactor.netty.http.client.HttpClientConnector(httpClient))
            .baseUrl("https://api.example.com")
            .build();

    String response = webClient.get()
            .uri("/data")
            .retrieve()
            .bodyToMono(String.class)
            .timeout(Duration.ofSeconds(3)) // 🚨 개별 요청에 대한 Timeout (3초)
            .block();


}
