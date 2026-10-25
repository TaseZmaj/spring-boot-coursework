# Exercise 2

## Layered Architecture

### What We Did

Transformed the messy servlet code into a proper layered architecture with separation of concerns.

### Before vs After

- **Before**: Everything in one servlet (data, logic, presentation)
- **After**: Clean layers - Model, Repository, Service, Controller (servlet)

### 1. Model Layer - Extracted Category

#### From Inner Class to Standalone Model

```java
// OLD: Inner class in servlet
@Getter
@Setter
@AllArgsConstructor
class Category {
    private String name;
    private String description;
}

// NEW: Standalone model class
@Data
@AllArgsConstructor
public class Category {
    private String name;
    private String description;
}
```

**What changed:**

- Moved to separate package: `mk.ukim.finki.wp2025.model`
- `@Data` replaces `@Getter @Setter` + adds `toString()`, `equals()`, `hashCode()`
- Now reusable across the entire application

### 2. Data Layer - Bootstrap DataHolder

#### Centralized Data Storage

```java

@Component
public class DataHolder {
    public static List<Category> categories = null;

    @PostConstruct
    public void init() {
        categories = new ArrayList<>();
        categories.add(new Category("Movies", "Movies Category"));
        categories.add(new Category("Books", "Books Category"));
    }
}
```

**Key concepts:**

- `@Component` makes it a Spring-managed bean
  - this `DataHolder` component is our in memory data holder
- `@PostConstruct` runs after Spring creates the bean (replaces servlet `init()`)
  - on application startup, initialize the in-memory lists with predefined data
  - on each startup, the lists will be initialized with the same values and the previous values will be lost
- `static` list accessible from anywhere
- Single source of truth for data

### 3. Repository Layer - Data Access

#### Repository Interface

```java
public interface CategoryRepository {
    Category save(Category category);

    List<Category> findAll();

    Optional<Category> findByName(String name);

    List<Category> search(String text);

    void delete(String name);
}
```

#### Repository Implementation

```java

@Repository
public class InMemoryCategoryRepositoryImpl implements CategoryRepository {
    @Override
    public Category save(Category category) {
        // Remove existing with same name (prevents duplicates)
        DataHolder.categories.removeIf(c -> c.getName().equals(category.getName()));
        DataHolder.categories.add(category);
        return category;
    }

    @Override
    public List<Category> findAll() {
        return DataHolder.categories;
    }

    @Override
    public Optional<Category> findByName(String name) {
        return DataHolder.categories.stream()
                .filter(c -> c.getName().equals(name))
                .findFirst();
    }

    @Override
    public List<Category> search(String text) {
        return DataHolder.categories.stream()
                .filter(c -> c.getName().contains(text) ||
                        c.getDescription().contains(text))
                .toList();
    }

    @Override
    public void delete(String name) {
        DataHolder.categories.removeIf(c -> c.getName().equals(name));
    }
}
```

**What this layer does:**

- `@Repository` marks it as data access component
- Implements interface - easy to swap implementations later
- Uses Java Streams for filtering/searching
- `Optional<Category>` for safe null handling
- All CRUD operations in one place

### 4. Service Layer - Business Logic

#### Service Interface

```java
public interface CategoryService {
    List<Category> listCategories();

    Category create(String name, String description);

    Category update(String name, String description);

    void delete(String name);

    List<Category> searchCategories(String text);
}
```

#### Service Implementation

```java

@Service
public class CategoryServiceImpl implements CategoryService {
    private final CategoryRepository categoryRepository;

    public CategoryServiceImpl(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    @Override
    public Category create(String name, String description) {
        // Business rule: name and description cannot be null or empty
        if (name == null || name.isEmpty() || description == null || description.isEmpty()) {
            throw new IllegalArgumentException();
        }
        Category category = new Category(name, description);
        return this.categoryRepository.save(category);
    }

    @Override
    public List<Category> listCategories() {
        return this.categoryRepository.findAll();
    }

    // ... other methods delegate to repository
}
```

**Service layer responsibilities:**

- `@Service` marks it as business logic component
- Constructor injection of repository
- **Validation logic** - throws `IllegalArgumentException` for invalid input
- **Business rules** - what constitutes valid data
- Delegates actual data operations to repository

### 5. Controller Layer - Refactored Servlet

#### Clean Servlet Code

```java

@WebServlet(name = "CategoryServlet", urlPatterns = "/servlet/category")
public class CategoryServlet extends HttpServlet {
    private final SpringTemplateEngine springTemplateEngine;
    private final CategoryService categoryService;

    public CategoryServlet(SpringTemplateEngine springTemplateEngine, CategoryService categoryService) {
        this.springTemplateEngine = springTemplateEngine;
        this.categoryService = categoryService;
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        IWebExchange webExchange = JakartaServletWebApplication
                .buildApplication(getServletContext())
                .buildExchange(req, resp);

        WebContext context = new WebContext(webExchange);
        context.setVariable("ipAddress", req.getRemoteAddr());
        context.setVariable("userAgent", req.getHeader("user-agent"));
        context.setVariable("errorMessage", req.getParameter("errorMessage"));
        context.setVariable("categories", this.categoryService.listCategories());

        springTemplateEngine.process("categories.html", context, resp.getWriter());
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String name = req.getParameter("name");
        String description = req.getParameter("description");

        try {
            this.categoryService.create(name, description);
        } catch (IllegalArgumentException e) {
            resp.sendRedirect("/servlet/category?errorMessage=Invalid input for category");
            return;
        }

        resp.sendRedirect("/servlet/category");
    }
}
```

**What's different:**

- **No more data storage** - removed `List<Category> categories`
- **No more `init()`** - data initialization moved to DataHolder
- **No more validation** - moved to service layer
- **Constructor injection** - receives CategoryService
- **Exception handling** - catches service exceptions and shows error messages
- **Single responsibility** - only handles HTTP requests/responses

### 6. Error Handling Enhancement

#### Template Update

```html

<div th:if="${errorMessage != null}">
    <p th:text="${errorMessage}" style="color: red;"></p>
</div>
```

#### Controller Error Flow

```java
try{
        this.categoryService.create(name, description);
}catch(
IllegalArgumentException e){
        resp.

sendRedirect("/servlet/category?errorMessage=Invalid input for category");
    return;
            }
```

**Error handling pattern:**

- Service throws `IllegalArgumentException` for invalid data
- Controller catches exception
- Redirects with error message as URL parameter
- Template displays error message in red
