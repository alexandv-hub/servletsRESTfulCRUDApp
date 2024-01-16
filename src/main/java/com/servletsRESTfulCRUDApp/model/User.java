package com.servletsRESTfulCRUDApp.model;

import com.google.gson.annotations.JsonAdapter;
import com.servletsRESTfulCRUDApp.json.GsonUserSerializerAdapter;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.NaturalId;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.SQLRestriction;

import java.util.List;

@Getter
@Setter
@EqualsAndHashCode(of = {"id", "name", "status"})
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString

@Entity
@Table(name = "user")
@SQLRestriction("status <> 'DELETED'")
@SQLDelete(sql = "update user set status = 'DELETED' where id = ?")
@JsonAdapter(GsonUserSerializerAdapter.class)
public class User implements DBEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NaturalId(mutable = true)
    @Column(nullable = false)
    private String name;

    @OneToMany(mappedBy = "user", cascade = {CascadeType.MERGE, CascadeType.PERSIST}, fetch = FetchType.LAZY)
    private List<Event> events;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    @Transient
    private Status status;
}
