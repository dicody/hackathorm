package com.hackathorm.examples.domain;

import lombok.Data;

@Data
public class PlayerMoveRequest {
    private Character[][] board;
    private Character playerSign;
}
