package project.hms.controller;

import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import project.hms.model.Room;
import project.hms.model.enums.RoomStatus;
import project.hms.repository.RoomRepository;

import java.util.Optional;

@Controller
@RequestMapping("/admin")
public class CleanRoomController {

    private final RoomRepository roomRepository;

    public CleanRoomController(RoomRepository roomRepository) {
        this.roomRepository = roomRepository;
    }

    @GetMapping("/clean")
    @Secured({"CLEANER", "CASHIER", "MANAGER"})
    public String clean(RedirectAttributes redirectAttributes) {
        redirectAttributes.addAttribute("multi", true);
        redirectAttributes.addAttribute("action", "/admin/clean");
        return "redirect:/admin/room/list";
    }

    @PostMapping("/clean")
    @Secured({"CLEANER", "CASHIER", "MANAGER"})
    public String cleanPOST(@RequestParam("room") Integer[] id) {
        Optional<Room> roomOptional;
        Room room;

        for (Integer i : id) {
            roomOptional = roomRepository.findById(i);
            if (roomOptional.isPresent()) {
                room = roomOptional.get();
                if (room.getStatus().equals(RoomStatus.DIRTY)) {
                    room.setStatus(RoomStatus.EMPTY);
                    roomRepository.save(room);
                }
            }
        }

        return "redirect:/admin/room/list";
    }


}
