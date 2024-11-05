package br.com.fiap.mechai.service;

import br.com.fiap.mechai.model.User;
import br.com.fiap.mechai.dao.UserDAO;

import java.util.List;

public class UserService {
    private UserDAO userDAO = new UserDAO();

    public void registerUser(User user) {
        userDAO.createUser(user);
    }

    public User getUserById(int id) {
        return userDAO.readUser(id);
    }

    public List<User> getAllUsers() {
        return userDAO.getAllUsers();
    }

    public void updateUser(User user) {
        userDAO.updateUser(user);
    }

    public void deleteUser(int id) {
        userDAO.deleteUser(id);
    }
}
