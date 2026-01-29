# LIFO / FIFO Storage – Strategy Pattern with Spring

## Interview Problem
Design and implement a storage system that can operate in two modes: LIFO (Last In, First Out) and FIFO (First In, First Out). 
The system should allow switching between these modes at runtime.
Implement LIFO (stack) and FIFO (queue) storage mechanisms.
The solution must:
- Use interfaces and services
- Support runtime selection (LIFO / FIFO)
- Use custom annotations
- Demonstrate Spring DI with @Service and @Autowired
- Include automated tests

## High-Level Design
- `Storage<T>` – common abstraction
- `LifoStorage` / `FifoStorage` – concrete strategies
- `@StorageStrategy` – custom annotation marking implementations
- `StorageSelector` – selects implementation based on enum
- Spring context handles wiring

## Key Concepts Demonstrated
- Strategy Pattern
- Custom annotations
- Reflection-based bean selection
- Spring dependency injection
- Clean separation between data structures and business logic
- JUnit 5 + Spring Test integration

## Why Not Hardcode?
Hardcoding `if/else` logic would violate Open/Closed Principle.
This approach allows:
- Easy extension (e.g. PRIORITY, DELAYED)
- Zero change to selection logic

## Testing Approach
- Spring context-based integration test
- No stacktrace hacks or reflection in tests

## Extension Ideas
- Add new storage types
- Replace in-memory storage with persistent implementation
- Add concurrency-safe variants

## Running the Demo

This project includes a Spring Boot application demonstrating
runtime usage of LIFO and FIFO storage strategies.

Run:
- `Application.java`
- Observe console output for stack and queue behavior

Example output:
```
2026-01-26T23:10:35.411+02:00  INFO 30776 --- [           main] com.alexchernikov.Application            : Started Application in 0.326 seconds (process running for 0.649)
=== LIFO Demo ===
3
2
1

=== FIFO Demo ===
1
2
3

Process finished with exit code 0
```

## Discover dependencies:
```
-----> mvn dependency:tree 
[INFO] Scanning for projects...
[INFO] 
[INFO] ------------< com.alexchernikov:coding-interview-solutions >------------
[INFO] Building coding-interview-solutions 1.0-SNAPSHOT
[INFO]   from pom.xml
[INFO] --------------------------------[ jar ]---------------------------------
[INFO] 
[INFO] --- dependency:2.8:tree (default-cli) @ coding-interview-solutions ---
[INFO] com.alexchernikov:coding-interview-solutions:jar:1.0-SNAPSHOT
[INFO] +- org.springframework:spring-context:jar:6.2.7:compile
[INFO] |  +- org.springframework:spring-aop:jar:6.2.7:compile
[INFO] |  +- org.springframework:spring-beans:jar:6.2.7:compile
[INFO] |  +- org.springframework:spring-core:jar:6.2.7:compile
[INFO] |  |  \- org.springframework:spring-jcl:jar:6.2.7:compile
[INFO] |  +- org.springframework:spring-expression:jar:6.2.7:compile
[INFO] |  \- io.micrometer:micrometer-observation:jar:1.14.7:compile
[INFO] |     \- io.micrometer:micrometer-commons:jar:1.14.7:compile
[INFO] +- org.springframework:spring-test:jar:6.1.2:test
[INFO] \- org.junit.jupiter:junit-jupiter:jar:5.10.1:test
[INFO]    +- org.junit.jupiter:junit-jupiter-api:jar:5.10.1:test
[INFO]    |  +- org.opentest4j:opentest4j:jar:1.3.0:test
[INFO]    |  +- org.junit.platform:junit-platform-commons:jar:1.10.1:test
[INFO]    |  \- org.apiguardian:apiguardian-api:jar:1.1.2:test
[INFO]    +- org.junit.jupiter:junit-jupiter-params:jar:5.10.1:test
[INFO]    \- org.junit.jupiter:junit-jupiter-engine:jar:5.10.1:test
[INFO]       \- org.junit.platform:junit-platform-engine:jar:1.10.1:test
[INFO] ------------------------------------------------------------------------
[INFO] BUILD SUCCESS
[INFO] ------------------------------------------------------------------------
[INFO] Total time:  0.457 s
[INFO] Finished at: 2026-01-26T12:20:22+02:00
[INFO] ------------------------------------------------------------------------
```