CREATE TABLE viewer (
id BIGINT PRIMARY KEY AUTO_INCREMENT,
ticketid INT NOT NULL,
name VARCHAR(255) NOT NULL,
lastlogin DATE NOT NULL,
status BOOLEAN NOT NULL
);

GET:
http://localhost:8080/viewers
http://localhost:8080/viewers/stream

POST:
curl --location 'http://localhost:8080/viewers' \
--header 'Authorization: Bearer eyJhbGciOiJIUzI1NiJ9.eyJpc3MiOiJjb21wYW5zb2wiLCJzdWIiOiJibHVtZW4yQGthc3RlY2hzc2cuY29tIiwib3JnQ29kZSI6ImNvbXBhbnNvbCIsIm9yZ0lkIjowLCJlbWFpbCI6ImJsdW1lbjJAa2FzdGVjaHNzZy5jb20iLCJzY29wZSI6InN1cGVyQWRtaW4iLCJpYXQiOjE2NjMzMjUwNDAsImV4cCI6MTY2MzMyNTM0MH0.C9Ut2vOd2i70PSdJ4GsRYWRVtM8advVgDPg7aaCV8is' \
--header 'Content-Type: application/json' \
--data '{
"ticketid": 101,
"name": "Younus",
"lastlogin": "2025-04-17",
"status": true
}
'



======

.exchangeToMono(response -> {
System.out.println("Status code: " + response.rawStatusCode());
if (response.statusCode().is2xxSuccessful()) {
return response.bodyToMono(Product.class);
} else {
return response.bodyToMono(String.class)
.flatMap(errorBody -> Mono.error(new RuntimeException("Error: " + errorBody)));
}
})
.timeout(Duration.ofSeconds(5))
.doOnError(error -> System.out.println("Error during call: " + error.getMessage()))
.subscribe(
product -> System.out.println("Received product: " + product),
err -> System.out.println("Failed: " + err.getMessage())
);
