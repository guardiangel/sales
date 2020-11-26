package com.scotia.sales.entity;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @author
 *  Felix
 * 自定义返回Json日期格式，用在实体类的对应日期的get方法中
 * 同时防止出现前端显示时间时，少8个小时的情况
 */
public final class CustomDateTimeSerializer extends JsonSerializer<Date> {
    @Override
    public void serialize(Date value, JsonGenerator gen, SerializerProvider serializers) throws IOException {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        gen.writeString(sdf.format(value));
    }
}
