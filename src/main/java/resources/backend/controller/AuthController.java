package resources.backend.controller;

import java.util.stream.Collectors;

import jakarta.validation.Valid;
import resources.backend.config.jwt.JwtUtils;
import resources.backend.model.ERoleModel;
import resources.backend.model.UserModel;
import resources.backend.payload.request.LoginRequest;
import resources.backend.payload.request.PasswordUpdateRequest;
import resources.backend.payload.request.SignupRequest;
import resources.backend.payload.response.JwtResponse;
import resources.backend.payload.response.MessageResponse;
import resources.backend.repos.UserRepos;
import resources.backend.service.UserDetailsImpl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/auth")
public class AuthController {
    @Autowired
    AuthenticationManager authenticationManager;

    @Autowired
    UserRepos userRepository;

    @Autowired
    PasswordEncoder encoder;

    @Autowired
    JwtUtils jwtUtils;

    @PostMapping("/updatePassword/{id}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<?> updatePassword(@PathVariable Long id, @Valid @RequestBody PasswordUpdateRequest passwordUpdateRequest) {
        return userRepository.findById(id).map(user -> {
            if (!encoder.matches(passwordUpdateRequest.getOldPassword(), user.getPassword())) {
                return ResponseEntity.badRequest().body(new MessageResponse("Error: Old password is incorrect!"));
            }
            
            // Mettez à jour le nouveau mot de passe
            user.setPassword(encoder.encode(passwordUpdateRequest.getNewPassword()));
            userRepository.save(user);
            
            return ResponseEntity.ok(new MessageResponse("Password updated successfully!"));
        }).orElse(ResponseEntity.notFound().build());
    }


    @DeleteMapping("/delete/{id}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<?> deleteUser(@PathVariable Long id) {
        if (!userRepository.existsById(id)) {
            return ResponseEntity.badRequest().body(new MessageResponse("Error: User does not exist!"));
        }
        
        try {
            userRepository.deleteById(id);
            return ResponseEntity.ok(new MessageResponse("User deleted successfully!"));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(new MessageResponse("Error: Could not delete user!"));
        }
    }
    
    @PostMapping("/signin")
    public ResponseEntity<?> authenticateUser(@Valid @RequestBody LoginRequest loginRequest) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword()));

        SecurityContextHolder.getContext().setAuthentication(authentication);
        String jwt = jwtUtils.generateJwtToken(authentication);

        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
        String role = userDetails.getAuthorities().stream()
                .map(item -> item.getAuthority())
                .collect(Collectors.joining());

        return ResponseEntity.ok(new JwtResponse(jwt,
                userDetails.getId(),
                userDetails.getUsername(),
                userDetails.getEmail(),
                role));
    }

    @PostMapping("/signup")
    public ResponseEntity<?> registerUser(@Valid @RequestBody SignupRequest signUpRequest) {
        if (userRepository.existsByUsername(signUpRequest.getUsername())) {
            return ResponseEntity.badRequest().body(new MessageResponse("Error: Username is already taken!"));
        }

        if (userRepository.existsByEmail(signUpRequest.getEmail())) {
            return ResponseEntity.badRequest().body(new MessageResponse("Error: Email is already in use!"));
        }

        ERoleModel role = ERoleModel.valueOf(signUpRequest.getRole().toUpperCase());

        UserModel user = new UserModel(
                signUpRequest.getUsername(),
                signUpRequest.getEmail(),
                encoder.encode(signUpRequest.getPassword()),
                role);

        userRepository.save(user);

        return ResponseEntity.ok(new MessageResponse("User registered successfully!"));
    }

    
    
//   @PostMapping("/signup")
//   public ResponseEntity<?> registerUser(@Valid @RequestBody SignupRequest signUpRequest) {
//     if (userRepository.existsByUsername(signUpRequest.getUsername())) {
//       return ResponseEntity
//           .badRequest()
//           .body(new MessageResponse("Error: Username is already taken!"));
//     }

//     if (userRepository.existsByEmail(signUpRequest.getEmail())) {
//       return ResponseEntity
//           .badRequest()
//           .body(new MessageResponse("Error: Email is already in use!"));
//     }

//     // Create new user's account
//     UserModel user = new UserModel(signUpRequest.getUsername(), 
//                signUpRequest.getEmail(),
//                encoder.encode(signUpRequest.getPassword()));

//     Set<String> strRoles = signUpRequest.getRole();
//     Set<RoleModel> roles = new HashSet<>();

//     if (strRoles == null) {
//       RoleModel userRole = roleRepository.findByName(ERoleModel.ROLE_USER)
//           .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
//       roles.add(userRole);
//     } else {
//       strRoles.forEach(role -> {
//         switch (role) {
//         case "admin":
//          RoleModel adminRole = roleRepository.findByName(ERoleModel.ROLE_ADMIN)
//               .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
//           roles.add(adminRole);

//           break;
//         case "mod":
//          RoleModel modRole = roleRepository.findByName(ERoleModel.ROLE_MODERATOR)
//               .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
//           roles.add(modRole);

//           break;
//         default:
//          RoleModel userRole = roleRepository.findByName(ERoleModel.ROLE_USER)
//               .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
//           roles.add(userRole);
//         }
//       });
//     }

//     user.setRoles(roles);
//     userRepository.save(user);

//     return ResponseEntity.ok(new MessageResponse("User registered successfully!"));
//   }
}
