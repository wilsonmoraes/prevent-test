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
            + " where (bean.clientIP like %:clientIP% or :clientIP is null)"
            + " and (bean.methodRequest like %:methodRequest% or :methodRequest is null)"
            + " and (bean.statusCodeResponde =:statusCodeResponde or :statusCodeResponde is null)"
            + " and (bean.client like %:client% or :client is null)")
    Page<AccessLog> findAllPaginated(@Param("clientIP") String clientIP,
                                     @Param("methodRequest") String methodRequest,
                                     @Param("statusCodeResponde") Integer statusCodeResponde,
                                     @Param("client") String client,
                                     Pageable pageable);


}
