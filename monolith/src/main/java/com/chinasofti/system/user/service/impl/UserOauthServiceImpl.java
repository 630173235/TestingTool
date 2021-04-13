
package com.chinasofti.system.user.service.impl;


import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.AllArgsConstructor;
import com.chinasofti.system.user.entity.UserOauth;
import com.chinasofti.system.user.mapper.UserOauthMapper;
import com.chinasofti.system.user.service.IUserOauthService;
import org.springframework.stereotype.Service;

/**
 * 服务实现类
 *
 *  @author Arvin Zhou
 */
@Service
@AllArgsConstructor
public class UserOauthServiceImpl extends ServiceImpl<UserOauthMapper, UserOauth> implements IUserOauthService {

}
