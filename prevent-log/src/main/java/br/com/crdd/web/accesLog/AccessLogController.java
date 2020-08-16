package br.com.crdd.web.accesLog;

import br.com.crdd.common.mapper.ControllerManager;
import br.com.crdd.common.pagination.PageResult;
import br.com.crdd.domain.AccessLog;
import br.com.crdd.service.AccessLogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * the main br.com.crdd.web api for customer CRUD
 */
@RestController
@RequestMapping(value = "/open/access_log", produces = MediaType.APPLICATION_JSON_VALUE)
public class AccessLogController {

    @Autowired
    private AccessLogService service;

    @Autowired
    private ControllerManager controllerManager;


    /*@GetMapping(value = "findOne/{id}")
    public ResponseEntity<VeiculoDto> getOneById(@PathVariable long id) {
        VeiculoDto response = controllerManager.entityToDto(veiculoService.findCustomerById(id), VeiculoDto.class);
        return ResponseEntity.status(Objects.isNull(response) ? HttpStatus.NO_CONTENT : HttpStatus.OK)
                .body(response);
    }

    @PostMapping("/all")
    public ResponseEntity<PageResult<VeiculoFindAllResponse>> findAll(@RequestBody VeiculoFindAllRequest request) {
        PageRequest pageRequest = controllerManager.toPageRequest(request);
        Page<Veiculo> consulta = veiculoService.findAllPaged(pageRequest);
        return ResponseEntity.status(HttpStatus.OK).body(controllerManager.toPageResult(consulta, VeiculoFindAllResponse.class));
    }

    @PostMapping(value = "/save")
    public ResponseEntity<VeiculoDto> save(@RequestBody VeiculoDto request) {
        Veiculo veiculo = controllerManager.dtoToEntity(request, Veiculo.class);
        veiculo = veiculoService.save(veiculo);
        return ResponseEntity.status(HttpStatus.OK).body(controllerManager.entityToDto(veiculo, VeiculoDto.class));
    }

    @GetMapping(value = "/autocomplete")
    public ResponseEntity<List<VeiculoAutoCompleteResponse>> getAutoComplete(@RequestParam String query, @RequestParam int maxResults) {
        List<Veiculo> consulta = veiculoService.findAllByPlaca(query, PageRequest.of(0, maxResults));
        return ResponseEntity.status(HttpStatus.OK)
                .body(controllerManager.entityToDto(consulta, VeiculoAutoCompleteResponse.class));
    }*/

    @PostMapping(value = "/upload")
    public ResponseEntity<Void> uploadLog(@RequestParam(value = "file", required = false) MultipartFile file) throws Exception {
        service.upload(file);
        return ResponseEntity.status(HttpStatus.OK).body(null);
    }

    @PostMapping("/paginated")
    public ResponseEntity<PageResult<AccessLogFindPaginatedResponse>> findAllPaginated(@RequestBody AccessLogFindPaginatedRequest request) {
        PageRequest pageRequest = controllerManager.toPageRequest(request);
        Page<AccessLog> consulta = service.findAllPaginated(request.getClientIP(), request.getMethodRequest(), request.getStatusCodeResponde(), request.getClient(), pageRequest);
        return ResponseEntity.status(HttpStatus.OK).body(controllerManager.toPageResult(consulta, AccessLogFindPaginatedResponse.class));
    }

    @DeleteMapping(value = "{id}")
    public ResponseEntity<Void> deleteById(@PathVariable long id) {
        service.deleteById(id);
        return ResponseEntity.status(HttpStatus.OK).body(null);
    }

    @PostMapping
    public ResponseEntity<AccessLogDto> save(@RequestBody AccessLogDto request) {
        AccessLog accessLog = controllerManager.dtoToEntity(request, AccessLog.class);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");
        accessLog.setDateTimeAudit(LocalDateTime.parse(request.getDateTimeAuditAux(), formatter));
        accessLog = service.save(accessLog);
        return ResponseEntity.status(HttpStatus.OK).body(controllerManager.entityToDto(accessLog, AccessLogDto.class));
    }

    @GetMapping(value = "{id}")
    public ResponseEntity<AccessLogDto> findById(@PathVariable long id) {
        AccessLogDto response = controllerManager.entityToDto(service.findById(id), AccessLogDto.class);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

}
