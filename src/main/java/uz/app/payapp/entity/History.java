package uz.app.payapp.entity;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@NamedQuery(name = "getHistory", query = "select h from History h where  h.setFrom = :number or h.setTo = :number")
@NamedQuery(name = "getByUser", query = "select h from History h where  h.user.id = :id")
public class History {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String setType;
    private String setTo;
    private String setFrom;
    private Double amount;
    @Column(name = "date")
    private LocalDateTime setDate;
    @ManyToOne
    private User user;


    @PrePersist
    public void prePersist() {
        if(setDate == null){
            setDate = LocalDateTime.now();
        }
    }
}

