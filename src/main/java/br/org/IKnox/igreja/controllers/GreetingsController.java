package br.org.IKnox.igreja.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import br.org.IKnox.igreja.repository.UsuarioRepository;
import br.org.IKnox.igreja.model.Usuario;

@RestController
public class GreetingsController {

	@Autowired //IC/CI/CDI - Injeção de dependência
	private UsuarioRepository usuarioRepository;
	
    @RequestMapping(value = "/{name}", method = RequestMethod.GET)
    @ResponseStatus(HttpStatus.OK)
    public String greetingText(@PathVariable String nome) {
        return "Olá " + nome + "! Bem-vindo à Igreja Presbiteriana do Brasil";
    }
    
	
	@RequestMapping(value = "/nome/{nome}", method = RequestMethod.GET)
    @ResponseStatus(HttpStatus.OK)
    public String retornaOlaMundo(@PathVariable String nome) {
		
		Usuario usuario = new Usuario();
		usuario.setNome(nome);
		usuarioRepository.save(usuario);  //Grava no Banco de Dados

        return "Seja bem-vindo " + nome + "!";
    }
	
	@GetMapping(value = "listatodos") //Nosso primeiro método de API
	@ResponseBody //Retorna os dados ao corpo da resposta
	public ResponseEntity<List<Usuario>> listaUsuario(){
		List<Usuario> usuarios = usuarioRepository.findAll(); //Executa a pesquisa no Banco de Dados
		return new ResponseEntity<List<Usuario>>(usuarios, HttpStatus.OK); //Retorna a lista em JSON
	}
	
	@PostMapping(value = "salvar") //Mapeia a url
	@ResponseBody //Retorna os dados ao corpo da resposta
	public ResponseEntity<Usuario> salvar(@RequestBody Usuario usuario){ //Recebe os dados para salvar
		Usuario user = usuarioRepository.save(usuario);
		return new ResponseEntity<Usuario>(user, HttpStatus.CREATED);
	}
		
	@DeleteMapping(value = "delete") //Mapeia a url
	@ResponseBody //Descrição da resposta
	public ResponseEntity<String> delete(@RequestParam Long idUsuario){ //Recebe o parâmetro para deletar
		usuarioRepository.deleteById(idUsuario);
		return new ResponseEntity<String>("Usuário deletado com sucesso", HttpStatus.OK);
	}	
	
	@GetMapping(value = "buscarUsuarioId") //Mapeia a url
	@ResponseBody //Descrição da resposta
	public ResponseEntity<Usuario> buscarUsuarioId(@RequestParam Long idUsuario){ //Recebe o parâmetro para deletar
		Usuario usuario = usuarioRepository.findById(idUsuario).get();
		return new ResponseEntity<Usuario>(usuario, HttpStatus.OK);
	}	
	
	@GetMapping(value = "buscarPorNome") //Mapeia a url
	@ResponseBody //Descrição da resposta
	public ResponseEntity<List<Usuario>> buscarPorNome(@RequestParam String nome){ //Recebe o parâmetro para deletar
		List<Usuario> usuario = usuarioRepository.buscaPorNome(nome.trim().toUpperCase());
		return new ResponseEntity<List<Usuario>>(usuario, HttpStatus.OK);
	}	
	
	@PutMapping(value = "atualizar") //Mapeia a url
	@ResponseBody //Descrição da resposta
	public ResponseEntity<?>atualizar(@RequestBody Usuario usuario){ //Recebe o parâmetro para deletar
		
		if(usuario.getIdUsuario() == 0) {
			return new ResponseEntity<String>("Id não informado para atualização.", HttpStatus.OK);
		}
		Usuario user = usuarioRepository.saveAndFlush(usuario);
		return new ResponseEntity<Usuario>(user, HttpStatus.OK);
	}
	
}
