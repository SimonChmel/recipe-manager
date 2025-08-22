# Recipe Manager App

A **Spring Boot + JPA + MySQL web application** for managing recipes,
ingredients, and users.\
The app supports ingredient replacement suggestions based on nutritional
and chemical similarity.\
It features both **REST APIs** and **JSP-based web views** for browser
management (with roles and authentication).

------------------------------------------------------------------------

## Features

### User Management

-   Register new users
-   Edit user profiles (username, email, password, role)
-   Delete users (admin only)
-   Role-based security with Spring Security (`USER`, `ADMIN`)

### Recipe Management

-   Create, update, and delete recipes
-   Add or remove ingredients with quantity and unit
-   View detailed recipes with all ingredient information

### Ingredient Replacement

-   Automatically suggests alternatives for missing ingredients
-   Similarity is based on:
    -   Macronutrients (calories, protein, fat, carbs)
    -   Basic chemical properties (pH, moisture, texture)
-   Extensible for AI-based similarity or external APIs

### Technology Stack

-   **Spring Boot 3** (REST + MVC hybrid)
-   **Spring Data JPA** with **MySQL**
-   **Spring Security** (BCrypt password encoding)
-   **JSP + JSTL** front-end pages
-   **JUnit 5** unit tests (service layer)
-   **Lombok** for boilerplate code reduction

------------------------------------------------------------------------

## Project Structure

    src/main/java/com/recipemanager
     ├── config         # Security and general app configuration
     ├── controller     # REST API controllers + JSP Web controllers
     ├── dto            # DTOs for data transfer to/from web layer
     ├── model          # Entities (User, Recipe, Ingredient, RecipeIngredient)
     ├── repository     # Spring Data JPA repositories
     ├── service        # Business logic (UserService, RecipeService, IngredientService)
     └── util           # Helper classes (mappers, similarity calculation)

    src/main/webapp/WEB-INF/views
     ├── layout/        # Header, footer JSPF fragments
     ├── users/         # List, form pages for user management
     ├── recipes/       # List, form, detail pages for recipe management
     └── error.jsp      # Global error page

------------------------------------------------------------------------

## Database Setup

1.  Install MySQL and create an empty database:

    ``` sql
    CREATE DATABASE recipe_manager CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;
    ```

2.  Update `application.properties`:

    ``` properties
    spring.datasource.url=jdbc:mysql://localhost:3306/recipe_manager
    spring.datasource.username=root
    spring.datasource.password=yourpassword
    spring.jpa.hibernate.ddl-auto=update
    ```

3.  App will create required tables automatically at startup.

------------------------------------------------------------------------

## Running the App

1.  **Build and start**:

    ``` bash
    ./mvnw spring-boot:run
    ```

2.  Visit **Web interface**:\
    <http://localhost:8080/users>\

3.  REST APIs are available under `/api/**` paths (e.g. `/api/users`,
    `/api/recipes`).

------------------------------------------------------------------------

## Default Accounts

*(if you preload data via `DataLoader`)*

-   **Admin user**:
    -   username: `admin`\
    -   password: `admin123`\
-   **Standard user**:
    -   username: `user`\
    -   password: `user123`

------------------------------------------------------------------------

## Testing

-   **Service tests** are written using JUnit 5 with in-memory data
    setup.

-   To run:

    ``` bash
    ./mvnw test
    ```

------------------------------------------------------------------------

## Next Steps / Future Enhancements

-   Migrate similarity calculation to AI model or external API.
-   Add image upload for recipes.
-   Implement paging/sorting for large recipe lists.
-   Switch to Thymeleaf or React frontend for richer UI.
-   Add role-based dashboards (admin vs. user).
