# Exercise 3: Session Management and Authentication

## Overview

Added comprehensive user authentication system with login/logout functionality, request-level security filtering, and
application lifecycle management. Extended the layered architecture to include User management with centralized security
enforcement.

## What We Added

- **User Model** and repository layer
- **Authentication Service** with business logic
- **Login/Logout Servlets** for session management
- **Custom Exception Classes** for better error handling
- **Session-based state management**
- **LoginFilter** for automatic authentication enforcement
- **Configurable filter** with init parameters
- **WebListener** for application lifecycle management
- **ServletContext** for application-wide state tracking

## 1. User Model - New Domain Object

### User Entity

```java

@Data
@AllArgsConstructor
public class User {
    private String username;
    private String password;
    private String name;
    private String surname;
}
```

**Key points:**

- Simple POJO with `@Data` for boilerplate generation
- Username serves as unique identifier
- Plain text password (will be improved in later exercises)
- Basic user profile information

## 2. Extended DataHolder - Multi-Entity Storage

### Updated Bootstrap Data

```java

@Component
public class DataHolder {
    public static List<Category> categories = null;
    public static List<User> users = null;

    @PostConstruct
    public void init() {
        categories = new ArrayList<>();
        categories.add(new Category("Movies", "Movies Category"));
        categories.add(new Category("Books", "Books Category"));

        users = new ArrayList<>();
        users.add(new User("elena.atanasoska", "ea", "Elena", "Atanasoska"));
        users.add(new User("darko.sasanski", "ds", "Darko", "Sasanski"));
        users.add(new User("ana.todorovska", "at", "Ana", "Todorovska"));
    }
}
```

**What changed:**

- Added `users` list alongside existing `categories`
- Pre-populated with 3 test users
- Simple passwords for demo purposes

## 3. Custom Exception Classes - Domain-Specific Errors

### Business Logic Exceptions

```java
public class InvalidArgumentsException extends RuntimeException {
    public InvalidArgumentsException() {
        super("Invalid arguments exception");
    }
}

public class InvalidUserCredentialsException extends RuntimeException {
    public InvalidUserCredentialsException() {
        super("Invalid user credentials exception");
    }
}

public class PasswordsDoNotMatchException extends RuntimeException {
    public PasswordsDoNotMatchException() {
        super("Passwords do not match exception");
    }
}
```

**Why custom exceptions:**

- **Specific error types** for different business scenarios
- **Clear error messages** for users
- **Type safety** - can catch specific exceptions
- **Business logic separation** - not just generic `IllegalArgumentException`

## 4. User Repository Layer

### Repository Interface

```java
public interface UserRepository {
    Optional<User> findByUsername(String username);

    Optional<User> findByUsernameAndPassword(String username, String password);

    User save(User user);

    void deleteByUsername(String username);
}
```

### Repository Implementation

```java

@Repository
public class InMemoryUserRepositoryImpl implements UserRepository {
    @Override
    public Optional<User> findByUsername(String username) {
        return DataHolder.users.stream()
                .filter(u -> u.getUsername().equals(username))
                .findFirst();
    }

    @Override
    public Optional<User> findByUsernameAndPassword(String username, String password) {
        return DataHolder.users.stream()
                .filter(u -> u.getUsername().equals(username) && u.getPassword().equals(password))
                .findFirst();
    }

    @Override
    public User save(User user) {
        // Remove existing user with same username (prevents duplicates)
        DataHolder.users.removeIf(u -> u.getUsername().equals(user.getUsername()));
        DataHolder.users.add(user);
        return user;
    }

    @Override
    public void deleteByUsername(String username) {
        DataHolder.users.removeIf(u -> u.getUsername().equals(username));
    }
}
```

**Repository features:**

- **Authentication query** - `findByUsernameAndPassword()` for login
- **User lookup** - `findByUsername()` for registration checks
- **CRUD operations** - save, delete by username
- **Optional return types** - safe null handling

## 5. Authentication Service Layer

### Service Interface

