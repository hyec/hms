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

/**
 * 这是一个关于房间的列表查看，具体房间信息查看，具体房间信息编辑的类
 */
@Controller
@RequestMapping("/admin/room")
public class AdminRoomController {

    private final RoomRepository repository;

    @Autowired
    public AdminRoomController(RoomRepository repository) {
        this.repository = repository;
    }

    /**
     * 这个函数展示了房间的列表
     *
     * @return 房间列表页面
     */
    @GetMapping({"", "/"})
    public String index() {
        return "redirect:/admin/room/list";
    }

    /**
     * 这个函数展示了房间的列表
     * @param selectDto 选择信息表单
     * @return 房间列表页面
     */
    @GetMapping("/list")
    @Secured({"ROLE_CLEANER", "ROLE_CASHIER", "ROLE_MANAGER"})
    public String list(@ModelAttribute SelectDto selectDto, Model model) {
        if (selectDto == null) {
            selectDto = new SelectDto();
        }

        model.addAttribute("sel", selectDto);
        model.addAttribute("rooms", repository.findAll());
        return "admin/room/list";
    }

    /**
     * 展示具体房间信息
     * @param id 房间id
     * @return 具体房间信息页面
     * @throws Exception 当具有这个id的房间不存在时抛出
     */
    @GetMapping("/info")
    @Secured({"ROLE_CASHIER", "ROLE_MANAGER"})
    public String info(@RequestParam("id") Integer id,
                       Model model) throws Exception {

        Optional<Room> roomOptional = repository.findById(id);
        if (!roomOptional.isPresent()) {
            throw new Exception("Error");
        }

        model.addAttribute("room", roomOptional.get());
        return "admin/room/info";
    }

    /**
     * 展示具体房间信息编辑界面
     * @param id 房间id
     * @return 具体房间编辑界面
     * @throws Exception 当id不为空且不存在具有这个id的房间时抛出
     */
    @GetMapping("/edit")
    @Secured({"ROLE_MANAGER"})
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

        return "admin/room/edit";
    }

    /**
     * 获得被编辑的房间并展示编辑后的房间信息
     * @param room 被编辑的房间实体
     * @param result 检查结果
     * @return 被编辑后的信息界面
     */
    @PostMapping("/edit")
    @Secured({"ROLE_MANAGER"})
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
