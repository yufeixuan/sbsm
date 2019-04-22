package com.snsprj.sbsm.dao;

import com.snsprj.sbsm.mapper.UserMapper;
import com.snsprj.sbsm.model.User;
import com.snsprj.sbsm.model.UserExample;
import java.util.List;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * 封装 UserMapper 接口
 *
 * @author xiaohb
 */
@Slf4j
@Component
public class UserDao {

    @Autowired
    private UserMapper userMapper;

    /**
     * getUserByAccount
     *
     * @param account account
     * @return User
     */
    public User getUserByAccount(String account) {

        if (StringUtils.isBlank(account)) {
            return null;
        } else {
            UserExample userExample = new UserExample();
            userExample.createCriteria().andAccountEqualTo(account);

            List<User> userList = userMapper.selectByExample(userExample);

            if (userList != null) {

                if (userList.size() > 1) {
                    log.error("====>getUserByAccount, account is {}, result size is {}", userList.size());
                }
                return userList.get(0);
            } else {
                return null;
            }
        }
    }
}
