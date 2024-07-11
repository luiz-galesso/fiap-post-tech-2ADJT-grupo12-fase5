package com.fase5.techchallenge.fiap.msusuario.infrastructure.authentication.controller;

import com.fase5.techchallenge.fiap.msusuario.entity.usuario.model.Usuario;
import com.fase5.techchallenge.fiap.msusuario.entity.usuario.model.enums.UserRole;
import com.fase5.techchallenge.fiap.msusuario.infrastructure.authentication.controller.dto.AuthenticationDTO;
import com.fase5.techchallenge.fiap.msusuario.infrastructure.authentication.controller.dto.ResponseDTO;
import com.fase5.techchallenge.fiap.msusuario.infrastructure.authentication.controller.dto.UsuarioRegistroDTO;
import com.fase5.techchallenge.fiap.msusuario.infrastructure.usuario.repository.UsuarioRepository;
import com.fase5.techchallenge.fiap.msusuario.security.TokenService;
import com.fase5.techchallenge.fiap.msusuario.usecase.usuario.CadastrarUsuario;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;

@RestController
@RequestMapping("/authentication")
@Tag(name = "Autenticação", description = "Serviços para realizar o cadastro e autenticação de um usuário")
@AllArgsConstructor
public class AuthenticationController {


    private final AuthenticationManager authenticationManager;
    private final CadastrarUsuario cadastrarUsuario;
    private final TokenService tokenService;
    private final UsuarioRepository usuarioRepository;

    @Operation(summary = "Realiza o login", description = "Serviço utilizado para realizar o login de um usuário e retornar seu token de acesso para os demais serviços.")
    @PostMapping("/login")
    public ResponseEntity login(@RequestBody @Valid AuthenticationDTO dto){
        var usernamePassword = new UsernamePasswordAuthenticationToken(dto.email(), dto.password());
        var auth = this.authenticationManager.authenticate(usernamePassword);
        var token = tokenService.generateToken((Usuario) auth.getPrincipal());
        return ResponseEntity.ok(new ResponseDTO(token));
    }

    @Operation(summary = "Cadastra um novo usuário", description = "Serviço utilizado para cadastrar um novo usuário.")
    @PostMapping("/cadastro")
    public ResponseEntity register(@RequestBody @Valid UsuarioRegistroDTO dto){
        if(this.usuarioRepository.findByEmail(dto.email()) != null) return ResponseEntity.badRequest().build();

        String encryptedPassword = new BCryptPasswordEncoder().encode(dto.password());
        Usuario usuario = new Usuario(dto.email(), dto.nome(), LocalDateTime.now(), encryptedPassword, UserRole.valueOf(dto.role()), dto.dataNascimento());

        this.cadastrarUsuario.execute(usuario);

        return ResponseEntity.ok().build();
    }
}



