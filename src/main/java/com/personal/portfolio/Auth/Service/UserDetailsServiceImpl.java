package com.personal.portfolio.Auth.Service;



import com.personal.portfolio.Dto.ContactDTO;
import com.personal.portfolio.Dto.UserDTO;
import com.personal.portfolio.Mapper.*;
import com.personal.portfolio.Model.Users;
import com.personal.portfolio.Repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {
    @Autowired
    UserRepository userRepository;

    public UserDTO getUserDto(UserDetails userDetails) {
        UserDTO userDTO = userDetailToUserDto(userDetails);
        return userDTO;
    }

    public UserDTO userDetailToUserDto(UserDetails userDetails){
        Users user = (Users)userDetails;
        UserDTO userDto = new UserDTO();
        userDto.setUserId(user.getUserId());
        userDto.setName(user.getName());
        userDto.setEmail(user.getEmail());
        userDto.setBio(user.getBio());
        userDto.setProfilePicture(user.getProfilePicture());
        userDto.setPassword("May ni bataunga chupa ke rakhunga");
//        userDto.setContacts(user.getContacts().stream().map(ContactMapper::toDTO).collect(Collectors.toList()));
//        userDto.setCertifications(user.getCertifications().stream().map(CertificationMapper::toDTO).collect(Collectors.toList()));
//        userDto.setEducationList(user.getEducationList().stream().map(ExperienceMapper::toDTO).collect(Collectors.toList()));
//        userDto.setExperienceList(user.getExperienceList().stream().map(ExperienceMapper::toDTO).collect(Collectors.toList()));
//        userDto.setProjects(user.getProjects().stream().map(ProjectMapper::toDTO).collect(Collectors.toList()));

        return userDto;
    }



    @Override
    @Transactional
    public UserDetails loadUserByUsername(String username) {
        return (UserDetails)userRepository.findByUsername(username);
    }

    public List<Users> getAllUsers(){
        return userRepository.findAll();
    }

}
