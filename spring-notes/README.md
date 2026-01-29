# Spring Framework – Practical Annotations & Concepts Cheat Sheet
This document collects practical reminders and interview-oriented explanations of commonly used 
Spring annotations and concepts.

## 1. Spring vs Spring Framework vs Spring Boot
**Spring Framework**: 
- The core framework 
- provides features like :
- Dependency injection(IoC container) 
- AOP
- Transaction management
- Spring MVC
- JDBC / JPA abstraction
- **Very flexible**, but requires lots of configuration.
* Think: toolbox with many tools, but you have to assemble them yourself.

**Spring Boot**:
- Built on top of Spring Framework
- Simplifies setup and development of Spring applications
- Provides :
- Auto-configuration 
- Starter dependencies 
- Embedded servers(Tomcat, Jetty)
- **Opinionated defaults** to reduce boilerplate configuration.
* Think: pre-assembled kit for building Spring apps quickly.

```
Aspect        | Spring Framework | Spring Boot
Setup         | Manual           | Automatic
Configuration |	XML / Java       | Mostly conventions
Server        | External         | Embedded
Speed         | Slower           | Very fast
```

**Spring (umbrella term)**
- Often means Spring ecosystem:
- Spring Framework
- Spring Boot
- Spring Data
- Spring Security
- Spring Cloud

## 2. Dependency Injection Basics
What is DI?

Spring creates and manages objects (beans) and injects dependencies instead of you creating them manually.

```
@Service
class OrderService {
    private final PaymentService paymentService;

    public OrderService(PaymentService paymentService) {
        this.paymentService = paymentService;
    }
}
```
- Cleaner code
- Easier testing
- Loose coupling

## 3. Core Stereotype Annotations
These are specializations of @Component.

**@Component**
- Generic stereotype for any Spring-managed component (Spring-managed bean).
- Use when no clearer role exists

```
@Component
class UtilityHelper { }
```
- Pros: simple, flexible
- Cons: less descriptive, harder to understand role, unclear intent


**@Service**
- Business logic layer

```
@Service
class ChargingService { }
```
- Pros: indicates business logic, clearer intent, semantically meaningful
- Enables service-level AOP (transactions, security)

**@Repository**
- Data access layer (DAOs)
- Translates DB exceptions to Spring's DataAccessException hierarchy

```
@Repository
class StationRepository { }
```

**@Controller** / **@RestController**

@Controller
- MVC controller (returns views)

@RestController
- REST APIs controller (returns JSON/XML directly)
- Combines @Controller + @ResponseBody

```
@RestController
@RequestMapping("/stations")
class StationController { }
```

When to use which?
```
Layer          | Annotation
Utility        | @Component
Business logic | @Service
Data access    | @Repository
REST API       | @RestController
```

## 4. @Bean vs @Component
**@Component**
- Class-level
- Automatically discovered via classpath scanning

```
@Component
class CacheManager { }
```

**@Bean**
- Method-level
- Explicit bean creation
- Used in @Configuration classes

```
@Configuration
class AppConfig {

    @Bean
    DataSource dataSource() {
        return new HikariDataSource();
    }
}
```
When to use @Bean?
- When you need to create third-party classes as beans
- When you need custom initialization logic (conditional logic)
- When you want to configure bean properties programmatically (complex initialization, e.g. read from config files)

## 5. @Autowired (Dependency Injection)
Injects dependencies into fields, constructors, or setters.

Preferred: constructor injection

```
@Service
class StorageSelector {

    private final List<Storage<?>> storages;

    public StorageSelector(List<Storage<?>> storages) {
        this.storages = storages;
    }
}
```
- Immutable dependencies
- Test-friendly
- Recommended by Spring
- X - Avoid field injection (harder to test)

## 6. @Configuration & @ContextConfiguration
**@Configuration**
- Marks a class as a source of bean definitions (as Spring configuration)

```
@Configuration
@ComponentScan("com.example")
class AppConfig { }
```

