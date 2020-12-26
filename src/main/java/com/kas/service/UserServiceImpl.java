package com.kas.service;

import com.kas.dto.UserRegistrationDTO;
import com.kas.entity.Role;
import com.kas.entity.User;
import com.kas.entity.authentication.CustomUserPrincipal;
import com.kas.repository.RoleRepository;
import com.kas.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;


@Service
public class UserServiceImpl implements UserService{

    @Autowired
    private UserRepository userRepositroy;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public User save(UserRegistrationDTO userRegistrationDTO) {
        User user = new User(
                userRegistrationDTO.getFirstName(),
                userRegistrationDTO.getLastName(),
                userRegistrationDTO.getEmail(),
                passwordEncoder.encode(userRegistrationDTO.getPassword())
        );

        Optional<Role> role = roleRepository.findById(userRegistrationDTO.getRole());
        user.getRoles().add(role.get());

        return userRepositroy.save(user);
    }

    @Override
    public List<User> getUsers() {
        return userRepositroy.findAll();
    }


    @Transactional
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepositroy.findByEmail(username);
        //boolean isPasswordMatch = passwordEncoder.matches("yawinpassword", user.getPassword());

        if(user == null){
            throw  new UsernameNotFoundException("Invalid Username Or Password");
        }

        return CustomUserPrincipal.create(user);
        //return new org.springframework.security.core.userdetails.User(user.getEmail(), user.getPassword(), mapRolesToAuthorities(user.getRoles()));
    }

    private Collection<? extends GrantedAuthority> mapRolesToAuthorities(Collection<Role> roles){
        return roles.stream().map(role -> new SimpleGrantedAuthority(role.getName())).collect(Collectors.toList());
    }
}
