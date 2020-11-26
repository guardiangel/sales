package com.scotia.sales.service.impl;

import com.scotia.sales.entity.Log;
import com.scotia.sales.entity.Role;
import com.scotia.sales.repository.LogRepository;
import com.scotia.sales.repository.UserRepository;
import com.scotia.sales.service.LogService;
import com.scotia.sales.util.StringUtil;
import org.apache.shiro.SecurityUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.persistence.criteria.Predicate;
import java.util.Date;
import java.util.List;

/**
 * @author
 *      Felix
 */
@Service("logService")
public class LogServiceImpl implements LogService {
    @Resource
    private LogRepository logRepository;

    @Resource
    private UserRepository userRepository;

    @Override
    public void save(Log log) {
        log.setTime(new Date());//设置操作日志
        log.setUser(userRepository.findByUserName((String) SecurityUtils.getSubject().getPrincipal()));//设置操作用户
        logRepository.save(log);
    }

    @Override
    public List<Log> list(Log log, Integer page, Integer pageSize, Sort.Direction direction, String... properties) {

        PageRequest pageRequest = PageRequest.of(page - 1, pageSize);

        Page<Log> pageLog = logRepository.findAll((Specification<Log>) (root, query, criteriaBuilder) -> {
            Predicate predicate = criteriaBuilder.conjunction();
            if (log != null) {
                if (log.getUser() != null && StringUtil.isNotEmpty(log.getUser().getTrueName())) {
                    predicate.getExpressions().add(criteriaBuilder.like(root.get("user").get("trueName"), "%" + log.getUser().getTrueName() + "%"));
                }
                if (StringUtil.isNotEmpty(log.getType())) {
                    predicate.getExpressions().add(criteriaBuilder.equal(root.get("type"), log.getType()));
                }
                if (log.getBtime() != null) {
                    predicate.getExpressions().add(criteriaBuilder.greaterThanOrEqualTo(root.get("time"), log.getBtime()));
                }
                if (log.getEtime() != null) {
                    predicate.getExpressions().add(criteriaBuilder.greaterThanOrEqualTo(root.get("time"), log.getEtime()));
                }
            }
            return predicate;
        }, pageRequest);

        return pageLog.getContent();
    }

    @Override
    public Long getCount(Log log) {
        return logRepository.count((Specification<Log>) (root, query, cb) -> {
            Predicate predicate=cb.conjunction();
            if(log!=null){
                if(log.getUser()!=null && StringUtil.isNotEmpty(log.getUser().getTrueName())){
                    predicate.getExpressions().add(cb.like(root.get("user").get("trueName"), "%"+log.getUser().getTrueName()+"%"));
                }
                if(StringUtil.isNotEmpty(log.getType())){
                    predicate.getExpressions().add(cb.equal(root.get("type"), log.getType()));
                }
                if(log.getBtime()!=null){
                    predicate.getExpressions().add(cb.greaterThanOrEqualTo(root.get("time"), log.getBtime()));
                }
                if(log.getEtime()!=null){
                    predicate.getExpressions().add(cb.lessThanOrEqualTo(root.get("time"), log.getEtime()));
                }
            }
            return predicate;
        });
    }
}
