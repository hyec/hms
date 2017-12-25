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

/**
 * 打扫房间的controller
 */
@Controller
@RequestMapping("/admin")
public class CleanRoomController {

    private final RoomRepository roomRepository;

    public CleanRoomController(RoomRepository roomRepository) {
        this.roomRepository = roomRepository;
    }

    /**
     * 列出房间信息
     */
    @GetMapping("/clean")
    @Secured({"ROLE_CLEANER", "ROLE_CASHIER", "ROLE_MANAGER"})
    public String clean(RedirectAttributes redirectAttributes) {
        redirectAttributes.addAttribute("multi", true);
        redirectAttributes.addAttribute("action", "/admin/clean");
        return "redirect:/admin/room/list";
    }

    /**
     * 将修改状态后的房间信息替换原数据库中的信息
     *
     * @param id 房间的id
     */
    @PostMapping("/clean")
    @Secured({"ROLE_CLEANER", "ROLE_CASHIER", "ROLE_MANAGER"})
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
