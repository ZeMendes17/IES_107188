package ies.lab3.lab3_1.controllers;

import ies.lab3.lab3_1.entities.Person;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import ies.lab3.lab3_1.repositories.PersonRepository;

@Controller
public class PersonController {

    private final PersonRepository personRepository;

    // @Autowired
    public PersonController(PersonRepository personRepository) {
        this.personRepository = personRepository;
    }

    @GetMapping("/index")
    public String showPersonList(Model model) {
        model.addAttribute("people", personRepository.findAll());
        return "index";
    }

    @GetMapping("/signup")
    public String showSignUpForm(Person person) {
        return "add-person";
    }

    @PostMapping("/addperson")
    public String addPerson(@Valid Person person, BindingResult result, Model model) {
        if (result.hasErrors()) {
            return "add-person";
        }

        personRepository.save(person);
        return "redirect:/index";
    }

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
}
