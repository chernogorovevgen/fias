package com.cdek.dao;

import com.cdek.entity.Fias;

import javax.validation.constraints.NotNull;

public interface FiasDao {

    String createFias(@NotNull Fias fias);

}