**@ContextConfiguration (tests)**
Defines which config is loaded for tests.

```
@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = AppConfig.class)
class MyTest { }
```
- Controls test scope
- Loads specific beans for testing
- Isolates test context
- Useful for integration tests
- Faster than full Spring Boot context
- X - Avoid loading unnecessary beans

## 7. Custom Annotations (Advanced / Interview Topic)
Used to add semantic meaning and enable reflection-based logic.

```
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface StorageStrategy {
    StorageType type();
}
```

Used together with:
```
@Service
@StorageStrategy(type = StorageType.LIFO)
class LifoStorage implements Storage { }
```
- Clean
- Extensible
- Enables dynamic behavior
- No if/else explosion
- X - Avoid overuse (can complicate code)

## 8. Reflection-Based Bean Selection (Advanced / Interview Topic)
Dynamically select beans based on custom annotations.

```
public class StorageSelector {

    private final Map<StorageType, Storage<?>> storageMap = new EnumMap<>(StorageType.class);

    public StorageSelector(ApplicationContext applicationContext) {
        Map<String, Object> beans = applicationContext.getBeansWithAnnotation(StorageStrategy.class);

        for (Object bean : beans.values()) {
            StorageStrategy annotation = bean.getClass().getAnnotation(StorageStrategy.class);
            storageMap.put(annotation.type(), (Storage<?>) bean);
        }
    }

    @SuppressWarnings("unchecked")
    public <T> Storage<T> getStorage(StorageType type) {
        Storage<T> storage = (Storage<T>) storageMap.get(type);

        if (storage == null) {
            throw new IllegalArgumentException("No storage implementation found for type " + type);
        }

        return storage;
    }
}
```

## 9. Common Pitfalls
- X - Too many @Component
- X - Field injection
- X - Loading full Spring Boot context in unit tests
- X - Mixing responsibilities across layers

## 10. Interview Tips
- Always explain why, not just how
- Mention:
  - Testability
  - Maintainability
  - SOLID principles
- Prefer constructor injection
- Use stereotypes intentionally

## 11. SOLID Principles in Spring
SOLID is a set of design principles that help create maintainable, extensible, and testable object-oriented systems.
In interviews, SOLID is often used to evaluate design thinking, not syntax.

- **S**ingle Responsibility: Each class has one reason to change (e.g., separate services, repositories).
- **O**pen/Closed: Use interfaces and abstractions to allow extension without modification (e.g., strategy pattern).
- **L**iskov Substitution: Subtypes should be substitutable for their base types (e.g., use interfaces).
- **I**nterface Segregation: Prefer many specific interfaces over a single general one (e.g., separate service interfaces).
- **D**ependency Inversion: Depend on abstractions, not concretions (e.g., use interfaces for services).

### 1. S — Single Responsibility Principle (SRP)

Definition:
A class should have only one reason to change.

X - Bad example 
```
class ChargingService {
    void startCharging() { }
    void saveToDatabase() { }
    void sendNotification() { }
}
```
Why bad?
- Business logic
- Persistence
- Messaging

→ 3 responsibilities

V - Good example
```
@Service
class ChargingService {
    void startCharging() { }
}

@Repository
class ChargingRepository {
    void save() { }
}

@Service
class NotificationService {
    void send() { }
}
```
Why we care?
- Easier testing
- Less ripple effects
- Cleaner commits

Rule of thumb:
- If you struggle to name the class precisely → it does too much.

### 2. O — Open/Closed Principle (OCP)
Definition:
Software entities should be open for extension, closed for modification.

X - Bad example
```a
if (type == LIFO) {
    ...
} else if (type == FIFO) {
    ...
}
```
Every new type → modify code - X

V - Good example (Strategy Pattern)
```
interface Storage<T> {
    void add(T item);
    T remove();
}

@StorageStrategy(type = LIFO)
class LifoStorage<T> implements Storage<T> { }

@StorageStrategy(type = FIFO)
class FifoStorage<T> implements Storage<T> { }

Storage get(StorageType type);

```

