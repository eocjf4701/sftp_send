package com.juwon.api.v1.service;

import com.jcraft.jsch.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.io.FileInputStream;
import java.io.InputStream;

@Service
@Slf4j
@RequiredArgsConstructor
public class ApiService {
    private final WebClient.RequestHeadersSpec<?> methodType;
    private Object response;


    /**
     * WebClient의 header와 response class 설정 후 subscribe로 호출<br>
     * 헤더와 응답값이 존재할 때 사용. (ex. 일반적인 api 호출)<br>
     * 예외처리 포함
     * @return {@link ResponseStep}
     */
    @Override
    public Mono<?> connectSubscribe(Map<String, String> headers, Class<?> responseType) {
        try {
            return this.methodType
                    .headers(httpHeaders -> httpHeaders.setAll(headers == null || headers.isEmpty() ? new HashMap<>() : headers))
                    .retrieve()
                    .onStatus(HttpStatusCode::isError, clientResponse ->
                            clientResponse.bodyToMono(String.class)
                                    .flatMap(msg -> Mono.error(new ApiException(ResponseCode.INTERNAL_SERVER_ERROR, msg))))
                    .bodyToMono(responseType)
                    .retryWhen(Retry.fixedDelay(3, java.time.Duration.ofSeconds(1))
                            .doBeforeRetry(before -> log.info("Retry: {} | {}", before.totalRetries(), before.failure())));
        } catch (Exception e) {
            throw new ApiException(ResponseCode.INTERNAL_SERVER_ERROR, e.getMessage());
        }
    }

    /**
     * WebClient의 header와 response class 설정 후 subscribe로 호출<br>
     * 헤더와 응답값이 존재하지 않을 때 사용. (ex. Google chat webhook api 호출)<br>
     * 예외처리 포함
     * @return {@link ResponseStep}
     */
    @Override
    public Mono<String> connectSubscribe() {
        try {
            return this.methodType
                    .retrieve()
                    .onStatus(HttpStatusCode::isError, clientResponse ->
                            clientResponse.bodyToMono(String.class)
                                    .flatMap(msg -> Mono.error(new ApiException(ResponseCode.INTERNAL_SERVER_ERROR, msg))))
                    .bodyToMono(String.class)
                    .retryWhen(Retry.fixedDelay(3, java.time.Duration.ofSeconds(1))
                            .doBeforeRetry(before -> log.info("Retry: {} | {}", before.totalRetries(), before.failure())));
        } catch (Exception e) {
            throw new ApiException(ResponseCode.INTERNAL_SERVER_ERROR, e.getMessage());
        }
    }
    출처: https://annajin.tistory.com/228 [내일 한걸음 더:티스토리]
}