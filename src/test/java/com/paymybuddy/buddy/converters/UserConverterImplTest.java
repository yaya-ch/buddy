package com.paymybuddy.buddy.converters;

import com.paymybuddy.buddy.domain.BuddyAccountInfo;
import com.paymybuddy.buddy.domain.User;
import com.paymybuddy.buddy.dto.ContactDTO;
import com.paymybuddy.buddy.dto.UserDTO;
import com.paymybuddy.buddy.enums.Role;
import com.paymybuddy.buddy.enums.Civility;
import org.junit.jupiter.api.*;
import org.mockito.Mock;
import org.modelmapper.ModelMapper;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

/**
 * @author Yahia CHERIFI
 */
@Tag("Converters")
class UserConverterImplTest {

    private UserConverter converter;

    private User user;

    private UserDTO userDTO;

    private ContactDTO contactDTO;

    @Mock
    private ModelMapper mapper;

    private User setUser() {
        Set<User> contacts = new HashSet<>();
        BuddyAccountInfo accountInfo = new BuddyAccountInfo();
        user = new User();
        user.setCivility(Civility.SIR);
        user.setRole(Role.USER);
        user.setFirstName("User");
        user.setLastName("user");
        user.setEmail("user@user.com");
        user.setPassword("okjuytf787");
        user.setBirthDate(LocalDate.of(2002, 12, 12));
        user.setAddress("any address");
        user.setCity("any city");
        user.setZip("12345");
        user.setPhone("1234567890");
        user.setBuddyAccountInfo(accountInfo);
        user.setContacts(contacts);
        return user;
    }

    private UserDTO setUserDTO() {
        Set<ContactDTO> contactDTOS = new HashSet<>();
        userDTO = new UserDTO();
        userDTO.setCivility("MADAM");
        userDTO.setFirstName("Girl");
        userDTO.setLastName("girl");
        userDTO.setEmail("girl@girl.com");
        userDTO.setPassword("KJUIOYY666");
        userDTO.setRole("USER");
        userDTO.setBirthDate(LocalDate.of(1997, 12, 22));
        userDTO.setAddress("girl address");
        userDTO.setCity("girl city");
        userDTO.setZip("56565");
        userDTO.setPhone("911911911");
        //userDTO.setBuddyAccountInfo();
        userDTO.setContacts(contactDTOS);
        return userDTO;
    }

    private ContactDTO setContactDTO() {
        contactDTO = new ContactDTO();
        contactDTO.setFirstName("Contact");
        contactDTO.setLastName("dto");
        contactDTO.setEmail("contact@mail.com");
        return contactDTO;
    }

    @BeforeEach
    void setUp() {
        mapper = new ModelMapper();
        converter = new UserConverterImpl(mapper);
        setUser();
        setUserDTO();
        setContactDTO();
    }

    @AfterEach
    void tearDown() {
        mapper = null;
        converter = null;
        user = null;
        userDTO = null;
        contactDTO = null;
    }

    @DisplayName("Convert User entity to UserDTO")
    @Test
    void userEntityToUserDTO_shouldConvertUserEntityToUserDTO() {
        UserDTO userDTO = converter.userEntityToUserDTO(user);
        assertEquals(userDTO.getCivility(), user.getCivility().toString());
        assertEquals(userDTO.getFirstName(), user.getFirstName());
        assertEquals(userDTO.getLastName(), user.getLastName());
        assertEquals(userDTO.getEmail(), user.getEmail());
        assertEquals(userDTO.getPassword(), user.getPassword());
        assertEquals(userDTO.getBirthDate(), user.getBirthDate());
        assertEquals(userDTO.getAddress(), user.getAddress());
        assertEquals(userDTO.getCity(), user.getCity());
        assertEquals(userDTO.getZip(), user.getZip());
        assertEquals(userDTO.getContacts().size(), user.getContacts().size());
        assertEquals(userDTO.getPhone(), user.getPhone());
        assertEquals(userDTO.getRole(), user.getRole().toString());
        //assertEquals(userDTO.getBuddyAccountInfo(), user.getBuddyAccountInfo());
    }

    @DisplayName("Convert User entity list to UserDTO list")
    @Test
    void userEntityListToUserDTOList_shouldConvertUserEntityListToUserDTOList() {
        List<User> users = new ArrayList<>();
        users.add(user);

        List<UserDTO> userDTOList = converter.userEntityListToUserDTOList(users);

        assertEquals(userDTOList.size(), users.size());
    }

    @DisplayName("Convert UserDTO to User entity")
    @Test
    void userDTOToUserEntity_shouldConvertUserDTOToUserEntity() {
        User user = converter.userDTOToUserEntity(userDTO);

        assertEquals(user.getCivility().toString(), userDTO.getCivility());
        assertEquals(user.getFirstName(), userDTO.getFirstName());
        assertEquals(user.getLastName(), userDTO.getLastName());
        assertEquals(user.getEmail(), userDTO.getEmail());
        assertEquals(user.getPassword(), userDTO.getPassword());
        assertEquals(user.getRole().toString(), userDTO.getRole());
        assertEquals(user.getBirthDate(), userDTO.getBirthDate());
        assertEquals(user.getAddress(), userDTO.getAddress());
        assertEquals(user.getCity(), userDTO.getCity());
        assertEquals(user.getZip(), userDTO.getZip());
        assertEquals(user.getContacts().size(), userDTO.getContacts().size());
        assertEquals(user.getPhone(), userDTO.getPhone());
        //assertEquals(user.getBuddyAccountInfo(), userDTO.getBuddyAccountInfo());
    }

    @DisplayName("Convert UserDTO list to User entity list")
    @Test
    void userDTOListToUserEntityList_shouldConvertUserDTOListToUserEntityList() {
        List<UserDTO> userDTOS = new ArrayList<>();
        userDTOS.add(userDTO);
        List<User> users = converter.userDTOListToUserEntityList(userDTOS);

        assertEquals(users.size(), userDTOS.size());
    }

    @DisplayName("Convert User entity to ContactDTO")
    @Test
    void userEntityToContactDTO_shouldConvertUserEntityToContactDTO() {
        ContactDTO contactDTO = converter.userEntityToContactDTO(user);

        assertEquals(contactDTO.getFirstName(), user.getFirstName());
        assertEquals(contactDTO.getLastName(), user.getLastName());
        assertEquals(contactDTO.getEmail(), user.getEmail());
    }

    @DisplayName("Convert User entity list to ContactDTO list")
    @Test
    void userEntityListToContactDTOList_shouldConvertUserEntityListToContactDTOList() {
        List<User> users = new ArrayList<>();
        users.add(user);

        List<ContactDTO> userDTOS = converter.userEntityListToContactDTOList(users);
        assertEquals(userDTOS.size(), users.size());
    }

    @DisplayName("Convert ContactDTO to User entity")
    @Test
    void contactDTOToUserEntity_shouldConvertContactDTOToUserEntity() {
        User user1 = converter.contactDTOToUserEntity(contactDTO);

        assertEquals(user1.getFirstName(), contactDTO.getFirstName());
        assertEquals(user1.getLastName(), contactDTO.getLastName());
        assertEquals(user1.getEmail(), contactDTO.getEmail());
    }

    @DisplayName("Convert ContactDTO list to User entity list")
    @Test
    void contactDTOListToUserEntityList_shouldConvertContactDTOListToUserEntityList() {
        List<ContactDTO> contactDTOS = new ArrayList<>();
        contactDTOS.add(contactDTO);

        List<User> users = converter.contactDTOListToUserEntityList(contactDTOS);
        assertEquals(users.size(), contactDTOS.size());
    }
}