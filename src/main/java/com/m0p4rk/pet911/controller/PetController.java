package com.m0p4rk.pet911.controller;

import com.m0p4rk.pet911.dto.PetDTO;
import com.m0p4rk.pet911.dto.UserSessionDTO;
import com.m0p4rk.pet911.service.PetService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/pets")
public class PetController {

    @Autowired
    private PetService petService;

    @Autowired
    private HttpSession session;

    @GetMapping
    public String getPets(Model model) {
        UserSessionDTO userSession = (UserSessionDTO) session.getAttribute("user");
        if (userSession == null) {
            return "redirect:/login";
        }
        List<PetDTO> pets = petService.findByUserId(userSession.getId());
        model.addAttribute("pets", pets);
        return "mypet";
    }

    @GetMapping("/new")
    public String showPetForm(Model model) {
        model.addAttribute("petDTO", new PetDTO());
        return "petform";
    }

    @PostMapping("/new")
    public String savePet(@ModelAttribute("petDTO") PetDTO petDTO) {
        UserSessionDTO userSession = (UserSessionDTO) session.getAttribute("user");
        if (userSession == null) {
            return "redirect:/login";
        }
        petDTO.setUserId(userSession.getId());
        petService.savePet(petDTO);
        return "redirect:/pets";
    }

    @GetMapping("/edit/{id}")
    public String showEditPetForm(@PathVariable Long id, Model model) {
        PetDTO petDTO = petService.findById(id);
        model.addAttribute("petDTO", petDTO);
        return "petform";
    }

    @PostMapping("/edit/{id}")
    public String updatePet(@PathVariable Long id, @ModelAttribute("petDTO") PetDTO petDTO) {
        petDTO.setId(id);
        petService.updatePet(petDTO);
        return "redirect:/pets";
    }

    @GetMapping("/delete/{id}")
    public String deletePet(@PathVariable Long id) {
        petService.deletePet(id);
        return "redirect:/pets";
    }
}
