package com.hackathorm.examples.domain;

import lombok.Data;

import java.util.List;

@Data
public class GameMoveRequest {
    private List<List<Character>> board;
    private Character playerMark;
}
