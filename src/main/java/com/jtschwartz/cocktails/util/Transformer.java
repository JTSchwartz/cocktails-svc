package com.jtschwartz.cocktails.util;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class Transformer {

    private final ModelMapper modelMapper;

    public <T> T transform(Object src, Class<T> destClass) {
        try {
            return modelMapper.map(src, destClass);
        } catch (Exception e) {
            log.error("Unable to map {} to {}", src.getClass(), destClass, e);
            throw new RuntimeException(e);
        }
    }
}
