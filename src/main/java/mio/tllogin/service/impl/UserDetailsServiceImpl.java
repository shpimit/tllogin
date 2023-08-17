package mio.tllogin.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import mio.tllogin.service.mapper.UserMapper;

import javax.annotation.Resource;
import java.util.List;

@RequiredArgsConstructor
@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    @Resource(name="UserMapper")
    private UserMapper userMapper;

    public List<UserDetailsImpl> findAll() {
        return userMapper.selectList();
    }

    public void insertUser(UserDetailsImpl userDetailsImpl) {
        userMapper.insertUser(userDetailsImpl);
    };

    @SneakyThrows
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return userMapper.findByUsername(username);
    }
}
