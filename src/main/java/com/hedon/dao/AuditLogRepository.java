package com.hedon.dao;

import com.hedon.bean.AuditLog;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.CrudRepository;

/**
 * @author Hedon Wang
 * @create 2020-10-02 17:03
 */
public interface AuditLogRepository extends JpaSpecificationExecutor<AuditLog>, CrudRepository<AuditLog,Integer> {

}
