package com.chinasofti.testing.dto;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;

@Setter
@Getter
public class DetailApiModuleResponse extends SaveApiModuleRequest{

    private String num;
}
