package com.springwebiv.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class FuncionarioController {
	
	@RequestMapping("/funcionarios")
	public ModelAndView listar() {
		ModelAndView mv = new ModelAndView("ListaFuncionarios");
		mv.addObject("titulo", "Minha Lista de Funcionários Dinâmica");
		return mv;
	}

}



