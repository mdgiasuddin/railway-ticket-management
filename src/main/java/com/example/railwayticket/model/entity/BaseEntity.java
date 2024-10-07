package com.example.railwayticket.model.entity;

import com.example.railwayticket.utils.AppDateTimeUtils;
import com.example.railwayticket.utils.SecurityUtils;
import jakarta.persistence.Column;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.MappedSuperclass;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.time.LocalDateTime;

import static jakarta.persistence.FetchType.LAZY;
import static jakarta.persistence.GenerationType.IDENTITY;

@MappedSuperclass
@Getter
@Setter
public class BaseEntity implements Serializable {

    @Id
    @GeneratedValue(strategy = IDENTITY)
    private long id;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(nullable = false)
    private User createdBy;

    @JoinColumn(nullable = false)
    @ManyToOne(fetch = LAZY)
    private User lastUpdatedBy;

    @Column(nullable = false)
    private LocalDateTime createdAt;

    @Column(nullable = false)
    private LocalDateTime lastUpdatedAt;

    @PrePersist
    public void prePersist() {
        createdBy = lastUpdatedBy = SecurityUtils.getCurrentUser().orElse(null);
        createdAt = lastUpdatedAt = AppDateTimeUtils.nowInBD();
    }

    @PreUpdate
    public void preUpdate() {
        lastUpdatedBy = SecurityUtils.getCurrentUser().orElse(null);
        lastUpdatedAt = AppDateTimeUtils.nowInBD();
    }
}
