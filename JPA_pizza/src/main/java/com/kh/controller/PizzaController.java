package com.kh.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.kh.entity.KH_pizza;
import com.kh.service.PizzaService;

@RestController
@RequestMapping("/api/pizza")
public class PizzaController {

	@Autowired
	private PizzaService pizzaService;
	
	
	@GetMapping
	public List<KH_pizza> getAllPizzas() {
		return pizzaService.getAllPizzas();
	}
	
	@PostMapping
	public KH_pizza createPizza(@RequestBody KH_pizza pizza) {
		return pizzaService.createPizza(pizza);
	}
	
	@GetMapping("/search")
	public List<KH_pizza> searchPizzas(@RequestParam("query") String query) {
		return pizzaService.searchPizzas(query);
	}
	
}
