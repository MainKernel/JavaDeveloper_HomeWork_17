package note.javadeveloper.controller;

import jakarta.servlet.http.HttpSession;
import note.javadeveloper.dto.NoteDto;
import note.javadeveloper.service.NoteService;
import note.javadeveloper.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@Controller
public class UserPageController {
    private NoteService noteService;
    private UserService userService;


    @Autowired
    private void set(NoteService noteService, UserService userService) {
        this.noteService = noteService;
        this.userService = userService;
    }

    @GetMapping("/user")
    public ModelAndView getUserPage(ModelAndView modelAndView,
                                    @ModelAttribute("userId") String userId,
                                    HttpSession session) {
        UserDetails principal = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String username = principal.getUsername();
        Long setUserId;
        if (userId.isBlank() || userId.isEmpty()) {
            setUserId = userService.findUserByUsername(username).getId();
            session.setAttribute("userId", setUserId);
        } else {
            setUserId = Long.parseLong(userId);
        }
        List<NoteDto> allNotes = noteService.getAllNotes(setUserId);
        modelAndView.addObject("notes", allNotes);
        modelAndView.setViewName("user/user");
        return modelAndView;
    }

    @GetMapping("/user/note/create")
    public ModelAndView getUserNoteCreatePage(ModelAndView modelAndView) {
        modelAndView.addObject("note", new NoteDto());
        modelAndView.setViewName("note/create");
        return modelAndView;
    }

    @PostMapping("/user/note/create")
    public String postUserNoteCreatePage(@ModelAttribute NoteDto noteDto, @SessionAttribute String userId) {
        noteService.saveNote(noteDto, Long.parseLong(userId));
        return "redirect:/user";
    }

    @GetMapping("/user/note/edit")
    public ModelAndView getUserNoteEditPage(ModelAndView modelAndView,
                                            @RequestParam("id") long id) {
        modelAndView.addObject("note", noteService.getNoteById(id));
        modelAndView.setViewName("note/edit");
        return modelAndView;
    }

    @PostMapping("/user/note/edit")
    public String postUserNoteEditPage(@ModelAttribute NoteDto noteDto, @SessionAttribute String userId) {
        noteService.editNote(noteDto, Long.parseLong(userId));
        return "redirect:/user";
    }

    @PostMapping("/user/note/delete")
    private String postUserNoteDeletePage(@RequestParam("id") long id) {
        noteService.deleteNoteById(id);
        return "redirect:/user";
    }
}
