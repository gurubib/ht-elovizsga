package hu.gurubib.ht.elovizsga.task2.controllers;

import hu.gurubib.ht.elovizsga.task2.dao.models.User;
import hu.gurubib.ht.elovizsga.task2.dao.repositories.UserRepository;
import hu.gurubib.ht.elovizsga.task2.domain.models.RegistrationForm;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.validation.Valid;
import java.util.Optional;

@Controller
public class RegistrationController {

    private final Logger logger = LoggerFactory.getLogger(RegistrationController.class);

    private final UserRepository repository;

    @Autowired
    public RegistrationController(UserRepository repository) {
        this.repository = repository;
    }

    @GetMapping("/")
    public String showRegistrationForm(RegistrationForm registrationForm) {
        return "registration";
    }

    @PostMapping("/")
    public String register(@Valid RegistrationForm registrationForm, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            logger.info("Validation errors occurred!");
            return "registration";
        }

        logger.info("Registering user with email: {}", registrationForm.getEmail());
        final boolean userIsRegistered = repository.findByEmail(registrationForm.getEmail()).isPresent();
        if (!userIsRegistered) {
            repository.save(new User(registrationForm.getEmail()));
        }

        return "redirect:/welcome?email=" + registrationForm.getEmail();
    }

    @GetMapping("/welcome")
    public String showWelcomePage(@RequestParam(required = false) String email, Model model) {
        final Optional<User> foundUser = repository.findByEmail(email);
        if (foundUser.isEmpty()) {
            return "redirect:/";
        }

        final User user = foundUser.get();
        final boolean readTerms = user.isReadTermsAndConditions();
        if (!readTerms) {
            user.setReadTermsAndConditions(true);
            repository.save(user);
        }

        model.addAttribute("readTerms", readTerms);
        return "welcome";
    }

}
