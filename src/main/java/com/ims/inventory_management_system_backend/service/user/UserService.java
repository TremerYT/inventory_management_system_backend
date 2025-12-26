package com.ims.inventory_management_system_backend.service.user;

import com.ims.inventory_management_system_backend.dto.register.RegisterRequestDTO;
import com.ims.inventory_management_system_backend.entities.customers.Customer;
import com.ims.inventory_management_system_backend.entities.role.Role;
import com.ims.inventory_management_system_backend.entities.role.RoleName;
import com.ims.inventory_management_system_backend.entities.user.User;
import com.ims.inventory_management_system_backend.repository.customer.CustomerRepository;
import com.ims.inventory_management_system_backend.repository.role.RoleRepository;
import com.ims.inventory_management_system_backend.repository.user.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserService implements UserDetailsService {
  private final UserRepository userRepository;
  private final CustomerRepository customerRepository;
  private final RoleRepository roleRepository;
  private final PasswordEncoder passwordEncoder;

  @Override
  public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
    User user = userRepository.findByEmail(email)
        .orElseThrow(() -> new UsernameNotFoundException("User not found"));
    return new org.springframework.security.core.userdetails.User(
        user.getEmail(),
        user.getPassword(),
        mapRolesToAuthorities(user.getRoles()));
  }

  private Collection<? extends GrantedAuthority> mapRolesToAuthorities(Set<Role> roles) {
    return roles.stream().map(role -> new SimpleGrantedAuthority(role.getName().name()))
        .collect(Collectors.toList());
  }

  @Transactional
  public void registerUser(RegisterRequestDTO dto){
    if (userRepository.existsByEmail((dto.getEmail()))) {
      throw new RuntimeException("Email already in use");
    }

    Role customRole = roleRepository
            .findByName(RoleName.ROLE_CUSTOMER)
            .orElseThrow(() -> new RuntimeException("Default Role not set"));

    User newUser = User
            .builder()
            .email(dto.getEmail())
            .password(passwordEncoder.encode(dto.getPassword()))
            .roles(new HashSet<>(Set.of(customRole)))
            .build();
    userRepository.save(newUser);

    Customer newCustomer = Customer
            .builder()
            .customerCode("CUST-" + UUID.randomUUID().toString().substring(0, 8).toUpperCase())
            .firstName(dto.getFirstName())
            .lastName(dto.getLastName())
            .phoneNumber(dto.getPhoneNumber())
            .user(newUser)
            .build();
    customerRepository.save(newCustomer);
  }
}
