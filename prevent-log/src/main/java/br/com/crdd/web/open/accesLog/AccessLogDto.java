package br.com.crdd.web.open.accesLog;

import br.com.crdd.common.mapper.BaseDTO;
import io.swagger.annotations.ApiModel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
@ApiModel(description = "Log detail property.")
public class AccessLogDto implements BaseDTO {
    private Long id;
    private LocalDateTime dateTimeAudit;
    private String dateTimeAuditAux;
    private String clientIP;
    private String methodRequest;
    private Integer statusCodeResponde;
    private String client;

}