```java
public interface AuthService {
    User login(String username, String password);

    User register(String username, String password, String repeatPassword, String name, String surname);
}
```

### Service Implementation

```java

@Service
public class AuthServiceImpl implements AuthService {
    private final UserRepository userRepository;

    public AuthServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public User login(String username, String password) {
        // Business rule: username and password cannot be null or empty
        if (username == null || username.isEmpty() || password == null || password.isEmpty()) {
            throw new InvalidArgumentsException();
        }

        // Find user or throw exception if credentials invalid
        return this.userRepository.findByUsernameAndPassword(username, password)
                .orElseThrow(InvalidUserCredentialsException::new);
    }

    @Override
    public User register(String username, String password, String repeatPassword, String name, String surname) {
        // Business rule: none of the fields can be null or empty
        if (username == null || username.isEmpty() ||
                password == null || password.isEmpty() ||
                repeatPassword == null || repeatPassword.isEmpty() ||
                name == null || name.isEmpty() ||
                surname == null || surname.isEmpty()) {
            throw new InvalidArgumentsException();
        }

        // Business rule: passwords must match
        if (!password.equals(repeatPassword)) {
            throw new PasswordsDoNotMatchException();
        }

        return this.userRepository.save(new User(username, password, name, surname));
    }
}
```

**Service responsibilities:**

- **Input validation** - null/empty checks for all fields
- **Business rules** - password confirmation matching
- **Authentication logic** - credential verification
- **Exception handling** - throws specific business exceptions
- **User creation** - handles registration process

- Note: We implement a few business rules:
  - When we login a user, username and password cannot be null or empty.
  - When we register a user, none of the fields (username, password, repeatPassword, name, surname) can be null or empty.
  - When we register a user, password and repeatPassword must match.

## 6. Login Servlet - Session Management

### Login Controller

```java

@WebServlet(name = "loginServlet", urlPatterns = "/login")
public class LoginServlet extends HttpServlet {
    private final AuthService authService;
    private final SpringTemplateEngine templateEngine;

    public LoginServlet(AuthService authService, SpringTemplateEngine templateEngine) {
        this.authService = authService;
        this.templateEngine = templateEngine;
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        IWebExchange webExchange = JakartaServletWebApplication
                .buildApplication(req.getServletContext())
                .buildExchange(req, resp);

        WebContext context = new WebContext(webExchange);
        templateEngine.process("login.html", context, resp.getWriter());
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        IWebExchange webExchange = JakartaServletWebApplication
                .buildApplication(req.getServletContext())
                .buildExchange(req, resp);

        WebContext context = new WebContext(webExchange);

        String username = req.getParameter("username");
        String password = req.getParameter("password");

        User user;
        try {
            user = this.authService.login(username, password);
        } catch (RuntimeException e) {
            // On login failure, re-render login page with error
            context.setVariable("error", e.getMessage());
            templateEngine.process("login.html", context, resp.getWriter());
            return;
        }

        // On successful login, store user in session and redirect
        req.getSession().setAttribute("user", user);
        resp.sendRedirect("/servlet/category");
    }
}
```

**Login flow:**

1. **GET** - Display login form
2. **POST** - Process login attempt
3. **Success** - Store user in session, redirect to categories
4. **Failure** - Re-render form with error message

**Session management:**

- `req.getSession().setAttribute("user", user)` - stores logged-in user
- Session persists across requests until logout or expiry

## 7. Logout Servlet - Session Cleanup

### Simple Logout

```java

@WebServlet(name = "LogoutServlet", urlPatterns = "/logout")
public class LogoutServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        // Invalidate session and redirect to login
        req.getSession().invalidate();
        resp.sendRedirect("/login");
    }
}
```

**What happens:**

- `req.getSession().invalidate()` - destroys entire session
- Removes all session attributes (including user)
- Redirects to login page

## 8. Login Template - User Interface

### Login Form

