package uz.app.payapp.service.card;

import jakarta.persistence.EntityManager;
import uz.app.payapp.db.Database;
import uz.app.payapp.entity.Card;
import uz.app.payapp.entity.User;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class CardService {
    public void addCard(User user,String password,String type) {
        Card card = Card.builder()
                .number(createCardNumber())
                .phone(user.getPhone())
                .expiryDate(expiryDate())
                .code(password)
                .type(type)
                .balance(0.0)
                .isActive(true)
                .isExpired(false)
                .user(user)
                .build();

        EntityManager em = Database.getInstance().getEntityManager();
        try {
            em.getTransaction().begin();
            em.persist(card);
        } finally {
            em.getTransaction().commit();
        }
    }

    public List<Card> getCard(Integer userId) {
        EntityManager em = Database.getInstance().getEntityManager();
        List<Card> cards = new ArrayList<>();
        try {
            em.getTransaction().begin();
            cards = em.createNamedQuery("getAllCards", Card.class)
                    .setParameter("user_id", userId)
                    .getResultList();
        }finally {
            em.getTransaction().commit();
        }
        return cards;
    }

    public void blockUnblock(Integer id, Boolean status) {
        EntityManager em = Database.getInstance().getEntityManager();
        try {
            System.out.println("status = " + status);
            em.getTransaction().begin();
            em.createNamedQuery("changeActive")
                    .setParameter("status", status)
                    .setParameter("id", id)
                    .executeUpdate();
        }finally {
            em.getTransaction().commit();
        }
    }

    private String createCardNumber() {
        StringBuilder sb = new StringBuilder();
        for (int i = 1; i <= 16; i++) {
            sb.append(new Random().nextInt(0, 10));
            if (i % 4 == 0 && i != 16)
                sb.append(" ");
        }
        return sb.toString();
    }

    private String expiryDate() {
        LocalDate expiryDate = LocalDate.now().plusYears(5);
        return expiryDate.format(DateTimeFormatter.ofPattern("MM/yy"));
    }
}
