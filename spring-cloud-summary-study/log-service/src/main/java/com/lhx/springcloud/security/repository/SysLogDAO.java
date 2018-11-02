package com.lhx.springcloud.security.repository;

import com.lhx.springcloud.security.domain.SysLog;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @author lihongxu
 * @desc @link(https://github.com/bjlhx15/spring-cloud-base)
 * @since 2018/11/1 下午5:43
 */
public interface SysLogDAO extends JpaRepository<SysLog, Long> {
}
