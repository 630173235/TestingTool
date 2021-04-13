package com.chinasofti.testing.vo;

import com.chinasofti.testing.entity.Report;
import lombok.Data;
import lombok.EqualsAndHashCode;
import io.swagger.annotations.ApiModel;

/**
 * 视图实体类
 *
 * @author Arvin
 * @since 2021-02-24
 */
@Data
@EqualsAndHashCode(callSuper = true)
@ApiModel(value = "ReportVO对象", description = "ReportVO对象")
public class ReportVO extends Report {
	private static final long serialVersionUID = 1L;

}
