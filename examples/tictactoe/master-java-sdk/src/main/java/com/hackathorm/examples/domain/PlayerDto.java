package com.hackathorm.examples.domain;

import lombok.Data;

@Data
public class PlayerDto {
    private String gameId;
    private String name;
    private String serviceAppId;
    private Character sign;
}
