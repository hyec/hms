package project.hms.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import project.hms.model.Room;
import project.hms.model.enums.RoomStatus;
import project.hms.repository.RoomRepository;

import javax.validation.Valid;
import java.util.Optional;

@Controller
@RequestMapping("/admin/room")
public class AdminRoomController {

    private final RoomRepository repository;

    @Autowired
    public AdminRoomController(RoomRepository repository) {
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
    public String editPOST(@Valid Room room, BindingResult result) {

        Optional<Room> roomOptional = repository.findById(room.getId());

        if (roomOptional.isPresent()) {
            room.setStatus(roomOptional.get().getStatus());
        } else {
            room.setStatus(RoomStatus.EMPTY);
        }

        Room savedRoom = repository.save(room);

        return "redirect:/admin/room/info?id=" + savedRoom.getId();
    }

}