```html
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Login</title>
</head>
<body>
<form th:method="POST" th:action="@{/login}">
    <label for="username">Username:</label>
    <input type="text" id="username" name="username"/><br/>

    <label for="password">Password:</label>
    <input type="password" id="password" name="password"/><br/><br/>

    <div th:if="${error != null}" class="error">
        <div style="color: red" th:text="${error}"></div>
    </div>

    <input type="submit" th:value="Submit">
</form>
</body>
</html>
```

**Form features:**

- **Proper form fields** - username (text) and password (hidden)
- **Thymeleaf action** - `@{/login}` generates correct URL
- **Error display** - shows authentication errors in red
- **Semantic HTML** - proper labels and input types

## 9. Enhanced Categories Template - User Info Display

### Session-Aware UI

```html

<th:block th:if="${session.user}">
    <h2>Info about user</h2>
    <div th:text="${session.user.username}"></div>
    <div th:text="${session.user.name}"></div>
    <div th:text="${session.user.surname}"></div>

    <div>
        <a th:href="@{/logout}">Logout</a>
    </div>
</th:block>
```

**Template features:**

- **Session access** - `${session.user}` reads from HTTP session
- **Conditional display** - only shows if user is logged in
- **User information** - displays username, name, surname
- **Logout link** - provides way to end session

**Key point:** Thymeleaf automatically exposes the HTTP session object as `${session}` in templates, so you can access
any session attribute directly without manually passing it from the servlet.

## Authentication Flow

### Login Process

1. User visits `/login` (GET)
2. Enters credentials and submits form (POST)
3. AuthService validates credentials
4. If valid: User stored in session, redirect to `/servlet/category`
5. If invalid: Error message displayed, stay on login page

### Session Protection

- Categories page shows user info if logged in
- Logout link available when authenticated
- Session invalidation clears all user data

### Test Users

- **elena.atanasoska** / **ea**
- **darko.sasanski** / **ds**
- **ana.todorovska** / **at**

## 10. Login Filter - Request Interception

### Basic Filter

```java

@WebFilter
public class LoginFilter implements Filter {
    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest req = (HttpServletRequest) servletRequest;
        HttpServletResponse resp = (HttpServletResponse) servletResponse;

        User loggedInUser = (User) req.getSession().getAttribute("user");
        String path = req.getServletPath();

        if (loggedInUser == null && !path.equals("/login")) {
            // Redirect to login if not authenticated
            resp.sendRedirect("/login");
        } else {
            // Continue with request
            filterChain.doFilter(req, resp);
        }
    }
}
```

### Enhanced Filter with Configuration

```java

@WebFilter(
        filterName = "auth-filter",
        urlPatterns = "/*",
        dispatcherTypes = {DispatcherType.REQUEST, DispatcherType.FORWARD},
        initParams = @WebInitParam(name = "ignore-path", value = "/login")
)
public class LoginFilter implements Filter {
    private String ignorePath;

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        Filter.super.init(filterConfig);
        this.ignorePath = filterConfig.getInitParameter("ignore-path");
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest req = (HttpServletRequest) servletRequest;
        HttpServletResponse resp = (HttpServletResponse) servletResponse;

        User loggedInUser = (User) req.getSession().getAttribute("user");
        String path = req.getServletPath();

        if (loggedInUser == null && !this.ignorePath.startsWith(path)) {
            resp.sendRedirect("/login");
        } else {
            filterChain.doFilter(req, resp);
        }
    }
}
```

**Filter Evolution:**

### Version 1 - Basic Protection

- **Hard-coded exception** - only `/login` allowed without authentication
- **Simple logic** - redirect if not logged in and not on login page
- **Basic servlet filter** - implements Filter interface

### Version 2 - Configurable Protection

- **Configuration-driven** - `ignore-path` parameter instead of hard-coded
- **URL pattern matching** - `urlPatterns = "/*"` intercepts all requests
- **Dispatcher types** - handles REQUEST and FORWARD dispatches
- **Flexible ignore logic** - `ignorePath.startsWith(path)` for pattern matching

