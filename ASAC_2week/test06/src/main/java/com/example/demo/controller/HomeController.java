package com.example.demo.controller;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import com.example.demo.model.USER;
import com.example.demo.repository.UserRepository;

import lombok.RequiredArgsConstructor;


@RequiredArgsConstructor//레포지토리 가져다 쓸 때 필요, getter, setter 안 선언해도 됨
@Controller
public class HomeController {
	private final UserRepository userRepository;
	
	//show
	@GetMapping("/home")
	public String goshow(Model model) {
		List<USER> users = userRepository.findAll();
        model.addAttribute("userList", users);
        return "content/home";
	}
	
	//add
	@GetMapping("/add")
	public String goadd(Model model) {
        model.addAttribute("user", new USER());
        return "content/add";
	}
	
	//save
	@PostMapping("/save")
	public String saveUser(@ModelAttribute USER user) {
		userRepository.save(user);
	    return "redirect:/home";
	}
	   
	//delete
	@GetMapping("/delete/{id}")
	public String deleteUser(@PathVariable String id) {
		userRepository.deleteById(id);
	    return "redirect:/home";
	}
	
	//update
    @GetMapping("/edit/{id}")
    public String goedit(@PathVariable String id, Model model) {
        USER user = userRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Invalid user id"));
        model.addAttribute("user", user);
        return "content/edit";
    }

    @PostMapping("/update/{id}")
    public String updateUser(@PathVariable String id, @ModelAttribute USER user) {
        user.setId(id);
        userRepository.save(user);
        return "redirect:/home";
    }
	
}