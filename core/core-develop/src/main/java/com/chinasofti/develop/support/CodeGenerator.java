
package com.chinasofti.develop.support;

import com.baomidou.mybatisplus.annotation.DbType;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.core.toolkit.IdWorker;
import com.baomidou.mybatisplus.core.toolkit.StringPool;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.generator.AutoGenerator;
import com.baomidou.mybatisplus.generator.InjectionConfig;
import com.baomidou.mybatisplus.generator.config.*;
import com.baomidou.mybatisplus.generator.config.converts.MySqlTypeConvert;
import com.baomidou.mybatisplus.generator.config.converts.OracleTypeConvert;
import com.baomidou.mybatisplus.generator.config.converts.PostgreSqlTypeConvert;
import com.baomidou.mybatisplus.generator.config.po.TableInfo;
import com.baomidou.mybatisplus.generator.config.rules.DateType;
import com.baomidou.mybatisplus.generator.config.rules.NamingStrategy;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.apache.velocity.Template;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.Velocity;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.chinasofti.core.tool.constant.BootConstant;
import com.chinasofti.core.tool.utils.Func;
import com.chinasofti.core.tool.utils.StringUtil;
import com.chinasofti.develop.constant.DevelopConstant;
import com.chinasofti.develop.utils.VelocityInitializer;
import com.chinasofti.develop.utils.VelocityUtils;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PropertiesLoaderUtils;

import java.io.File;
import java.io.IOException;
import java.io.StringWriter;
import java.util.*;

/**
 * 代码生成器配置类
 *
 * @author Arvin Zhou
 */
public class CodeGenerator {
	
	private  final Logger log = LoggerFactory.getLogger(CodeGenerator.class);
	/**
	 * 代码所在系统
	 */
	private String systemName = DevelopConstant.SABER_NAME;
	/**
	 * 代码模块名称
	 */
	private String codeName;
	/**
	 * 代码所在服务名
	 */
	private String serviceName = "micro-service";
	public String getSystemName() {
		return systemName;
	}

	public void setSystemName(String systemName) {
		this.systemName = systemName;
	}

	public String getCodeName() {
		return codeName;
	}

	public void setCodeName(String codeName) {
		this.codeName = codeName;
	}

	public String getServiceName() {
		return serviceName;
	}

	public void setServiceName(String serviceName) {
		this.serviceName = serviceName;
	}

	public String getPackageName() {
		return packageName;
	}

	public void setPackageName(String packageName) {
		this.packageName = packageName;
	}

	public String getPackageDir() {
		return packageDir;
	}

	public void setPackageDir(String packageDir) {
		this.packageDir = packageDir;
	}

	public String getPackageWebDir() {
		return packageWebDir;
	}

	public void setPackageWebDir(String packageWebDir) {
		this.packageWebDir = packageWebDir;
	}

	public String[] getTablePrefix() {
		return tablePrefix;
	}

	public void setTablePrefix(String[] tablePrefix) {
		this.tablePrefix = tablePrefix;
	}

	public String[] getIncludeTables() {
		return includeTables;
	}

	public void setIncludeTables(String[] includeTables) {
		this.includeTables = includeTables;
	}

	public String[] getExcludeTables() {
		return excludeTables;
	}

	public void setExcludeTables(String[] excludeTables) {
		this.excludeTables = excludeTables;
	}

	public Boolean getHasSuperEntity() {
		return hasSuperEntity;
	}

	public void setHasSuperEntity(Boolean hasSuperEntity) {
		this.hasSuperEntity = hasSuperEntity;
	}

	public Boolean getHasWrapper() {
		return hasWrapper;
	}

	public void setHasWrapper(Boolean hasWrapper) {
		this.hasWrapper = hasWrapper;
	}

	public String[] getSuperEntityColumns() {
		return superEntityColumns;
	}

	public void setSuperEntityColumns(String[] superEntityColumns) {
		this.superEntityColumns = superEntityColumns;
	}

	public String getTenantColumn() {
		return tenantColumn;
	}

	public void setTenantColumn(String tenantColumn) {
		this.tenantColumn = tenantColumn;
	}

	public Boolean getIsSwagger2() {
		return isSwagger2;
	}

	public void setIsSwagger2(Boolean isSwagger2) {
		this.isSwagger2 = isSwagger2;
	}

	public String getDriverName() {
		return driverName;
	}

