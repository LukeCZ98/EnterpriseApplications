package unical.informatica.it.enterpriseapplicationbackend.model.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import unical.informatica.it.enterpriseapplicationbackend.model.AuditLog;

public interface AuditLogRepository extends JpaRepository<AuditLog, Long> {
    AuditLog findTopByOrderByIdDesc();
}