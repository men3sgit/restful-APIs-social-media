package com.rse.mobile.webservice.dto.mapper;

import java.util.function.Function;

public interface DTOMapper<R, T> extends Function<R, T> {
    @Override
    T apply(R r);
}
