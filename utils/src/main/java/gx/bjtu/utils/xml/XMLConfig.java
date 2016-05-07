package gx.bjtu.utils.xml;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * XML静态工具类
 * Created by jean on 16-5-7.
 */
public class XMLConfig {
    private static final Logger log = LoggerFactory.getLogger(XMLConfig.class);
    private static String xmlFile;
    private static XMLProperties xmlProperties = null;

    /**
     * 根据名称返回值
     *
     * @param name
     * @return
     */
    public static String getXMLProperty(String name) {
        if (xmlProperties == null) {
            loadSetupProperties();
        }

        if (xmlProperties == null) {
            return null;
        }

        return xmlProperties.getProperty(name);
    }

    /**
     * 根据名称返回值，有默认值
     *
     * @param name
     * @param defaultValue
     * @return
     */
    public static String getXMLProperty(String name, String defaultValue) {
        String value = getXMLProperty(name);
        if (value == null) {
            value = defaultValue;
        }
        return value;
    }

    /**
     * 根据名称返回值，有默认值
     *
     * @param name
     * @param defaultValue
     * @return
     */
    public static int getXMLProperty(String name, int defaultValue) {
        String value = getXMLProperty(name);
        if (value != null) {
            try {
                return Integer.parseInt(value);
            } catch (NumberFormatException nfe) {
                nfe.printStackTrace();
            }
        }
        return defaultValue;
    }

    /**
     * 根据名称返回值，有默认值
     *
     * @param name
     * @param defaultValue
     * @return
     */
    public static boolean getXMLProperty(String name, boolean defaultValue) {
        String value = getXMLProperty(name);
        if (value != null) {
            try {
                return Boolean.valueOf(value);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return defaultValue;
    }

    /**
     * 根据父节点名称获取字节点值
     *
     * @param parent
     * @return
     */
    @SuppressWarnings("unchecked")
    public static List<String> getXMLPropertiesList(String parent) {
        if (xmlProperties == null) {
            loadSetupProperties();
        }
        if (xmlProperties == null) {
            return Collections.EMPTY_LIST;
        }
        String[] propNames = xmlProperties.getChildrenProperties(parent);
        List<String> values = new ArrayList<String>();
        for (String propName : propNames) {
            String value = getXMLProperty(parent + "." + propName);
            if (value != null) {
                values.add(value);
            }
        }
        return values;
    }

    /**
     * 根据父节点名称获取字节点值
     *
     * @param parent
     * @return
     */
    public static String[] getXMLPropertiesArray(String parent) {
        if (xmlProperties == null) {
            loadSetupProperties();
        }
        if (xmlProperties == null) {
            return new String[0];
        }
        String[] propNames = xmlProperties.getChildrenProperties(parent);
        List<String> values = new ArrayList<String>();
        for (String propName : propNames) {
            String value = getXMLProperty(parent + "." + propName);
            if (value != null) {
                values.add(value);
            }
        }
        String[] array = new String[values.size()];
        return values.toArray(array);
    }

    /**
     * 加载配置文件
     */
    private synchronized static void loadSetupProperties() {
        if (xmlProperties == null) {
            try {
                if (xmlFile != null && xmlFile.trim().length() != 0) {
                    xmlProperties = new XMLProperties(xmlFile);
                } else {
                    log.error("xmlFile is not init");
                }
            } catch (IOException ioe) {
                log.error(ioe.getMessage(), ioe);
            }
        }
    }

    /**
     * 设置配置文件地址
     *
     * @param xmlFile
     */
    public static void setXmlFile(String xmlFile) {
        XMLConfig.xmlFile = xmlFile;
    }

    public static void main(String[] args) {

        String configPath = XMLConfig.class.getClassLoader().getResource("config.xml").getFile();
        XMLConfig.setXmlFile(configPath);
        System.out.println(XMLConfig.getXMLProperty("is_success"));
        System.out.println(Arrays.asList(XMLConfig.getXMLPropertiesArray("request")));
        // TODO 相同节点名重复读了第一个
    }
}
