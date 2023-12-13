package com.attornatuschallenge.personapi.controllers;

import com.attornatuschallenge.personapi.entities.Address;
import com.attornatuschallenge.personapi.entities.Person;
import com.attornatuschallenge.personapi.services.AddressService;
import com.attornatuschallenge.personapi.services.PersonService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.Optional;

@Controller
@RequestMapping(value = "/people")
public class AddressController {

    @Autowired
    AddressService service;

    @Autowired
    PersonService personService;

    @PutMapping(value = "/address/{id}")
    public ResponseEntity<Address> update(@Valid @RequestBody Address address, @PathVariable Long id) {
        Address result = service.update(id, address);
        Person person = personService.findPersonAllInfoById(result.getPersonId());
        personService.update(person.getId(), person);
        return new ResponseEntity("Updated " + person.getName() +"'s address info", HttpStatus.OK);
    }

    @PostMapping(value = "/{id}/address/add")
    public String insert(HttpServletRequest request, HttpServletResponse response,
                         @RequestParam Long personId, @ModelAttribute @Valid Address address, BindingResult result,
                         ModelMap model, RedirectAttributes redirectAttributes) {
        // Retrieve the personId from the path

        if (personId == null) {
            // Handle the case where id is null
            return "redirect:/people"; // Or handle differently as needed
        }

        // Find the person by ID
        Person person = personService.findPersonAllInfoById(personId);

        // Set the person for the address
        address.setPerson(person);

        // Save the address
        service.insert(address, personId);

        // Update main address if needed
        if (address.getMain()) {
            person.setMainAddressId(address.getId());
            personService.update(personId, person);
        }

        return "redirect:/people";
    }


    @GetMapping("/{id}/address/add")
    public String showAddAddressForm(@PathVariable Long id, Model model) {
        model.addAttribute("personId", id);
        model.addAttribute("address", new Address());
        return "addAddress";
    }
}

