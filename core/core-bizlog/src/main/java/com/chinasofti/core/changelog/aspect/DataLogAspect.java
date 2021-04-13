package com.chinasofti.core.changelog.aspect;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.ReflectUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.json.JSONUtil;
import com.baomidou.mybatisplus.core.toolkit.Constants;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import lombok.AllArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;

import org.apache.ibatis.mapping.SqlCommandType;
import org.apache.ibatis.session.SqlSession;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.reflect.MethodSignature;
import org.mybatis.spring.SqlSessionUtils;
import com.chinasofti.core.changelog.annotation.DataChangeLog;
import com.chinasofti.core.changelog.entity.DataLog;
import com.chinasofti.core.changelog.handle.BaseDataLog;
import com.chinasofti.core.changelog.handle.DataChange;
import com.chinasofti.core.changelog.handle.DataLogHandler;
import com.chinasofti.core.secure.utils.SecureUtil;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletRequest;

import java.util.*;
import java.util.stream.Collectors;

/**
 * <p>
 * DataLog切面
 * </p>
 */
@Slf4j
@Aspect
@Order(99)
@Component
@AllArgsConstructor
public class DataLogAspect {

    private final BaseDataLog baseDataLog;

    private DataLogHandler dataLogHandler;
    /**
     * <p>
     * 初始化
     * </p>
     */
    @PostConstruct
    public void init() {
        baseDataLog.setting();
    }
    
    /**
     * <p>
     * 切面前执行
     * </p>
     */
    @SneakyThrows
    @Before("@annotation(dataChangeLog)")
    public void before(JoinPoint joinPoint, DataChangeLog dataChangeLog) {
        // 使用 ThreadLocal 记录一次操作
    	log.debug( "更新操作前拦截" );
        BaseDataLog.DATA_CHANGES.set(new LinkedList<>());
        BaseDataLog.JOIN_POINT.set(joinPoint);
        BaseDataLog.DATA_LOG.set(dataChangeLog);
    }

    /**
     * <p>
     * 切面后执行
     * </p>
     */
    @SneakyThrows
    @AfterReturning("@annotation(dataChangeLog)")
    public void after(DataChangeLog dataChangeLog) {
    	log.debug( "更新操作后拦截" );
        List<DataChange> list = BaseDataLog.DATA_CHANGES.get();
        if (CollUtil.isEmpty(list)) {
            return;
        }
        List<DataLog> datalogs = new ArrayList<DataLog>();
        log.debug( getIpAddress() + ":" + dataChangeLog.type() + ":" + dataChangeLog.tag() + dataChangeLog.note() + ":" );
        list.forEach(change -> {
        	DataLog dataLog = new DataLog();
        	dataLog.setCreateBy( SecureUtil.getUser().getUserName() );
        	dataLog.setCreateDept( SecureUtil.getUser().getDeptId() );
        	dataLog.setTenantId( SecureUtil.getUser().getTenantId() );
        	dataLog.setIp( getIpAddress() );
        	dataLog.setMoudle( dataChangeLog.type()  );
        	dataLog.setTag( dataChangeLog.tag() );
        	dataLog.setNote( dataChangeLog.note() );
        	dataLog.setOperationType(change.getType() );
            if( change.getType() == SqlCommandType.UPDATE.name() )
            {
            	List<?> oldData = change.getOldData();
            	if (CollUtil.isEmpty(oldData)) {
                    return;
                }
                List<Long> ids = oldData.stream()
                        .map(o -> ReflectUtil.invoke(o, "getId").toString())
                        .filter(ObjectUtil::isNotNull)
                        .map(Long::parseLong)
                        .collect(Collectors.toList());
                log.debug( "ids = "+ ids);
                SqlSession sqlSession = change.getSqlSessionFactory().openSession();
                try {
                    Map<String, Object> map = new HashMap<>(1);
                    map.put(Constants.WRAPPER, Wrappers.query().in("id", ids));
                    
                    List<?> newData = sqlSession.selectList(change.getSqlStatement(), map);
                    log.debug( change.getSqlStatement().toString());
                    change.setNewData(Optional.ofNullable(newData).orElse(new ArrayList<>()));
                } finally {
                    SqlSessionUtils.closeSqlSession(sqlSession, change.getSqlSessionFactory());
                }
                
                dataLog.setOldData(JSONUtil.toJsonStr(change.getOldData()));
                dataLog.setNewData(JSONUtil.toJsonStr(change.getNewData()));
                log.debug("oldData:" + JSONUtil.toJsonStr(change.getOldData()));
                log.debug("newData:" + JSONUtil.toJsonStr(change.getNewData()));
                // 对比调模块
                dataLog.setDataDiff(this.compareAndTransfer(list));
            }
            else if(  change.getType() == SqlCommandType.INSERT.name()  )
            {
            	JoinPoint _joinPoint = BaseDataLog.JOIN_POINT.get();
            	MethodSignature signature = (MethodSignature) _joinPoint.getSignature();
            	//Method method = signature.getMethod();
                Object[] args = _joinPoint.getArgs();
                String params = JSONUtil.toJsonStr(args);
                log.debug( "newDate " + params );
                dataLog.setNewData(JSONUtil.toJsonStr(change.getNewData()));
            }
            else if(   change.getType() == SqlCommandType.DELETE.name()  )
            {
            	 log.debug("oldData:" + JSONUtil.toJsonStr(change.getOldData()));
            	 dataLog.setOldData(JSONUtil.toJsonStr(change.getOldData()));
            }
            
            datalogs.add( dataLog );
        });
        dataLogHandler.log( datalogs );
        baseDataLog.transfer();
    }

