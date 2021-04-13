
package com.chinasofti.auth.granter;

import lombok.Data;
import com.chinasofti.core.tool.support.Kv;

/**
 * TokenParameter
 *
 * @author Arvin Zhou
 */
@Data
public class TokenParameter {

	private Kv args = Kv.init();

}
