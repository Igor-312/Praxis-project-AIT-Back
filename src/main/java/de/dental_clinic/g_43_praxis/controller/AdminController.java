package de.dental_clinic.g_43_praxis.controller;

import de.dental_clinic.g_43_praxis.domain.dto.AdminDto;
import de.dental_clinic.g_43_praxis.domain.dto.ChangePasswordDto;
import de.dental_clinic.g_43_praxis.service.interfaces.AdminService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
@Transactional
public class AdminController {

    private final AdminService adminService;

    @PostMapping("/admin")
    public ResponseEntity<AdminDto> createAdmin(@RequestBody AdminDto adminDto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(adminService.createAdmin(adminDto));
    }

    @PatchMapping("/mypassword")
    public ResponseEntity<String> changePassword(@RequestBody ChangePasswordDto changePasswordDto) {
        adminService.changePassword(changePasswordDto);
        return ResponseEntity.ok("Password updated successfully");
    }

    @GetMapping("/admins")
    public ResponseEntity<List<AdminDto>> getAllAdmins() {
        List<AdminDto> admins = adminService.findAllAdmins();
        return ResponseEntity.ok(admins);
    }

    @DeleteMapping("/admin")
    public ResponseEntity<AdminDto> deleteAdmin(@RequestBody AdminDto adminDto) {
        return ResponseEntity.ok(adminService.deleteAdmin(adminDto));
    }

    @DeleteMapping("/adminbylogin/{login}")
    public ResponseEntity<AdminDto> killAdmin(@PathVariable String login) {
        return ResponseEntity.ok(adminService.killAdmin(login));
    }

//    For future
//    /**
//     * Delete an administrator by their ID.
//     *
//     * @param id the ID of the administrator to delete
//     * @return success message if the admin was deleted
//     */
//    @Operation(summary = "Delete an admin", description = "Delete an administrator by their unique ID.")
//    @ApiResponses(value = {
//            @ApiResponse(responseCode = "200", description = "Admin deleted successfully",
//                    content = @Content(mediaType = "application/json")),
//            @ApiResponse(responseCode = "404", description = "Admin not found",
//                    content = @Content(mediaType = "application/json"))
//    })
//    @DeleteMapping("/admin/{id}")
//    public ResponseEntity<String> deleteAdmin(@PathVariable Long id) {
//        adminService.deleteAdmin(id);
//        return ResponseEntity.ok("Admin deleted successfully");
//    }
}

