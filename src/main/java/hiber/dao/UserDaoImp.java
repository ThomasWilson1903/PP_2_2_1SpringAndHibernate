package hiber.dao;

import hiber.model.Car;
import hiber.model.User;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;


import javax.persistence.TypedQuery;
import java.util.List;
import java.util.stream.Collectors;

@Repository
public class UserDaoImp implements UserDao {

    @Autowired
    private SessionFactory sessionFactory;

    @Override
    @Transactional
    public void add(User user) {
        sessionFactory.getCurrentSession().save(user);
    }

    @Override
    @SuppressWarnings("unchecked")
    @Transactional
    public List<User> listUsers() {
        TypedQuery<User> query = sessionFactory.getCurrentSession().createQuery("from User");
        return query.getResultList();
    }

    @Override
    @Transactional
    public User getUser(String model, int series) {
        TypedQuery<User> query = sessionFactory.getCurrentSession().createQuery("from User");
        try {
            return query.getResultList().stream()
                    .filter(user -> user.getEmtCar().getModel().equals(model))
                    .filter(user -> user.getEmtCar().getSeries() == series)
                    .collect(Collectors.toList()).get(1);
        }catch (Exception exception){
            System.out.println("[[the search returned no results]]");
        }
        return null;
    }

}
