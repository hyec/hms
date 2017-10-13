package project.hms.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import project.hms.model.Room;
import project.hms.repository.RoomRepository;

import java.util.Optional;

@Controller
@RequestMapping("/admin/room")
public class RoomController {

    private final RoomRepository repository;

    @Autowired
    public RoomController(RoomRepository repository) {
        this.repository = repository;
    }

    @GetMapping("/list")
    public String list(Model model) {
        model.addAttribute("rooms", repository.findAll());
        return "room/list";
    }

    @GetMapping("/info")
    public String info(@RequestParam("id") Integer id,
                       Model model) throws Exception {

        Optional<Room> roomOptional = repository.findById(id);
        if (!roomOptional.isPresent()) {
            throw new Exception("Error");
        }

        model.addAttribute("room", roomOptional.get());
        return "room/info";
    }

}
