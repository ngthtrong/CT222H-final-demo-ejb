package com.example.ejb;

import com.example.api.MySessionBeanLocal;
import com.example.api.MySessionBeanRemote;
import com.example.entity.User;
import jakarta.ejb.Remote;
import jakarta.ejb.Stateless;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import java.util.List;
import java.util.stream.Collectors;

@Stateless(name = "MySessionBean")
@Remote(MySessionBeanRemote.class)
public class MySessionBean implements MySessionBeanLocal, MySessionBeanRemote {

    @PersistenceContext(unitName = "myAppPU")
    private EntityManager em;

    // Local method: Interact with DB
    @Override
    public void addUser(String name) {
        User user = new User();
        user.setName(name);
        em.persist(user);
    }

    @Override
    public List<String> getAllUserNames() {
        return em.createQuery("SELECT u FROM User u", User.class)
                 .getResultList()
                 .stream()
                 .map(User::getName)
                 .collect(Collectors.toList());
    }

    // Remote method: Simple logic
    @Override
    public String getGreeting(String name) {
        return "Hello, " + name + " from Remote EJB!";
    }
}
