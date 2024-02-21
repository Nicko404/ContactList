package ru.skillbox.contactapplications.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import ru.skillbox.contactapplications.model.Contact;
import ru.skillbox.contactapplications.service.IContactService;

@Controller("/")
@RequiredArgsConstructor
@Slf4j
public class MainController {

    private final IContactService contactService;

    @GetMapping
    public String index(Model model) {
        log.debug("MainController->index");
        model.addAttribute("contacts", contactService.findAll());
        return "index";
    }

    @GetMapping("/contact/create")
    public String showCreateForm(Model model) {
        log.debug("MainController->showCreateForm");
        model.addAttribute("title", "Add contact");
        model.addAttribute("contact", new Contact());
        model.addAttribute("path", "create");
        return "create";
    }

    @PostMapping("/contact/create")
    public String performCreateContact(@ModelAttribute Contact contact) {
        log.debug("MainController->preformCreateContact: {}", contact);
        contactService.save(contact);
        return "redirect:/";
    }

    @GetMapping("/contact/edit/{id}")
    public String showEditForm(@PathVariable Long id, Model model) {
        log.debug("MainController->showEditForm for ID: {}", id);
        model.addAttribute("contact", contactService.findById(id));
        model.addAttribute("title", "Edit contact");
        model.addAttribute("path", "edit/" + id);
        return "create";
    }

    @PostMapping("/contact/edit/{id}")
    public String performEditContact(@ModelAttribute Contact contact) {
        log.debug("MainController->performEditContact: {}", contact);
        contactService.update(contact);
        return "redirect:/";
    }

    @GetMapping("/contact/delete/{id}")
    public String deleteContact(@PathVariable Long id) {
        log.debug("MainController->deleteContact. ID: {}", id);
        contactService.deleteById(id);
        return "redirect:/";
    }

}
