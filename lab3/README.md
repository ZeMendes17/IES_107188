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

Let's create some GET/POST methods to see how this works in practice.
First we want to show the signup form:

```
@GetMapping("/signup")
public String showSignUpForm(Person person) {
    return "add-person";
}
```
> This GET method returns the template **add-person.html**.

Now we want to save the person in the database, for that we need a POST method:

```
@PostMapping("/addperson")
public String addPerson(@Valid Person person, BindingResult result, Model model) {
    if (result.hasErrors()) {
        return "add-person";
    }
    
    personRepository.save(person);
    return "redirect:/index";
}
```
> This method receives a **Person** object, validates it and saves it in the database. If the object is not valid, it redisplays the template **add-person.html**. If the object is valid, it saves it in the database and redirects to the template **index.html**.

It is also necessary to map the **/index** URL, in this case, it will show all the people in the database, thus we need to pass the list as an attribute:

```
@GetMapping("/index")
public String showPersonList(Model model) {
    model.addAttribute("people", personRepository.findAll());
    return "index";
}
```
> As we can see, the repository has a method **findAll()** that returns all the people in the database.

Finnaly we will map, **/edit/{id}**, **/update/{id}** and **/delete/{id}** URLs. The first one will show the template **update-person.html** with the person to edit, the second one will update the person in the database and the last one will delete the person from the database.

```
@GetMapping("/edit/{id}")
public String showUpdateForm(@PathVariable("id") long id, Model model) {
    Person person = personRepository.findById(id)
            .orElseThrow(() -> new IllegalArgumentException("Invalid person Id:" + id));

    model.addAttribute("person", person);
    return "update-person";
}

@PostMapping("/update/{id}")
public String updatePerson(@PathVariable("id") long id, @Valid Person person, BindingResult result, Model model) {
    if (result.hasErrors()) {
        person.setId(id);
        return "update-person";
    }

    personRepository.save(person);
    return "redirect:/index";
}

@GetMapping("/delete/{id}")
public String deletePerson(@PathVariable("id") long id, Model model) {
    Person person = personRepository.findById(id)
            .orElseThrow(() -> new IllegalArgumentException("Invalid person Id:" + id));
    personRepository.delete(person);
    return "redirect:/index";
}
```
> The **@PathVariable** annotation indicates that the method parameter should be bound to a URI template variable

> The **@Valid** annotation makes sure that the object passed in is valid according to the validation constraints we set in our Person class.

### The View Layer

At this point, we’ve implemented a functional controller class that performs CRUD operations on Person entities. Even so, there’s still a missing component in this schema: the view layer.

Under the src/main/resources/templates folder, we need to create the HTML templates required for displaying the signup form and the update form as well as rendering the list of persisted Person entities.

## Exercise c)

1. The “UserController” class gets an instance of “userRepository” through its constructor; how is this new repository instantiated?

- We use the annotation **@Autowired** to indicate that the constructor is autowired, this means that the Spring container will automatically inject an instance of the **UserRepository** class into the constructor when it creates the **UserController** class.
This feature enables you to inject the object dependency implicitly. It internally uses setter or constructor injection.

2. List the methods invoked in the “userRepository” object by the “UserController”. Where are these methods defined?

- The methods invoked in the **userRepository** object are **findAll()**, **findById()**, **save()** and **delete()**.
These methods are defined in the **CrudRepository** interface.

3. Where is the data being saved?

- The data is being saved in the **H2** database. By default, Spring Boot configures the application to connect to an in-memory store when using H2, so all data is lost when the application stops.

4. Where is the rule for the “not empty” email address defined?

- The rule for the “not empty” email address is defined in the **User** class with the annotation **@NotBlank** with the message **"Email is mandatory"**.

## Adding a new field to the entity

This can be done by adding a new attribute (e.g **phone number**) to the **Person** class, creating a getter, a setter and updating the contructor. Finnaly, we need to add the corresponding attribute to the templates (**index.html**, **add-person.html** and **update-person.html**).