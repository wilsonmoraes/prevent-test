package br.com.crdd.service;

import br.com.crdd.dao.AccessLogRepository;
import br.com.crdd.domain.AccessLog;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.regex.Pattern;

/**
 * the main access service
 */
@Service
public class AccessLogService {


    @Autowired
    private AccessLogRepository accessLogRepository;

    public void upload(MultipartFile file) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(file.getInputStream()));
        String strLine;

        //Read File Line By Line
        ArrayList<AccessLog> listAccessLog = new ArrayList<>();
        while ((strLine = br.readLine()) != null) {
            // Print the content on the console
            String[] alol = strLine.split(Pattern.quote("|"));
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.SSS");
            AccessLog accessLog = AccessLog.builder()
                    .dateTimeAudit(LocalDateTime.parse(alol[0], formatter))
                    .clientIP(alol[1])
                    .methodRequest(alol[2].replace("\"", "")).statusCodeResponde(Integer.parseInt(alol[3]))
                    .client(alol[4].replace("\"", "")).build();
            listAccessLog.add(accessLog);
        }
        if (!listAccessLog.isEmpty()) {
            accessLogRepository.saveAll(listAccessLog);
        }

    }


    /*public Veiculo findCustomerById(Long id) {
        return veiculoRepository.findById(id).orElse(null);
    }

    public Page<Veiculo> findAllPaged(PageRequest pageRequest) {
        return veiculoRepository.findAll(pageRequest);
    }


    public Veiculo save(Veiculo veiculo) {

        if (StringUtils.isEmpty(veiculo.getPlaca())) {
            throw new BusinessException("A placa é obrigatória.");
        }

        return veiculoRepository.save(veiculo);
    }
*/
    public Page<AccessLog> findAllPaginated(String clientIP,
                                            String methodRequest,
                                            Integer statusCodeResponde,
                                            String client, PageRequest pageRequest) {
        return accessLogRepository.findAllPaginated(StringUtils.defaultIfEmpty(clientIP, null), methodRequest, statusCodeResponde, client, pageRequest);
    }

    public void deleteById(Long id) {
        accessLogRepository.deleteById(id);
    }

    public AccessLog save(AccessLog accessLog) {
        return accessLogRepository.save(accessLog);
    }

    public AccessLog findById(Long id) {
        return accessLogRepository.findById(id).orElse(null);
    }
}
