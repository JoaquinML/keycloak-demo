package com.example.demo.controller;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;

import com.example.demo.repository.BookRepository;

import org.keycloak.KeycloakSecurityContext;
import org.keycloak.representations.IDToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class LibraryController{
	private final HttpServletRequest request;
    private final BookRepository bookRepository;

    @Autowired
    public LibraryController(HttpServletRequest request, BookRepository bookRepository){
        this.request = request;
        this.bookRepository = bookRepository;
    }

    @GetMapping(value = "/")
	public String getHome() {
		return "index";
	}

	@GetMapping(value = "/books")
	public String getBooks(Model model) {
		configCommonAttributes(model);
		model.addAttribute("books", bookRepository.readAll());
		return "books";
	}

	@GetMapping(value = "/manager")
	public String getManager(Model model) {
		configCommonAttributes(model);
		model.addAttribute("books", bookRepository.readAll());
		return "manager";
	}

	@GetMapping(value = "/logout")
	public String logout() throws ServletException {
		request.logout();
		return "redirect:/";
	}

	private void configCommonAttributes(Model model) {
		KeycloakSecurityContext auxContext = getKeycloakSecurityContext();
		IDToken aux= auxContext.getIdToken();
		String auxString = aux.getGivenName();
		model.addAttribute("name", auxString);
	}

	private KeycloakSecurityContext getKeycloakSecurityContext() {
		String auxName = KeycloakSecurityContext.class.getName();
		Object auxObject = request.getAttribute(auxName); //this is null
		return (KeycloakSecurityContext) auxObject;
	}
}