V - Add new behavior without touching selector logic

Why we care?
- Prevents regression bugs
- Scales with features
- Matches real-world microservices evolution

### 3. L — Liskov Substitution Principle (LSP)
Definition:
Subtypes must be substitutable(replaceable) for their base types without breaking behavior.

X - Violation example
```
class ReadOnlyQueue extends Queue {
    @Override
    void add() {
        throw new UnsupportedOperationException();
    }
}
```
Breaks expectations - X

V - Correct usage:
```
Storage<Integer> storage = new LifoStorage<>();
```

Any implementation works without surprises.

**Practical check**

Ask yourself:
- Does subclass weaken contracts?
- Does it throw unexpected exceptions?

### 4. I — Interface Segregation Principle (ISP)
Definition:
Clients should not be forced to depend on interfaces they do not use.

X - Bad example
```
interface Storage<T> {
    void add(T item);
    T remove();
    int size();
    void clear();
    void serialize();
}
```
Forces unrelated methods - X

V - Good example
```
interface Storage<T> {
    void add(T item);
    T remove();
}

interface Clearable {
    void clear();
}
```

Why we care:
- Smaller contracts
- Less coupling
- Easier mocking

### 5. D — Dependency Inversion Principle (DIP)
Definition:
Depend on abstractions, not concrete implementations.
X - Bad example
```
class ChargingService {
    private MySqlChargingRepository repo = new MySqlChargingRepository();
}
```
Hard-coded - X

Untestable - X

V - Good example (Spring-style)
```
@Service
class ChargingService {

    private final ChargingRepository repository;

    public ChargingService(ChargingRepository repository) {
        this.repository = repository;
    }
}
```
V - Mockable
V - Replaceable
V - Clean

Spring enforces SOLID naturally if used correctly.

## 12. Typical interview questions + answers
**Q1: What is the difference between @Component, @Service, @Repository, and @RestController?**
- A1: They are all stereotypes for Spring-managed beans. @Component is generic, while the others indicate specific roles: @Service for business logic, @Repository for data access, and @RestController for REST APIs.

**Q2: When would you use @Bean instead of @Component?**
- A2: Use @Bean for third-party classes or when you need custom initialization logic that cannot be handled by classpath scanning.

**Q3: Why is constructor injection preferred over field injection?**
- A3: Constructor injection makes dependencies explicit, supports immutability, and is easier to test.

**Q4: How can you dynamically select a bean implementation at runtime?**
- A4: You can use custom annotations and reflection to map implementations to types, allowing dynamic selection without if/else statements.  

**Q5: What are some common pitfalls when using Spring?**
- A5: Overusing @Component, using field injection, loading unnecessary beans in tests, and mixing responsibilities across layers.

**Q6: How do SOLID principles apply to Spring applications?**
- A6: They guide the design of maintainable and testable code by promoting single responsibility, open/closed design, substitutability, interface segregation, and dependency inversion.

**Q7: Can you explain the difference between Spring Framework and Spring Boot?**
- A7: Spring Framework is the core framework providing features like DI, AOP, and MVC, while Spring Boot simplifies setup with auto-configuration, starter dependencies, and embedded servers for rapid development.

**Q8: Which SOLID principle is most important?**
- A8: SRP. If SRP is violated, all others usually are.

**Q9: How does Spring help with DIP?**
- A9: By injecting interfaces instead of concrete implementations using IoC.

**Q10: How does your LIFO/FIFO solution follow SOLID?**
- A10: It follows SRP by separating storage logic, OCP by allowing new storage types without modifying existing code, and DIP by depending on abstractions (Storage interface) rather than concrete implementations:
  - S: Each class has one responsibility
  - O: New storage types added without modifying selector
  - L: All storages interchangeable
  - I: Minimal interfaces
  - D: Selector depends on Storage, not implementations