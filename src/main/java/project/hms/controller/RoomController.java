package project.hms.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import project.hms.model.Room;
import project.hms.model.enums.RoomStatus;
import project.hms.repository.RoomRepository;

import javax.validation.Valid;
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


    @GetMapping("/edit")
    public String edit(@RequestParam(value = "id", required = false) Integer id,
                       Model model) throws Exception {

        if (id != null) {

            Optional<Room> roomOptional = repository.findById(id);
            if (!roomOptional.isPresent()) {
                throw new Exception("Error");
            }

            model.addAttribute("room", roomOptional.get());
        } else {
            model.addAttribute("room", new Room());
        }

        return "room/edit";
    }

    @PostMapping("/edit")
    public String editPOST(@ModelAttribute("room") @Valid Room room, BindingResult result) {

        if (room.getStatus() == null) {
            room.setStatus(RoomStatus.EMPTY);
        }

        Room savedRoom = repository.save(room);

        return "redirect:/admin/room/info?id=" + savedRoom.getId();
    }

}