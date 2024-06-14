package com.henrique.dscommerce.services;

import com.henrique.dscommerce.dto.UserDTO;
import com.henrique.dscommerce.entities.Role;
import com.henrique.dscommerce.entities.User;
import com.henrique.dscommerce.projections.UserDetailsProjection;
import com.henrique.dscommerce.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class UserService implements UserDetailsService {
    @Autowired
    private UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        List<UserDetailsProjection> result = userRepository.searchUserAndRolesByEmail(username);

        if(result.isEmpty()){
            throw new UsernameNotFoundException("User not found with email: " + username);
        }

        User user = new User();
        user.setEmail(username);
        user.setPassword(result.get(0).getPassword());
        result.forEach(ud -> user.addRole(new Role(ud.getRoleId(), ud.getAuthority())));

        return user;
    }

    @Transactional(readOnly = true)
    protected User authenticated(){
        try{
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            Jwt jwtPrincipal = (Jwt) authentication.getPrincipal();
            String username = jwtPrincipal.getClaim("username");
            return userRepository.findByEmail(username).get();
        }catch (Exception e){
            throw new UsernameNotFoundException("Email not found");
        }
    }

    @Transactional(readOnly = true)
    public UserDTO getMe(){
        User user = authenticated();
        return new UserDTO(user);
    }
}
