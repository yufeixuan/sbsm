package com.snsprj.sbsm.service.impl;

import com.snsprj.sbsm.common.ServerResponse;
import com.snsprj.sbsm.vo.UserVo;
import com.snsprj.sbsm.dao.UserDao;
import com.snsprj.sbsm.mapper.UserMapper;
import com.snsprj.sbsm.model.User;
import com.snsprj.sbsm.service.UserService;
import com.snsprj.sbsm.utils.Chinese2PinyinUtil;
import com.snsprj.sbsm.utils.idc.IdCenter;
import lombok.extern.slf4j.Slf4j;
import net.sourceforge.pinyin4j.format.exception.BadHanyuPinyinOutputFormatCombination;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Transactional
@Service
@Slf4j
public class UserServiceImpl implements UserService {

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private UserDao userManualMapper;

    @Override
    public ServerResponse createUser(UserVo userVo) {

        String account = userVo.getAccount();
        log.info("====>insert user, user account is {}", account);

        User user = userManualMapper.getUserByAccount(account);

        if (user != null){
            // TODO 用户已存在，返回错误code
            return null;
        }else {
            user = new User();
            user.setId(IdCenter.getId());
            user.setAccount(account);
            try {
                user.setAccountPinyin(Chinese2PinyinUtil.toPinYin(account));
            } catch (BadHanyuPinyinOutputFormatCombination badHanyuPinyinOutputFormatCombination) {
                badHanyuPinyinOutputFormatCombination.printStackTrace();
            }

            // TODO 使用盐值加密密码，获取随机6位的盐值
            user.setSalt("123456");
            user.setPassword("123456");

            user.setEmail(userVo.getEmail());
            user.setMobile(userVo.getMobile());
            userMapper.insert(user);

            return ServerResponse.createBySuccess();
        }
    }
}
