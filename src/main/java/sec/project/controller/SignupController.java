package sec.project.controller;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
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

    @PersistenceContext
    private EntityManager em;

    @PostConstruct
    public void init() {
        // add some content to the signup repository
        signupRepository.save(new Signup("Roger Rabbit", "roger.rabbit@toontown.com"));
        signupRepository.save(new Signup("Kevin Mitnick", "free@kevin.com"));
        signupRepository.save(new Signup("Baba Sonic", "baba@sonic.se"));
        signupRepository.save(new Signup("Bada Bing", "baba@boom.net"));
    }

    @RequestMapping("*")
    public String defaultMapping() {
        return "redirect:/form";
    }

    @RequestMapping(value = "/form", method = RequestMethod.GET)
    public String loadForm(Authentication authentication, Model model) {
        model.addAttribute("name", authentication.getName());
        model.addAttribute("isAdmin", authentication.getName().equals("admin"));
        return "form";
    }

    @RequestMapping(value = "/form", method = RequestMethod.POST)
    public String submitForm(Model model, @RequestParam String name, @RequestParam String address) {
        signupRepository.save(new Signup(name, address));
        model.addAttribute("name", name);
        model.addAttribute("address", address);
        return "done";
    }
    
    @RequestMapping(value = "/list", method = RequestMethod.GET)
    public String list(Model model) {
        model.addAttribute("list", signupRepository.findAll());
        return "list";
    }
    
    @RequestMapping(value = "/list", method = RequestMethod.POST)
    public String filter(Model model, @RequestParam String name) throws SQLException {
        List<Object[]> result = em.createQuery("SELECT name, address FROM Signup WHERE name LIKE '" + name + "%'").getResultList();

        List<Signup> list = new ArrayList<Signup>();
        for (Object[] item : result) {
            list.add(new Signup((String)item[0], (String)item[1]));
        }
        
        model.addAttribute("list", list);
        return "list";
    }
    
    @RequestMapping(value = "/delete", method = RequestMethod.POST)
    public String delete(@RequestParam String name) {
        Signup signup = signupRepository.findByName(name);
        signupRepository.delete(signup);

        return "redirect:list";
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
