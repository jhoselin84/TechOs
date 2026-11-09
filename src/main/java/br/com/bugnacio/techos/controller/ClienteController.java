package br.com.bugnacio.techos.controller;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import br.com.bugnacio.techos.model.Cliente;
import br.com.bugnacio.techos.repository.ClienteRepository;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;




// @Controller: bean do Spring, a recepcao dos pedidos de clientes.
@Controller
// @RequestMapping na classe: o comeco do endereco para TODOS os metodos daqui.
@RequestMapping("/clientes")
public class ClienteController {

    //o controle precisa de repositorio para buscar os cliente  no banco de dados
    private  final ClienteRepository repo;

    //
    public ClienteController(ClienteRepository repo){
        this.repo= repo;
    }

    // @GetMapping vazio = o proprio "/clientes".
    // Endereco completo: http://localhost:8080/clientes
    @GetMapping
    public String listar(Model model) {

        // A tela clientes/lista espera uma lista chamada "clientes".
        // Ainda nao temos banco, entao mandamos uma lista VAZIA (List.of()).
        // A tela mostra o "estado vazio". Na aula 5 essa lista vem do banco.
        model.addAttribute("clientes", repo.findAll());

        // SEM @ResponseBody: o texto devolvido e o nome da tela.
        // Abre: src/main/resources/templates/clientes/lista.html
        return "clientes/lista";
    }
    //Get /cliente/novo:mostar o formulario para criar um novo ccliente
@GetMapping ("/novo")
 public String novo(Model model) {
    // a tela cliente/form espera um objeto chamado "cliente"
    //mandamos um cliente VAZIO(ne Cliente())
    //A tela mostra o "estado vazio" Na aula 5esse cliente vem do banco
    model.addAttribute("cliente", new Cliente());
     //SEM @ResponseBody: o texto devolvido e o nome da tela
     //Abre: src/main/ resources/templates/clientes/form.html
     return "clientes/form";
 }

 
// para salvar um cliente
@PostMapping("/salvar")
public String salvar(Cliente cliente, RedirectAttributes redirect){

    // Verifica se o cliente possui um ID
    if (cliente.getId() == null) {
        // Se não possui ID, é um novo cliente
        redirect.addFlashAttribute("mensagem", "Cliente criado com sucesso!");
    } else {
        // Se possui ID, é uma atualização de cliente existente
        redirect.addFlashAttribute("mensagem", "Cliente atualizado com sucesso!");
    }

    // Salva o cliente no banco de dados.
    repo.save(cliente);

    // Redireciona para a lista de clientes.
    return "redirect:/clientes";
}


// para editar um cliente
@GetMapping("/{id}/editar")
public String editar(@PathVariable Long id, Model model){

    // Recebe o ID da URL e busca o cliente no banco de dados.
    Cliente cliente = repo.findById(id).get();

    // Envia os dados do cliente para a tela de edição.
    model.addAttribute("cliente", cliente);

    // Abre a tela de edição.
    return "clientes/form";
}

// para delete: Excluir Cliente

@PostMapping("/{id}/excluir")
public String excluir(@PathVariable Long id, RedirectAttributes redirect){

    // Busca o cliente no banco de dados.
    Cliente cliente = repo.findById(id).get();

    repo.delete(cliente);

    redirect.addFlashAttribute("mensagem","cliente excluido com sucesso!");

    return "redirect:/clientes";


}




}