package br.com.bugnacio.techos.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller 
public class EquipamentoController {
@GetMapping ("/equipamentos")
@ResponseBody 
public String Equipamento(){
    return "Listar Equipamentos";
}
}
