package com.cdek.dao.mapper;

import com.cdek.entity.Fias;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface FiasMapper {
    String insertFias(@Param("entity") Fias fias);
}
