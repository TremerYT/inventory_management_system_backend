package com.ims.inventory_management_system_backend.service.user;

import com.ims.inventory_management_system_backend.dto.customer.RegisterRequestDTO;
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

    User newUser = new User();
    newUser.setEmail(dto.getEmail());
    newUser.setPassword(passwordEncoder.encode(dto.getPassword()));
    Role customRole = roleRepository
            .findByName(RoleName.ROLE_CUSTOMER)
            .orElseThrow(() -> new RuntimeException("Default Role not set"));
    newUser.setRoles(new HashSet<>(Set.of(customRole)));
    userRepository.save(newUser);

    Customer newCustomer = new Customer();

    newCustomer.setCustomerCode("CUST-" + UUID.randomUUID().toString().substring(0, 8).toUpperCase());
    newCustomer.setFirstName(dto.getFirstName());
    newCustomer.setLastName(dto.getLastName());
    newCustomer.setPhoneNumber(dto.getPhoneNumber());
    newCustomer.setUser(newUser);

    customerRepository.save(newCustomer);
  }
}
