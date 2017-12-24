package sec.project.controller;

import java.io.IOException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import sec.project.domain.Signup;
import sec.project.repository.SignupRepository;

@Controller
public class SignupController {

    @Autowired
    private SignupRepository signupRepository;

    @RequestMapping("*")
    public String defaultMapping() {
        return "redirect:/form";
    }

    @RequestMapping(value = "/form", method = RequestMethod.GET)
    public String loadForm(Authentication authentication, Model model) {
        model.addAttribute("name", authentication == null ? "none" : authentication.getName());
        model.addAttribute("isAuthenticated", authentication != null && authentication.isAuthenticated());
        return "form";
    }

    @RequestMapping(value = "/form", method = RequestMethod.POST)
    public String submitForm(Model model, @RequestParam String name, @RequestParam String address) {
        signupRepository.save(new Signup(name, address));
        model.addAttribute("name", name);
        return "done";
    }
    
    @RequestMapping(value = "/list", method = RequestMethod.GET)
    public String list(Model model) {
        model.addAttribute("list", signupRepository.findAll());
        return "list";
    }
    
    @RequestMapping(value = "/files/{fileName:.+}", method = RequestMethod.GET)
    public ResponseEntity<InputStreamResource> getFile(@PathVariable("fileName") String fileName) throws IOException {
        ClassPathResource file = new ClassPathResource(fileName);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.parseMediaType("text/plain"));
        headers.add("Access-Control-Allow-Origin", "*");
        headers.add("Access-Control-Allow-Methods", "GET, POST, PUT");
        headers.add("Access-Control-Allow-Headers", "Content-Type");
        headers.add("Content-Disposition", "filename=" + fileName);
        headers.add("Cache-Control", "no-cache, no-store, must-revalidate");
        headers.add("Pragma", "no-cache");
        headers.add("Expires", "0");

        headers.setContentLength(file.contentLength());

        ResponseEntity<InputStreamResource> response = 
                new ResponseEntity<InputStreamResource>(new InputStreamResource(file.getInputStream()), headers, HttpStatus.OK);
        
        return response;
    }

}
