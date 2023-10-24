# IES Lab 3

## Accessing Databases in SpringBoot

The Jakarta Persistence API (JPA) defines a standard interface to manage data over relational databases. Spring Data uses and enhances the JPA. When you use Spring Data your Java code is independent
from the specific database implementation.

As a specification, the Jakarta Persistence API is concerned with persistence, which loosely means any mechanism by which Java objects outlive the application process that created them. The JPA specification lets you define which objects should be persisted, and how they are persisted in your Java applications.

By itself, JPA is not a tool or framework; rather, it defines a set of concepts that guide implementers. While JPA's object-relational mapping (ORM) model was originally based on Hibernate, it has since evolved. Likewise, while JPA was originally intended for use with relational databases, some JPA implementations have been extended for use with NoSQL datastores.

The implementation of DAO layers that provide CRUD functionality on JPA entities can be a repetitive, time-consuming task that we want to avoid in most cases.
Luckily, Spring Boot makes it easy to create CRUD applications through a layer of standard JPA-based CRUD repositories.

### Creating a SpringBoot project with Spring Data

Using the Spring Initializr create a new project with the following dependencies:

- Spring Web
- Thymeleaf
- Spring Data JPA
- H2 Database
- Validation

### Creating an entity

First we create a class, for example, **Person** , with the annotation **@Entity**, implying that it is a JPA entity and it is mapped to a table named **person** in the database.

We create private attributes for the class, this attributes will be the columns of the table in the database. The annotation **@Id** indicates that the attribute is the primary key of the table. The annotation **@GeneratedValue** indicates that the value of the attribute is generated automatically (with **strategy=GenerationType.AUTO**). Normally, we use both annotations together to indicate the primary key (usually a long) of the table.

The annotation **NotBlank** indicates that the attribute cannot be empty. We can put a message to show when the attribute is empty, for example, **@NotBlank(message = "Name is mandatory")**.

### Creating a repository

At this point, our sample web application does nothing.
Spring Data JPA allows us to implement JPA-based repositories (a fancy name for the DAO pattern implementation) with minimal fuss.

Spring Data JPA is a key component of Spring Boot’s spring-boot-starter-data-jpa that makes it easy to add CRUD functionality through a powerful layer of abstraction placed on top of a JPA implementation. This abstraction layer allows us to access the persistence layer without having to provide our own DAO implementations from scratch.

To provide our application with basic CRUD functionality, in this case, on Person objects, we need to extend the **CrudRepository**, giving
it CRUD functionality:
```
@Repository
public interface PersonRepository extends CrudRepository<Person, Long> {}
```

And that’s it! By extending the CrudRepository interface, Spring Data JPA will provide implementations for the repository’s CRUD methods for us.

### The Controller Layer

Thanks to the layer of abstraction that spring-boot-starter-data-jpa places on top of the underlying JPA implementation, we can easily add some CRUD functionality to our web application through a basic web tier.

In our case, a single controller class will suffice for handling GET and POST HTTP requests and then mapping them to calls to our PersonRepository implementation:

```
@Controller
public class PersonController {

    private final PersonRepository personRepository;

    public PersonController(PersonRepository personRepository) {
        this.personRepository = personRepository;
    }

    // GET and POST methods here
}
```

The controller class relies on some of Spring MVC’s key features. For a detailed guide on Spring MVC, check out our Spring MVC tutorial.