package web.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import web.dao.UserDao;
import web.model.Role;
import web.model.User;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@Transactional
@Service
public class UserServiceImpl implements UserService {

    private final UserDao userDao;
    @Autowired
    private PasswordEncoder passwordEncoder;



    public UserServiceImpl(UserDao userDao) {
        this.userDao = userDao;

    }


    @Override
    public User findByLastName(String lastname) {
        return userDao.findByLastName(lastname);
    }


    @Override
    public User findById(Long id) {
        return userDao.findById(id);
    }


    @Override
    public List<User> findAll() {
        return userDao.findAll();
    }


    @Override
    public void saveUser(User user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        userDao.saveUser(user);
    }


    @Override
    public void deleteById(Long id) {
        userDao.deleteById(id);
    }

    @Override
    public void updateUser(User user) {
        if (user.getPassword().equals("")) {
            user.setPassword(userDao.findById(user.getId()).getPassword());
        } else {
            if (!passwordEncoder.matches(passwordEncoder.encode(user.getPassword()), userDao.findById(user.getId()).getPassword())) {
                user.setPassword(passwordEncoder.encode(user.getPassword()));
            }
        }
        userDao.updateUser(user);
    }


    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
        User user = findByLastName(s);
        if (user == null) {
            throw new UsernameNotFoundException("");
        }
        return new org.springframework.security.core.userdetails.User(user.getLastName(),
                user.getPassword(), convRoles(user.getRoles()));
    }

    private Collection<? extends GrantedAuthority> convRoles(Collection<Role> roles) {
        return roles.stream().map(x -> new SimpleGrantedAuthority(x.getRole())).collect(Collectors.toList());
    }
}