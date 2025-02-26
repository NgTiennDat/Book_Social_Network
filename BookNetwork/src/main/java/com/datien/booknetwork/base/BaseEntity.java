package com.datien.booknetwork.base;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.sql.Timestamp;
import java.time.LocalDateTime;

@Data
@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
public class BaseEntity {

    @Id
    @GeneratedValue
    private Integer id;

    @Column(nullable = false, updatable = false)
    private Timestamp createdDate;

    @Column(insertable = false)
    private Timestamp lastModifiedDate;

    @Column(nullable = false, updatable = false)
    private Integer createdBy;

    @Column(insertable = false)
    private Integer lastModifiedBy;

}
