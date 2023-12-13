package com.attornatuschallenge.personapi.controllers;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import com.attornatuschallenge.personapi.entities.Address;
import com.attornatuschallenge.personapi.entities.Person;
import com.attornatuschallenge.personapi.entities.PersonResponseData;
import com.attornatuschallenge.personapi.services.PersonService;
import jakarta.validation.Valid;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
@RequestMapping(value = "/people")
public class PersonController {

    @Autowired
    private PersonService service;

    @GetMapping()
    public String listPeople(Model model) {
        List<PersonResponseData> people = service.findAll();
        model.addAttribute("people", people);
        System.out.println("Number of people: " + people.size());
        return "people"; // Update to "people" to match the template name
    }

    @PostMapping(value = "/{id}")
    public String updatePerson(@RequestParam Long id, @ModelAttribute @Valid Person person, BindingResult result, Model model) {
        System.out.println("Opa");
        System.out.println(person);
        if (result.hasErrors()) {
            // If validation fails, return to the update form page with error messages
            System.out.println("Erro");
            System.out.println("Validation errors: " + result.getAllErrors());
            return "updatePerson";
        }

        // Your logic to update the person
        service.update(id, person);

        // Redirect to the page showing the updated person or any other appropriate page
        return "redirect:/people";
    }

    @GetMapping(value = "/{id}/address")
    public ResponseEntity<List<Address>> getPersonAddresses(@PathVariable Long id) {
        Person person = service.findPersonAllInfoById(id);
        return ResponseEntity.ok().body(person.getAddresses());
    }

    @GetMapping(value = "/{id}/address/main")
    public ResponseEntity<Person> getPersonMainAddress(@PathVariable Long id) {
        Person response = service.mainAddressInfo(id);
        return ResponseEntity.ok().body(response);
    }

    @GetMapping(value = "/name")
    public ResponseEntity<List<Person>> findByName(@RequestParam String name) {
        return new ResponseEntity<>(service.findByName(name), HttpStatus.OK);
    }

    @PostMapping("/add")
    public String insert(HttpServletRequest request, HttpServletResponse response,
                         @ModelAttribute @Valid Person person, BindingResult result,
                         ModelMap model, RedirectAttributes redirectAttributes) {
        System.out.println("-----------------------------AQUI-------------------------------------------------------");
        System.out.println("Received Person: " + person);

        if (result.hasErrors()) {
            // Print out validation errors
            System.out.println("Validation errors: " + result.getAllErrors());

            // If validation fails, return to the form page with error messages
            return "addPerson";
        }

        // Your logic to save the person
        Person savedPerson = service.insert(person);

        // Redirect to the next page (e.g., /people/{id}/address/add)
        redirectAttributes.addAttribute("id", savedPerson.getId());
        return "redirect:/people/{id}/address/add";
    }

    @GetMapping("/add/person")
    public String showAddPersonPage(Model model) {
        // Assuming you create a new Person object and add it to the model
        model.addAttribute("person", new Person());
        return "addPerson";
    }

    @GetMapping("/{id}")
    public String showUpdatePersonPage(@PathVariable Long id, Model model) {
        // Retrieve the person by ID
        PersonResponseData person = service.findById(id);
        System.out.println(person);
        // Add the person to the model
        model.addAttribute("person", person);

        // Return the view name for the update page
        return "updatePerson";
    }

    @PostMapping("/delete")
    public String deletePerson(@RequestParam Long id) {
        // Your logic to delete the person and related addresses
        service.delete(id);

        // Redirect to the page showing the updated list of people
        return "redirect:/people";
    }
}
