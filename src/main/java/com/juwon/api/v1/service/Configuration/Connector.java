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
}
출처: https://annajin.tistory.com/228 [내일 한걸음 더:티스토리]
