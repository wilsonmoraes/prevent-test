package br.com.crdd.web.open.accesLog;

import br.com.crdd.common.mapper.BaseDTO;
import lombok.Data;

import java.time.LocalDateTime;

@Data
class AccessLogFindPaginatedResponse implements BaseDTO {

    private Long id;

    private LocalDateTime dateTimeAudit;

    private String clientIP;

    private String methodRequest;

    private Integer statusCodeResponde;

    private String client;

}
