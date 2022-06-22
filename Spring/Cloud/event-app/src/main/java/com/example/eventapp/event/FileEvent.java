package com.example.eventapp.event;

import lombok.Builder;
import lombok.Getter;

import java.util.Map;
import java.util.UUID;

@Getter
@Builder
public class FileEvent {
    private String id;
    private String type;
    private Map<String, Object> data;

    public static FileEvent completeEvent(Map data){
        return FileEvent.builder()
                .id(UUID.randomUUID().toString())
                .type(EventType.COMPLETE.toString())
                .data(data)
                .build();
    }
    public static FileEvent errorEvent(Map data){
        return FileEvent.builder()
                .id(UUID.randomUUID().toString())
                .type(EventType.ERROR.toString())
                .data(data)
                .build();
    }
}
