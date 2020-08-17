package br.com.crdd.domain;

import br.com.crdd.common.mapper.BaseEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;

/**
 *
 */
@Entity
@Table(name = "access_log")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AccessLog implements BaseEntity<Long> {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private LocalDateTime dateTimeAudit;

    @Column
    private LocalDateTime registrationDate;

    @Column
    private String clientIP;

    @Column
    private String methodRequest;

    @Column
    private Integer statusCodeResponde;

    @Column
    private String client;


}