    private String getIpAddress()
    {
    	//获取RequestAttributes
    	RequestAttributes requestAttributes = RequestContextHolder.getRequestAttributes();
    	HttpServletRequest request = (HttpServletRequest) requestAttributes.resolveReference(RequestAttributes.REFERENCE_REQUEST);
    	String ip = request.getHeader("x-forwarded-for");  
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {  
            ip = request.getHeader("Proxy-Client-IP");  
        }  
         if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {  
            ip = request.getHeader("WL-Proxy-Client-IP");  
         }  
         if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {  
             ip = request.getHeader("HTTP_CLIENT_IP");  
         }  
         if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {  
            ip = request.getHeader("HTTP_X_FORWARDED_FOR");  
         }  
         if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {  
             ip = request.getRemoteAddr();  
         }  
         return ip; 
    }
    /**
     * <p>
     * 对比保存
     * </p>
     */
    public String compareAndTransfer(List<DataChange> list) {
        StringBuilder sb = new StringBuilder();
        StringBuilder rsb = new StringBuilder();
        list.forEach(change -> {
            List<?> oldData = change.getOldData();
            List<?> newData = change.getNewData();
            // 更新前后数据量不对必定是删除（逻辑删除）不做处理
            if (newData == null || oldData.size() != newData.size()) {
                return;
            }
            // 按id排序
            oldData.sort(Comparator.comparingLong(d -> Long.parseLong(ReflectUtil.invoke(d, "getId").toString())));
            newData.sort(Comparator.comparingLong(d -> Long.parseLong(ReflectUtil.invoke(d, "getId").toString())));

            for (int i = 0; i < oldData.size(); i++) {
                final int[] finalI = {0};
                baseDataLog.sameClazzDiff(oldData.get(i), newData.get(i)).forEach(r -> {
                    if (finalI[0] == 0) {
                        sb.append(StrUtil.LF);
                        sb.append(StrUtil.format("修改表：【{}】;", change.getTableName()));
                        sb.append(StrUtil.format("id：【{}】;", r.getId()));
                    }
                    sb.append(StrUtil.LF);
                    rsb.append(StrUtil.LF);
                    sb.append(StrUtil.format("把字段[{}]从[{}]改为[{}];",
                            r.getFieldName(), r.getOldValue(), r.getNewValue()));
                    rsb.append(StrUtil.indexedFormat(baseDataLog.getLogFormat(), r.getId(),
                            r.getFieldName(), r.getFieldComment(), r.getOldValue(), r.getNewValue()));
                    finalI[0]++;
                });
            }
        });
        if (sb.length() > 0) {
            sb.deleteCharAt(0);
            rsb.deleteCharAt(0);
        }
        // 存库
        log.debug(sb.toString());
        log.debug(rsb.toString());
        BaseDataLog.DATA_CHANGES.set(list);
        BaseDataLog.LOG_STR.set(rsb.toString());
        return rsb.toString();
    }

}