**Filter annotations explained:**

- `filterName = "auth-filter"` - gives filter a name for debugging
- `urlPatterns = "/*"` - applies to all URLs
- `dispatcherTypes = {REQUEST, FORWARD}` - intercepts normal requests and forwards
- `initParams = @WebInitParam(...)` - configuration parameter

**Key improvements in Version 2:**

- **Configurable ignore paths** - easy to add more public endpoints
- **Better pattern matching** - `startsWith()` instead of `equals()`
- **Proper filter configuration** - uses init parameters
- **More explicit mapping** - clear URL patterns and dispatcher types

## Filter Request Flow

### Authentication Check Process

```
1. User requests any URL (e.g., /servlet/category)
2. LoginFilter intercepts request BEFORE servlet
3. Check: Is user in session?
   - YES: Continue to servlet (filterChain.doFilter())
   - NO: Is path in ignore list?
     - YES: Continue to servlet
     - NO: Redirect to /login
```

### Why Filters Are Better Than Manual Checks

- **Centralized security** - one place for all authentication logic
- **Automatic protection** - applies to ALL requests without code changes
- **Early interception** - stops unauthorized requests before reaching servlets
- **Separation of concerns** - security logic separate from business logic

## 11. Application Context Management - WebListener

### ServletContext Listener

```java

@WebListener
public class ApplicationContextListener implements ServletContextListener {
    @Override
    public void contextInitialized(ServletContextEvent sce) {
        ServletContextListener.super.contextInitialized(sce);
        sce.getServletContext().setAttribute("userViews", 0);
    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {
        ServletContextListener.super.contextDestroyed(sce);
        sce.getServletContext().removeAttribute("userViews");
    }
}
```

### Updated CategoryServlet - Using ServletContext

```java

@Override
protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
    // ... existing context setup ...

    // Update and retrieve the user views count from the servlet context
    Integer userViews = (Integer) getServletContext().getAttribute("userViews");
    getServletContext().setAttribute("userViews", userViews + 1);
    context.setVariable("userViews", userViews);

    springTemplateEngine.process("categories.html", context, resp.getWriter());
}
```

### Template Display - Application-Wide Counter

```html

<th:block>
    <div th:text="${userViews?:0}"></div>
</th:block>
```

**ServletContext vs Session vs Request:**

| Scope              | Lifetime              | Shared Across                  | Use Case                           |
|--------------------|-----------------------|--------------------------------|------------------------------------|
| **ServletContext** | Application lifecycle | All users, all sessions        | Global counters, app config        |
| **Session**        | User session          | Single user, multiple requests | User authentication, shopping cart |
| **Request**        | Single request        | Single request only            | Form data, request-specific data   |

**WebListener functionality:**

- `@WebListener` - automatically registered when app starts
- `contextInitialized()` - called when application starts up
- `contextDestroyed()` - called when application shuts down
- **ServletContext** - application-wide storage shared by all users

**Key concepts:**

- **Application-level state** - data shared across all users
- **Lifecycle management** - initialize/cleanup resources
- **Global counters** - track application-wide metrics
- **Thread-safe considerations** - multiple users accessing same data

**Use cases for ServletContext:**

- Application configuration settings
- Global counters (total page views, active users)
- Shared resources (database connections, caches)
- Application-wide feature flags

## What We Gained

### Complete Web Application Lifecycle Management

- **Application startup/shutdown** - WebListener handles lifecycle events
- **Global state management** - ServletContext for application-wide data
- **Multi-scope data** - Request, Session, and Application level storage
- **User authentication** - credential verification
- **Session management** - stateful user tracking
- **Proper logout** - secure session cleanup

### Better Error Handling

- **Custom exceptions** - domain-specific error types
- **User-friendly messages** - clear feedback on login failures
- **Exception propagation** - service → controller → template

### Extended Architecture

- **Multi-entity support** - Users alongside Categories
- **Authentication service** - dedicated business logic layer
- **Session-aware templates** - dynamic UI based on auth state