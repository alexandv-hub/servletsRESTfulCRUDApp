package com.servletsRESTfulCRUDApp.model;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.SQLRestriction;

@Getter
@Setter
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Builder

@Entity
@Table(name = "event")
@SQLRestriction(value = "status <> 'DELETED'")
@SQLDelete(sql = "update event set status = 'DELETED' where id = ?")
public class Event implements DBEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

        @ManyToOne(cascade = {CascadeType.MERGE, CascadeType.PERSIST},
                fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @OneToOne(cascade = {CascadeType.MERGE, CascadeType.PERSIST}, fetch = FetchType.LAZY)
    @JoinColumn(name = "file_id")
    private File file;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    @Transient
    private Status status;
}
