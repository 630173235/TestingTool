
package com.chinasofti.system.dto;

import lombok.Data;
import lombok.EqualsAndHashCode;
import com.chinasofti.system.entity.RoleMenu;

/**
 * 数据传输对象实体类
 *
 *  @author Arvin Zhou
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class RoleMenuDTO extends RoleMenu {
	private static final long serialVersionUID = 1L;

}
