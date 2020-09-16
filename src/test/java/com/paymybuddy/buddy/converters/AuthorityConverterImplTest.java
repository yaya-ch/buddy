package com.paymybuddy.buddy.converters;

import com.paymybuddy.buddy.domain.Authority;
import com.paymybuddy.buddy.domain.User;
import com.paymybuddy.buddy.dto.AuthorityDTO;
import com.paymybuddy.buddy.dto.UserDTO;
import org.junit.jupiter.api.*;
import org.mockito.Mock;
import org.modelmapper.ModelMapper;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 * @author Yahia CHERIFI
 */

@Tag("Converters")
class AuthorityConverterImplTest {

    private AuthorityConverter converter;

    private Authority authority;

    private AuthorityDTO authorityDTO;

    @Mock
    private ModelMapper mapper;

    private Authority setAuthority() {
        List<User> users = new ArrayList<>();
        authority = new Authority();
        authority.setName("ADMIN");
        authority.setUsers(users);
        return authority;
    }

    private AuthorityDTO setAuthorityDTO() {
        List<UserDTO> userDTOS = new ArrayList<>();
        authorityDTO = new AuthorityDTO();
        authorityDTO.setName("USER");
        authorityDTO.setUsers(userDTOS);
        return authorityDTO;
    }

    @BeforeEach
    void setUp() {
        mapper = new ModelMapper();
        converter = new AuthorityConverterImpl(mapper);
        setAuthority();
        setAuthorityDTO();
    }

    @AfterEach
    void tearDown() {
        mapper = null;
        converter = null;
        authority = null;
        authorityDTO = null;
    }

    @DisplayName("Convert Authority entity to AuthorityDTO")
    @Test
    void authorityEntityToDTOConverter_shouldConvertEntityToDTO() {
        AuthorityDTO authorityDTO1 = converter.authorityEntityToDTOConverter(authority);

        assertEquals(authorityDTO1.getName(), authority.getName());
        assertEquals(authorityDTO1.getUsers().size(), authority.getUsers().size());
    }

    @DisplayName("Convert Authority entity list to AuthorityDTO list")
    @Test
    void authorityEntityListToDTOListConverter_shouldConvertEntityListToDTOList() {
        List<Authority> authorities = new ArrayList<>();
        authorities.add(authority);

        List<AuthorityDTO> authorityDTOList =
                converter.authorityEntityListToDTOListConverter(authorities);

        assertEquals(authorities.size(), authorityDTOList.size());
    }

    @DisplayName("Convert AuthorityDTO to Authority entity")
    @Test
    void authorityDTOToEntityConverter_shouldConvertDTOToAuthorityEntity() {
        Authority authority1 = converter.authorityDTOToEntityConverter(authorityDTO);

        assertEquals(authority1.getName(), authorityDTO.getName());
        assertEquals(authority1.getUsers().size(), authorityDTO.getUsers().size());
    }

    @DisplayName("Convert AuthorityDTO list to Authority entity list")
    @Test
    void authorityDTOListToEntityListConverter_shouldConvertDTOListToAuthorityEntityList() {
        List<AuthorityDTO> authorityDTOList = new ArrayList<>();
        authorityDTOList.add(authorityDTO);

        List<Authority> authorityList =
                converter.authorityDTOListToEntityListConverter(authorityDTOList);

        assertEquals(authorityList.size(), authorityDTOList.size());
    }
}