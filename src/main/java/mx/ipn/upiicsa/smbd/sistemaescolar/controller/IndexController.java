package mx.ipn.upiicsa.smbd.sistemaescolar.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class IndexController {
    PasswordEncoder passwordEncoder;

    @Autowired
    public IndexController(PasswordEncoder passwordEncoder) {
        this.passwordEncoder = passwordEncoder;
    }


    @GetMapping("/")
    public String index(Authentication authentication, Model model) {
        String userName = authentication.getName();
        System.out.println("Usuario: " + userName);
        StringBuilder rol = new StringBuilder();
        for (GrantedAuthority authority : authentication.getAuthorities()) {
            rol.append(authority);
        }
        model.addAttribute("rol", rol.toString());

        return "index";
    }

    @GetMapping("/bcrypt/{txt}")
    @ResponseBody
    public String encrypt(@PathVariable("txt") String txt){
            return passwordEncoder.encode(txt);
    }

}