	public void setDriverName(String driverName) {
		this.driverName = driverName;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public Integer getPort() {
		return port;
	}

	public void setPort(Integer port) {
		this.port = port;
	}

	/**
	 * 代码生成的包名
	 */
	private String packageName;
	/**
	 * 代码后端生成的地址
	 */
	private String packageDir;
	/**
	 * 代码前端生成的地址
	 */
	private String packageWebDir;
	/**
	 * 需要去掉的表前缀
	 */
	private String[] tablePrefix = {"sys_"};
	/**
	 * 需要生成的表名(两者只能取其一)
	 */
	private String[] includeTables = {"sys_test"};
	/**
	 * 需要排除的表名(两者只能取其一)
	 */
	private String[] excludeTables = {};
	/**
	 * 是否包含基础业务字段
	 */
	private Boolean hasSuperEntity = Boolean.FALSE;
	/**
	 * 是否包含包装器
	 */
	private Boolean hasWrapper = Boolean.TRUE;
	/**
	 * 基础业务字段
	 */
	private String[] superEntityColumns = {"create_time", "create_user", "create_by", "update_time", "update_user", "status", "is_deleted"};
	/**
	 * 租户字段
	 */
	private String tenantColumn = "tenant_id";
	/**
	 * 是否启用swagger
	 */
	private Boolean isSwagger2 = Boolean.TRUE;
	/**
	 * 数据库驱动名
	 */
	private String driverName;
	/**
	 * 数据库链接地址
	 */
	private String url;
	/**
	 * 数据库用户名
	 */
	private String username;
	/**
	 * 数据库密码
	 */
	private String password;

	/**
	 * 应用的端口
	 */
	private Integer port = 9001;
	
	/**
	 * 
	 * @param cleanHistory  是否对历史文件进行清除
	 */
	public void runCfg() { 
				
		VelocityInitializer.initVelocity();
        VelocityContext context = VelocityUtils.prepareContext(this);
        List<Map<String,String>> templates = VelocityUtils.getTemplateList(this);
        for (Map<String,String> templateMap : templates)
        {
            // 渲染模板
            StringWriter sw = new StringWriter();
            String templateFileName = templateMap.keySet().toArray(new String[1])[0];
            String targetFilePath =  templateMap.values().toArray(new String[1])[0];
            Template tpl = Velocity.getTemplate(templateFileName, BootConstant.UTF_8);
            tpl.merge(context, sw);
            
            try
            {
                FileUtils.writeStringToFile( new File(targetFilePath), sw.toString() , BootConstant.UTF_8 );
            	
            }
            catch (IOException e)
            {
                log.error("渲染模板失败，文件名：" + templateFileName , e);
            }
            finally
            {
            	 IOUtils.closeQuietly(sw);
            }
        }
	}
	
	/**
	 * 
	 * @param cleanHistory  是否对历史文件清除
	 */
	public void runCode() {		
		AutoGenerator mpg = getAutoGenerator();
		mpg.setCfg(getInjectionConfigForCode());
		mpg.execute();
	}

	public void cleanHistory()
	{
		try {
			FileUtils.deleteDirectory( new File( this.getProjectOutputDir() ) );
			FileUtils.deleteDirectory( new File( this.getPackageWebDir() ) );
			FileUtils.deleteDirectory( new File( this.getPackageDir() ) );
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
	}
	
	private AutoGenerator getAutoGenerator()
	{
		Properties props = getProperties();
		AutoGenerator mpg = new AutoGenerator();
		GlobalConfig gc = new GlobalConfig();
		String outputDir = this.getCodeOutputDir();
		String author = props.getProperty("author");
		gc.setOutputDir(outputDir);
		gc.setAuthor(author);
		gc.setFileOverride(true);
		gc.setOpen(false);
		gc.setActiveRecord(false);
		gc.setEnableCache(false);
		gc.setBaseResultMap(true);
		gc.setBaseColumnList(true);
		gc.setMapperName("%sMapper");
		gc.setXmlName("%sMapper");
		gc.setServiceName("I%sService");
		gc.setServiceImplName("%sServiceImpl");
		gc.setControllerName("%sController");
		gc.setSwagger2(isSwagger2);
		gc.setIdType(IdType.ASSIGN_ID); //主键策略
		gc.setDateType(DateType.TIME_PACK); 
		mpg.setGlobalConfig(gc);
		DataSourceConfig dsc = new DataSourceConfig();
		String driverName = Func.toStr(this.driverName, props.getProperty("spring.datasource.driver-class-name"));
		if (StringUtil.containsAny(driverName, DbType.MYSQL.getDb())) {
			dsc.setDbType(DbType.MYSQL);
			dsc.setTypeConvert(new MySqlTypeConvert());
		} else if (StringUtil.containsAny(driverName, DbType.POSTGRE_SQL.getDb())) {
			dsc.setDbType(DbType.POSTGRE_SQL);
			dsc.setTypeConvert(new PostgreSqlTypeConvert());
		} else {
			dsc.setDbType(DbType.ORACLE);
			dsc.setTypeConvert(new OracleTypeConvert());
		}
		dsc.setDriverName(driverName);
		dsc.setUrl(Func.toStr(this.url, props.getProperty("spring.datasource.url")));
		dsc.setUsername(Func.toStr(this.username, props.getProperty("spring.datasource.username")));
		dsc.setPassword(Func.toStr(this.password, props.getProperty("spring.datasource.password")));
		mpg.setDataSource(dsc);
		// 策略配置
		StrategyConfig strategy = new StrategyConfig();
		// strategy.setCapitalMode(true);// 全局大写命名
		// strategy.setDbColumnUnderline(true);//全局下划线命名
		strategy.setNaming(NamingStrategy.underline_to_camel);
		strategy.setColumnNaming(NamingStrategy.underline_to_camel);
		strategy.setTablePrefix(tablePrefix);
		if (includeTables.length > 0) {
			strategy.setInclude(includeTables);
		}
		if (excludeTables.length > 0) {
			strategy.setExclude(excludeTables);
		}
		if (hasSuperEntity) {
			strategy.setSuperEntityClass("com.chinasofti.core.mp.base.BaseEntity");
			strategy.setSuperEntityColumns(superEntityColumns);
			strategy.setSuperServiceClass("com.chinasofti.core.mp.base.BaseService");
			strategy.setSuperServiceImplClass("com.chinasofti.core.mp.base.BaseServiceImpl");
		} else {
			strategy.setSuperServiceClass("com.baomidou.mybatisplus.extension.service.IService");
			strategy.setSuperServiceImplClass("com.baomidou.mybatisplus.extension.service.impl.ServiceImpl");
		}
		// 自定义 controller 父类
		strategy.setSuperControllerClass("com.chinasofti.core.boot.ctrl.BootController");
		strategy.setEntityBuilderModel(false);
		strategy.setEntityLombokModel(true);
		strategy.setControllerMappingHyphenStyle(true);
		mpg.setStrategy(strategy);
		// 包配置
		PackageConfig pc = new PackageConfig();
		// 控制台扫描
		pc.setModuleName(null);
		pc.setParent(packageName);
		pc.setController("controller");
		pc.setEntity("entity");
		pc.setXml("mapper");
		mpg.setPackageInfo(pc);
		return mpg;
	}
	
	private InjectionConfig getInjectionConfigForCode() {
		String servicePackage = serviceName.split("-").length > 1 ? serviceName.split("-")[1] : serviceName;
		// 自定义配置
		Map<String, Object> map = new HashMap<>(16);
		InjectionConfig cfg = new InjectionConfig() {
			@Override
			public void initMap() {
				map.put("codeName", codeName);
				map.put("serviceName", serviceName);
				map.put("servicePackage", servicePackage);
				map.put("servicePackageLowerCase", servicePackage.toLowerCase());
				map.put("tenantColumn", tenantColumn);
				map.put("hasWrapper", hasWrapper);
				this.setMap(map);
			}
		};
		List<FileOutConfig> focList = new ArrayList<>();		
		focList.add(new FileOutConfig("/templates/sql/menu.sql.vm") {
			@Override
			public String outputFile(TableInfo tableInfo) {
				map.put("entityKey", (tableInfo.getEntityName().toLowerCase()));
				map.put("menuId", IdWorker.getId());
				map.put("addMenuId", IdWorker.getId());
				map.put("editMenuId", IdWorker.getId());
				map.put("removeMenuId", IdWorker.getId());
				map.put("viewMenuId", IdWorker.getId());
				return getCodeOutputDir() + "/" + "/sql/" + tableInfo.getEntityName().toLowerCase() + ".menu.mysql";
			}
		});
		focList.add(new FileOutConfig("/templates/entityVO.java.vm") {
			@Override
			public String outputFile(TableInfo tableInfo) {
				return getCodeOutputDir() + "/" + packageName.replace(".", "/") + "/" + "vo" + "/" + tableInfo.getEntityName() + "VO" + StringPool.DOT_JAVA;
			}
		});
		focList.add(new FileOutConfig("/templates/entityDTO.java.vm") {
			@Override
			public String outputFile(TableInfo tableInfo) {
				return getCodeOutputDir() + "/" + packageName.replace(".", "/") + "/" + "dto" + "/" + tableInfo.getEntityName() + "DTO" + StringPool.DOT_JAVA;
			}
		});
		if (hasWrapper) {
			focList.add(new FileOutConfig("/templates/wrapper.java.vm") {
				@Override
				public String outputFile(TableInfo tableInfo) {
					return getCodeOutputDir() + "/" + packageName.replace(".", "/") + "/" + "wrapper" + "/" + tableInfo.getEntityName() + "Wrapper" + StringPool.DOT_JAVA;
				}
			});
		}
		if (Func.isNotBlank(packageWebDir)) {
			if (Func.equals(systemName, DevelopConstant.SWORD_NAME)) {
				focList.add(new FileOutConfig("/templates/sword/action.js.vm") {
					@Override
					public String outputFile(TableInfo tableInfo) {
						return getOutputWebDir() + "/actions" + "/" + tableInfo.getEntityName().toLowerCase() + ".js";
					}
				});
				focList.add(new FileOutConfig("/templates/sword/model.js.vm") {
					@Override
					public String outputFile(TableInfo tableInfo) {
						return getOutputWebDir() + "/models" + "/" + tableInfo.getEntityName().toLowerCase() + ".js";
					}
				});
				focList.add(new FileOutConfig("/templates/sword/service.js.vm") {
					@Override
					public String outputFile(TableInfo tableInfo) {
						return getOutputWebDir() + "/services" + "/" + tableInfo.getEntityName().toLowerCase() + ".js";
					}
				});
				focList.add(new FileOutConfig("/templates/sword/list.js.vm") {
					@Override
					public String outputFile(TableInfo tableInfo) {
						return getOutputWebDir() + "/pages" + "/" + StringUtil.upperFirst(servicePackage) + "/" + tableInfo.getEntityName() + "/" + tableInfo.getEntityName() + ".js";
					}
				});
				focList.add(new FileOutConfig("/templates/sword/add.js.vm") {
					@Override
					public String outputFile(TableInfo tableInfo) {
						return getOutputWebDir() + "/pages" + "/" + StringUtil.upperFirst(servicePackage) + "/" + tableInfo.getEntityName() + "/" + tableInfo.getEntityName() + "Add.js";
					}
				});
				focList.add(new FileOutConfig("/templates/sword/edit.js.vm") {
					@Override
					public String outputFile(TableInfo tableInfo) {
						return getOutputWebDir() + "/pages" + "/" + StringUtil.upperFirst(servicePackage) + "/" + tableInfo.getEntityName() + "/" + tableInfo.getEntityName() + "Edit.js";
					}
				});
				focList.add(new FileOutConfig("/templates/sword/view.js.vm") {
					@Override
					public String outputFile(TableInfo tableInfo) {
						return getOutputWebDir() + "/pages" + "/" + StringUtil.upperFirst(servicePackage) + "/" + tableInfo.getEntityName() + "/" + tableInfo.getEntityName() + "View.js";
					}
				});
			} else if (Func.equals(systemName, DevelopConstant.SABER_NAME)) {
				focList.add(new FileOutConfig("/templates/saber/api.js.vm") {
					@Override
					public String outputFile(TableInfo tableInfo) {
						return getOutputWebDir() + "/api" + "/" + servicePackage.toLowerCase() + "/" + tableInfo.getEntityName().toLowerCase() + ".js";
					}
				});
				focList.add(new FileOutConfig("/templates/saber/crud.vue.vm") {
					@Override
					public String outputFile(TableInfo tableInfo) {
						return getOutputWebDir() + "/views" + "/" + servicePackage.toLowerCase() + "/" + tableInfo.getEntityName().toLowerCase() + ".vue";
					}
				});
			}
		}
		cfg.setFileOutConfigList(focList);
		return cfg;
	}

	/**
	 * 获取配置文件
	 *
	 * @return 配置Props
	 */
	private Properties getProperties() {
		// 读取配置文件
		Resource resource = new ClassPathResource("/templates/code.properties");
		Properties props = new Properties();
		try {
			props = PropertiesLoaderUtils.loadProperties(resource);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return props;
	}

	/**
	 * 在项目中生成代码的路径
	 *
	 * @return outputDir
	 */
	public String getCodeOutputDir() {
		return getProjectOutputDir() + "/src/main/java";
	}

	/**
	 * 项目路径
	 * @return outputDir
	 */
	public String getProjectOutputDir()
	{
		return (Func.isBlank(packageDir) ? System.getProperty("user.dir") + "/blade-ops/blade-develop" : packageDir);
	}
	
	/**
	 * YAML路径
	 * @return outputDir
	 */
	public String getYmlOutputDir()
	{
		return getProjectOutputDir() +  "/src/main/resources";
	}
	
	/**
	 * 生成到Web项目中
	 *
	 * @return outputDir
	 */
	public String getOutputWebDir() {
		return (Func.isBlank(packageWebDir) ? System.getProperty("user.dir") : packageWebDir) + "/src";
	}

	/**
	 * 页面生成的文件名
	 */
	private String getGeneratorViewPath(String viewOutputDir, TableInfo tableInfo, String suffixPath) {
		String name = StringUtils.firstToLowerCase(tableInfo.getEntityName());
		String path = viewOutputDir + "/" + name + "/" + name + suffixPath;
		File viewDir = new File(path).getParentFile();
		if (!viewDir.exists()) {
			viewDir.mkdirs();
		}
		return path;
	}

}
