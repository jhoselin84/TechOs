package br.com.bugnacio.techos.controller;

import java.util.Optional;

import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import br.com.bugnacio.techos.model.Cliente;
import br.com.bugnacio.techos.model.Equipamento;
import br.com.bugnacio.techos.model.TipoEquipamento;
import br.com.bugnacio.techos.repository.ClienteRepository;
import br.com.bugnacio.techos.repository.EquipamentoRepository;
import jakarta.validation.Valid;

@Controller
@RequestMapping("/equipamentos")
public class EquipamentoController {

    private final EquipamentoRepository equipamentoRepository;
    private final ClienteRepository clienteRepository;

    
    public EquipamentoController(EquipamentoRepository equipamentoRepository, ClienteRepository clienteRepository) {
        this.equipamentoRepository = equipamentoRepository;
        this.clienteRepository = clienteRepository;
    }

    //listar
    @GetMapping
    public String listar(Model model) {
        model.addAttribute("equipamentos", equipamentoRepository.findAll());
        return "equipamentos/lista";
    }

    //novo
     @GetMapping("/novo")
    public String novo(Model model) {
        model.addAttribute("equipamento", new Equipamento());
        carregarCombos(model);
        return "equipamentos/form";
    }

     // CREATE  e UPDATE
     @PostMapping("/salvar")
    public String salvar(
            @Valid Equipamento equipamento,
            BindingResult result,
            Model model,
            RedirectAttributes redirect) {

        boolean novoCadastro = equipamento.getId() == null;

        //
        if (result.hasErrors()) {
            carregarCombos(model);
            return "equipamentos/form";
        }

        equipamentoRepository.save(equipamento);

        if (novoCadastro) {
            redirect.addFlashAttribute("mensagem", "Equipamento cadastrado com sucesso!");
        } else {
            redirect.addFlashAttribute("mensagem", "Equipamento atualizado com sucesso!");
        }

        return "redirect:/equipamentos";
    }

    // UPDATE  
    @GetMapping("/{id}/editar")
    public String editar(@PathVariable("id") Long id, Model model, RedirectAttributes redirect) {
        Optional<Equipamento> equipamento = equipamentoRepository.findById(id);

        if (equipamento.isEmpty()) {
            redirect.addFlashAttribute("erro", "Equipamento não encontrado.");
            return "redirect:/equipamentos";
        }

        model.addAttribute("equipamento", equipamento.get());
        carregarCombos(model);
        return "equipamentos/form";
    }

    // exclui
    @PostMapping("/{id}/excluir")
    public String excluir(@PathVariable("id") Long id, RedirectAttributes redirect) {
        equipamentoRepository.deleteById(id);
        redirect.addFlashAttribute("mensagem", "Equipamento excluído.");
        return "redirect:/equipamentos";
    }

    // 
    private void carregarCombos(Model model) {
        model.addAttribute("clientes", clienteRepository.findAll(Sort.by("nome")));
        model.addAttribute("tipos", TipoEquipamento.values());
    }









    
}