package br.com.crdd.dao;

import br.com.crdd.domain.AccessLog;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;


@Repository
@Transactional
public interface AccessLogRepository extends JpaRepository<AccessLog, Long> {

    @Query("SELECT bean from AccessLog bean "
            + " where (:clientIP is null or bean.clientIP like %:clientIP%)"
            + " and (:methodRequest is null or lower(bean.methodRequest) like %:methodRequest%)"
            + " and (:statusCodeResponde is null or bean.statusCodeResponde =:statusCodeResponde)"
            + " and (:client is null or lower(bean.client) like %:client%)")
    Page<AccessLog> findAllPaginated(@Param("clientIP") String clientIP,
                                     @Param("methodRequest") String methodRequest,
                                     @Param("statusCodeResponde") Integer statusCodeResponde,
                                     @Param("client") String client,
                                     Pageable pageable);


}
