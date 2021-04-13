package com.chinasofti.core.launch.utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Properties;
import lombok.extern.slf4j.Slf4j;

/**
 * 
 * @author Arvin Zhou
 */
@Slf4j
public class PropertiesFileUtils {
    /**
     * 构造函数私有化
     */
    private PropertiesFileUtils() {

    }

    /**
     * 保存或更新properties文件中的key
     * 
     * @param fileName
     * @param key
     * @param value
     */
    public static void saveOrUpdateProperty(String fileName, String key, String value) {
        Properties properties = new Properties();
        InputStream inputStream = null;
        OutputStream outputStream = null;
        try {
            String path = PropertiesFileUtils.class.getClassLoader().getResource(fileName).getPath();
            log.debug("path -> {}", path);
            inputStream = new FileInputStream(new File(path));
            properties.load(inputStream);
            properties.setProperty(key, value);
            // 保存到文件中(如果有的话会自动更新，没有会创建)
            outputStream = new FileOutputStream(new File(path));
            properties.store(outputStream, "");

           
        } catch (FileNotFoundException e) {
            log.error("saveOrUpdateProperty error", e);
        } catch (IOException e) {
            log.error("saveOrUpdateProperty error", e);
        }
        finally
        {
        	if( outputStream != null )
				try {
					outputStream.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
        	if( inputStream != null )
				try {
					inputStream.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
        }
    }

    /**
     * 获取Properties
     * 
     * @param fileName
     * @param key
     * @return
     */
    public static String getPropertyValue(String fileName, String key) {
        Properties properties = new Properties();
        InputStream inputStream = null;
        String value = "";
        try {
        	inputStream = PropertiesFileUtils.class.getClassLoader().getResourceAsStream(fileName);
            //inputStream = new FileInputStream(new File(path));
            properties.load(inputStream);

            value = properties.getProperty(key);
            // 保存到文件中(如果有的话会自动更新，没有会创建)
            
        } catch (FileNotFoundException e) {
            log.error("getPropertyValue error", e);
        } catch (IOException e) {
            log.error("getPropertyValue error", e);
        }
        finally
        {
        	if( inputStream != null )
				try {
					inputStream.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
        }
       
        return value;
    }

    /**
     * 获取Properties
     * 
     * @param fileName
     * @return
     */
    public static Properties getProperties(String fileName) {
        Properties properties = new Properties();
        InputStream inputStream = null ;
        try {
            String path = PropertiesFileUtils.class.getClassLoader().getResource(fileName).getPath();
            log.info("path -> {}", path);
            inputStream = new FileInputStream(new File(path));
            properties.load(inputStream);
        } catch (FileNotFoundException e) {
            log.error("saveOrUpdateProperty error", e);
        } catch (IOException e) {
            log.error("saveOrUpdateProperty error", e);
        }
        finally
        {
        	if( inputStream != null )
				try {
					inputStream.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
        }
        
        return properties;
    }

    /**
     * 获取Properties
     * 
     * @param fileName
     * @return
     */
    public static Properties removeProperty(String fileName, String key) {
        Properties properties = new Properties();
        InputStream inputStream = null;
        OutputStream outputStream = null;
        try {
            String path = PropertiesFileUtils.class.getClassLoader().getResource(fileName).getPath();
            log.info("path -> {}", path);
            inputStream = new FileInputStream(new File(path));
            properties.load(inputStream);
            log.info("properties -> {}", properties);
            if (properties != null && properties.containsKey(key)) {
                log.info("remove key:{}", key);
                properties.remove(key);
            }

            // 保存到文件中(将properties保存到文件)
            outputStream = new FileOutputStream(new File(path));
            properties.store(outputStream, "");
        } catch (FileNotFoundException e) {
            log.error("saveOrUpdateProperty error", e);
        } catch (IOException e) {
            log.error("saveOrUpdateProperty error", e);
        }
        finally
        {
        	if( outputStream != null )
				try {
					outputStream.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
        	if( inputStream != null )
				try {
					inputStream.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
        }
        return properties;
    }

    /**
     * 保存或更新properties文件中的key
     * 
     * @param path
     *            文件全路径
     * @param key
     * @param value
     */
    public static void saveOrUpdatePropertyByFilePath(String path, String key, String value) {
        Properties properties = new Properties();
        InputStream inputStream = null;
        OutputStream outputStream = null;
        try {
            log.debug("path -> {}", path);
            inputStream = new FileInputStream(new File(path));
            properties.load(inputStream);
            properties.setProperty(key, value);

            // 保存到文件中(如果有的话会自动更新，没有会创建)
            outputStream = new FileOutputStream(new File(path));
            properties.store(outputStream, "");
        } catch (FileNotFoundException e) {
            log.error("saveOrUpdateProperty error", e);
        } catch (IOException e) {
            log.error("saveOrUpdateProperty error", e);
        }
        finally
        {
        	if( outputStream != null )
				try {
					outputStream.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
        	if( inputStream != null )
				try {
					inputStream.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
        }
    }

    /**
     * 获取Properties
     * 
     * @param path
     *            文件全路径
     * @param key
     * @return
     */
    public static String getPropertyValueByFilePath(String path, String key) {
        Properties properties = new Properties();
        InputStream inputStream = null;
        String value = "";
        try {
            log.info("path -> {}", path);
            inputStream = new FileInputStream(new File(path));
            properties.load(inputStream);

            value = properties.getProperty(key);
            // 保存到文件中(如果有的话会自动更新，没有会创建)
        } catch (FileNotFoundException e) {
            log.error("saveOrUpdateProperty error", e);
        } catch (IOException e) {
            log.error("saveOrUpdateProperty error", e);
        }
        finally
        {
        	if( inputStream != null )
				try {
					inputStream.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
        }
        return value;
    }

    /**
     * 获取Properties
     * 
     * @param path
     *            文件全路径
     * @return
     */
    public static Properties getPropertiesByFilePath(String path) {
        Properties properties = new Properties();
        InputStream inputStream = null;
        try {
            log.info("path -> {}", path);
            inputStream = new FileInputStream(new File(path));
            properties.load(inputStream);
        } catch (FileNotFoundException e) {
            log.error("saveOrUpdateProperty error", e);
        } catch (IOException e) {
            log.error("saveOrUpdateProperty error", e);
        }
        finally
        {
        	if( inputStream != null )
				try {
					inputStream.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
        }
        return properties;
    }

    /**
     * 获取Properties
     * 
     * @param path
     *            文件全路径
     * @param key
     *            key值
     * @return
     */
    public static Properties removePropertyByFilePath(String path, String key) {
        Properties properties = new Properties();
        InputStream inputStream = null;
        OutputStream outputStream = null;
        try {
            log.info("path -> {}", path);
            inputStream = new FileInputStream(new File(path));
            properties.load(inputStream);
            log.info("properties -> {}", properties);
            if (properties != null && properties.containsKey(key)) {
                log.info("remove key:{}", key);
                properties.remove(key);
            }

            // 保存到文件中(将properties保存到文件)
            outputStream = new FileOutputStream(new File(path));
            properties.store(outputStream, "");
        } catch (FileNotFoundException e) {
            log.error("saveOrUpdateProperty error", e);
        } catch (IOException e) {
            log.error("saveOrUpdateProperty error", e);
        }
        finally
        {
        	if( outputStream != null )
				try {
					outputStream.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
        	if( inputStream != null )
				try {
					inputStream.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
        }
        return properties;
    }
}