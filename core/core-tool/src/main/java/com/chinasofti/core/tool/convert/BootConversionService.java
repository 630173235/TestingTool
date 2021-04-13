package com.chinasofti.core.tool.convert;

import org.springframework.boot.convert.ApplicationConversionService;
import org.springframework.core.convert.support.GenericConversionService;
import org.springframework.lang.Nullable;
import org.springframework.util.StringValueResolver;

/**
 * 类型 转换 服务，添加了 IEnum 转换
 *
 * @author L.cm
 */
public class BootConversionService extends ApplicationConversionService {
	@Nullable
	private static volatile BootConversionService SHARED_INSTANCE;

	public BootConversionService() {
		this(null);
	}

	public BootConversionService(@Nullable StringValueResolver embeddedValueResolver) {
		super(embeddedValueResolver);
		super.addConverter(new EnumToStringConverter());
		super.addConverter(new StringToEnumConverter());
	}

	/**
	 * Return a shared default application {@code ConversionService} instance, lazily
	 * building it once needed.
	 * <p>
	 * Note: This method actually returns an {@link BootConversionService}
	 * instance. However, the {@code ConversionService} signature has been preserved for
	 * binary compatibility.
	 * @return the shared {@code BladeConversionService} instance (never{@code null})
	 */
	public static GenericConversionService getInstance() {
		BootConversionService sharedInstance = BootConversionService.SHARED_INSTANCE;
		if (sharedInstance == null) {
			synchronized (BootConversionService.class) {
				sharedInstance = BootConversionService.SHARED_INSTANCE;
				if (sharedInstance == null) {
					sharedInstance = new BootConversionService();
					BootConversionService.SHARED_INSTANCE = sharedInstance;
				}
			}
		}
		return sharedInstance;
	}

}
