package project.hms.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import project.hms.data.dto.SelectDto;
import project.hms.model.Room;
import project.hms.model.enums.RoomStatus;
import project.hms.repository.RoomRepository;
import project.hms.util.ModelTool;

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

    @GetMapping("/")
    public String index() {
        return "redirect:/admin/room/list";
    }

    @GetMapping("/list")
    @Secured({"CASHIER", "MANAGER"})
    public String list(@ModelAttribute SelectDto selectDto, Model model) {
        if (selectDto == null) {
            selectDto = new SelectDto();
        }

        model.addAttribute("sel", selectDto);
        model.addAttribute("rooms", repository.findAll());
        return "room/list";
    }

    @GetMapping("/info")
    @Secured({"CASHIER", "MANAGER"})
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
    @Secured({"MANAGER"})
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
    @Secured({"MANAGER"})
    public String editPOST(@Valid Room room, BindingResult result) {

        if (result.hasErrors()) {
            return "room/edit";
        }

        Room savedRoom;
        Optional<Room> roomOptional = repository.findById(room.getId());

        if (roomOptional.isPresent()) {
            savedRoom = roomOptional.get();
            ModelTool.merge(room, savedRoom);
            room = savedRoom;
        } else {
            room.setStatus(RoomStatus.EMPTY);
        }

        savedRoom = repository.save(room);

        return "redirect:/admin/room/info?id=" + savedRoom.getId();
    }

}
