package com.cdek.dao.impl;

import com.cdek.dao.FiasDao;
import com.cdek.dao.mapper.FiasMapper;
import com.cdek.entity.Fias;
import org.springframework.stereotype.Repository;

@Repository
public class FiasDaoImpl implements FiasDao {

    private final FiasMapper fiasMapper;

    public FiasDaoImpl(FiasMapper fiasMapper) {
        this.fiasMapper = fiasMapper;
    }

    @Override
    public String createFias(Fias fias) {
        return fiasMapper.insertFias(fias);
    }


}
