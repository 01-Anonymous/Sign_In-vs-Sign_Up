package uz.app.payapp.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
@NamedQuery(name = "getAllCards", query = "select c from Card c where c.user.id = :user_id")
@NamedQuery(name = "updateBalance", query = "update Card c set c.balance = :balance where c.number = :number")
@NamedQuery(name = "byCardNumber", query = "select c from Card c where c.number = :number")
@NamedQuery(name = "getCard", query = "select c from Card c where c.id = :id")
@NamedQuery(name = "changeActive", query = "update Card c set c.isActive = :status where id = :id")
@Table(name = "cards")
public class Card {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String number;
    private String expiryDate;
    private String phone;
    private String code;
    private String type;
    private Double balance = 0.0;
    private Boolean isActive = true;
    private Boolean isExpired = false;

    @ManyToOne
    private User user;
}
