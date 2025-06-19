import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.Map;

public class GraphApiPaginatedClient {

    private final WebClient webClient;

    public GraphApiPaginatedClient(WebClient.Builder builder) {
        this.webClient = builder
                .baseUrl("https://graph.microsoft.com/v1.0")
                .build();
    }

    public Flux<Map<String, Object>> getAllMembers(String groupId) {
        String initialUrl = "/groups/" + groupId + "/members?$top=50";

        return fetchPage(initialUrl)
                .expand(response -> {
                    Object next = response.get("@odata.nextLink");
                    return next != null
                            ? fetchPageByFullUrl(next.toString())
                            : Mono.empty();
                })
                .flatMap(response -> {
                    Object value = response.get("value");
                    if (value instanceof List<?>) {
                        List<?> list = (List<?>) value;
                        return Flux.fromIterable((List<Map<String, Object>>) list);
                    }
                    return Flux.empty();
                });
    }

    private Mono<Map<String, Object>> fetchPage(String relativeUrl) {
        return webClient.get()
                .uri(relativeUrl)
                .retrieve()
                .bodyToMono(new ParameterizedTypeReference<>() {});
    }

    private Mono<Map<String, Object>> fetchPageByFullUrl(String fullUrl) {
        return webClient.get()
                .uri(fullUrl)
                .retrieve()
                .bodyToMono(new ParameterizedTypeReference<>() {});
    }
}
