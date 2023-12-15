package com.kotkina.ContaclsWebDB.controllers;

import com.kotkina.ContaclsWebDB.entities.Contact;
import com.kotkina.ContaclsWebDB.services.ContactService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
@RequiredArgsConstructor
public class ContactController {

    private final ContactService contactService;

    @ModelAttribute("title")
    public String getTitlePage() {
        return "Contact Management Page";
    }

    @GetMapping("/")
    public String indexPage(Model model) {
        model.addAttribute("title", "Contact List");
        model.addAttribute("contacts", contactService.findAll());

        return "index";
    }

    @GetMapping("/contacts/create")
    public String createPage(Model model) {
        model.addAttribute("title", "New Contact");
        model.addAttribute("contact", new Contact());

        return "contact_form";
    }

    @GetMapping("/contacts/edit/{id}")
    public String updatePage(@PathVariable Long id, Model model) {
        Contact contact = contactService.findById(id);
        if (contact == null) {
            return "redirect:/";
        }

        model.addAttribute("title", "Update Contact");
        model.addAttribute("contact", contact);

        return "contact_form";
    }

    @PostMapping("/contacts/edit")
    public String createOrUpdateContact(@ModelAttribute Contact contact) {
        if (contact.getId() == 0) {
            contactService.save(contact);
        } else {
            contactService.update(contact);
        }

        return "redirect:/";
    }

    @GetMapping("/contacts/delete/{id}")
    public String deleteContact(@PathVariable Long id) {
        contactService.deleteById(id);

        return "redirect:/";
    }
}
