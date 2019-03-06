package com.snsprj.sbsm.mapper;

import com.snsprj.sbsm.model.QuartzJob;
import com.snsprj.sbsm.model.QuartzJobExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface QuartzJobMapper {
    long countByExample(QuartzJobExample example);

    int deleteByExample(QuartzJobExample example);

    int insert(QuartzJob record);

    int insertSelective(QuartzJob record);

    List<QuartzJob> selectByExample(QuartzJobExample example);

    int updateByExampleSelective(@Param("record") QuartzJob record, @Param("example") QuartzJobExample example);

    int updateByExample(@Param("record") QuartzJob record, @Param("example") QuartzJobExample example);
}