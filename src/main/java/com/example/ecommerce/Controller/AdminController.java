package com.example.ecommerce.Controller;

import com.example.ecommerce.Service.AdminService;
import com.example.ecommerce.entity.Admin;
import com.example.ecommerce.repository.AdminRepository;
import jakarta.websocket.server.PathParam;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/admins")
public class AdminController {
    AdminService adminService;
    public AdminController(AdminService adminService) {
        this.adminService = adminService;
    }
    @GetMapping
    public List<Admin> getAllAdmins() {
        return adminService.getAllAdmins();
    }
    @GetMapping("/{id}")
    public Admin getAdminById(@PathVariable int id) {
        return adminService.getAdminById(id);
    }
    @PostMapping
    public void createAdmin(@RequestBody Admin admin) {
        adminService.addAdmin(admin);
    }
    @PutMapping("/{id}")
    public void updateAdmin(@RequestBody Admin admin, @PathVariable int id) {
        adminService.updateAdmin(id,admin);
    }
    @DeleteMapping("/{id}")
    public void deleteAdmin(@PathVariable Integer id) {
        adminService.deleteAdmin(id);
    }
}
