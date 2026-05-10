package com.human.common.http;

import lombok.Data;

@Data
public class ResponseDTO {

    private Long code;

    private String msg;

    private Object data;

    public static ResponseDTO ok() {
        ResponseDTO responseDTO = new ResponseDTO();
        responseDTO.setCode(200L);
        responseDTO.setMsg("success");
        return responseDTO;
    }

    public static ResponseDTO ok(Object data) {
        ResponseDTO responseDTO = ok();
        responseDTO.setData(data);
        return responseDTO;
    }

    public static ResponseDTO ok(Object data, String msg) {
        ResponseDTO responseDTO = new ResponseDTO();
        responseDTO.setCode(200L);
        responseDTO.setMsg(msg);
        responseDTO.setData(data);
        return responseDTO;
    }

    public static ResponseDTO error() {
        ResponseDTO responseDTO = new ResponseDTO();
        responseDTO.setCode(500L);
        responseDTO.setMsg("error");
        return responseDTO;
    }

    public static ResponseDTO error(String msg) {
        ResponseDTO responseDTO = new ResponseDTO();
        responseDTO.setCode(500L);
        responseDTO.setMsg(msg);
        return responseDTO;
    }

    public static ResponseDTO error(Long code, String msg) {
        ResponseDTO responseDTO = new ResponseDTO();
        responseDTO.setCode(code);
        responseDTO.setMsg(msg);
        return responseDTO;
    }
}
