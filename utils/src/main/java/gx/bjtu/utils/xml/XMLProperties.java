package gx.bjtu.utils.xml;

import org.apache.commons.lang3.StringEscapeUtils;
import org.dom4j.CDATA;
import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.Node;
import org.dom4j.io.SAXReader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.util.*;


/**
 * 解析XML属性配置文件
 * Created by jean on 16-5-7.
 */
public class XMLProperties {
    private static final Logger log = LoggerFactory.getLogger(XMLProperties.class);
    private Document document;

    /**
     * 缓存配置
     */
    private Map<String, String> propertyCache = new HashMap<String, String>();

    /**
     * 构造方法
     *
     * @param fileName
     * @throws IOException
     */
    public XMLProperties(String fileName) throws IOException {
        this(new File(fileName));
        log.debug("config file:" + fileName);
    }

    /**
     * 加载配置文件
     *
     * @param in
     * @throws IOException
     */
    public XMLProperties(InputStream in) throws IOException {
        Reader reader = new BufferedReader(new InputStreamReader(in, "UTF-8"));
        buildDoc(reader);
    }

    /**
     * 构造方法
     *
     * @param file
     * @throws IOException
     */
    public XMLProperties(File file) throws IOException {
        if (!file.exists()) {
            File tempFile;
            tempFile = new File(file.getParentFile(), file.getName() + ".tmp");
            if (tempFile.exists()) {
                log.warn("WARNING: " + file.getName() + " was not found, but temp file from " +
                        "previous write operation was. Attempting automatic recovery." +
                        " Please check file for data consistency.");
                tempFile.renameTo(file);
            } else {
                throw new FileNotFoundException("XML properties file does not exist: "
                        + file.getName());
            }
        }
        // 检查权限
        if (!file.canRead()) {
            throw new IOException("XML properties file must be readable: " + file.getName());
        }
        if (!file.canWrite()) {
            throw new IOException("XML properties file must be writable: " + file.getName());
        }
        FileReader reader = new FileReader(file);
        buildDoc(reader);
    }

    /**
     * 根据节点名获取值
     *
     * @param name
     * @return
     */
    public synchronized String getProperty(String name) {
        String value = propertyCache.get(name);
        if (value != null) {
            return value;
        }
        String[] propName = parsePropertyName(name);
        Element element = document.getRootElement();
        for (String aPropName : propName) {
            element = element.element(aPropName);
            if (element == null) {
                return null;
            }
        }
        value = element.getTextTrim();
        if ("".equals(value)) {
            return null;
        } else {
            propertyCache.put(name, value);
            return value;
        }
    }

    /**
     * 根据父节点名获取子节点值
     *
     * @param name
     * @return
     */
    public Iterator<?> getChildProperties(String name) {
        String[] propName = parsePropertyName(name);
        Element element = document.getRootElement();
        for (int i = 0; i < propName.length - 1; i++) {
            element = element.element(propName[i]);
            if (element == null) {
                return Collections.EMPTY_LIST.iterator();
            }
        }
        Iterator<?> iter = element.elementIterator(propName[propName.length - 1]);
        ArrayList<String> props = new ArrayList<String>();
        while (iter.hasNext()) {
            props.add(((Element) iter.next()).getText());
        }
        return props.iterator();
    }

    /**
     * 根据属性获取值
     *
     * @param name
     * @param attribute
     * @return
     */
    public String getAttribute(String name, String attribute) {
        if (name == null || attribute == null) {
            return null;
        }
        String[] propName = parsePropertyName(name);
        Element element = document.getRootElement();
        for (String child : propName) {
            element = element.element(child);
            if (element == null) {
                break;
            }
        }
        if (element != null) {
            return element.attributeValue(attribute);
        }
        return null;
    }

    /**
     * 设置属性值
     *
     * @param name
     * @param values
     */
    public void setProperties(String name, List<String> values) {
        String[] propName = parsePropertyName(name);
        Element element = document.getRootElement();
        for (int i = 0; i < propName.length - 1; i++) {
            if (element.element(propName[i]) == null) {
                element.addElement(propName[i]);
            }
            element = element.element(propName[i]);
        }
        String childName = propName[propName.length - 1];
        List<Element> toRemove = new ArrayList<Element>();
        Iterator<?> iter = element.elementIterator(childName);
        while (iter.hasNext()) {
            toRemove.add((Element) iter.next());
        }
        for (iter = toRemove.iterator(); iter.hasNext(); ) {
            element.remove((Element) iter.next());
        }
        for (String value : values) {
            Element childElement = element.addElement(childName);
            if (value.startsWith("<![CDATA[")) {
                Iterator<?> it = childElement.nodeIterator();
                while (it.hasNext()) {
                    Node node = (Node) it.next();
                    if (node instanceof CDATA) {
                        childElement.remove(node);
                        break;
                    }
                }
                childElement.addCDATA(value.substring(9, value.length() - 3));
            } else {
                childElement.setText(StringEscapeUtils.escapeXml(value));
            }
        }
    }

    /**
     * 根据父节点获取字节点名称数组
     *
     * @param parent
     * @return
     */
    public String[] getChildrenProperties(String parent) {
        String[] propName = parsePropertyName(parent);
        Element element = document.getRootElement();
        for (String aPropName : propName) {
            element = element.element(aPropName);
            if (element == null) {
                return new String[]{};
            }
        }
        List<?> children = element.elements();
        int childCount = children.size();
        String[] childrenNames = new String[childCount];
        for (int i = 0; i < childCount; i++) {
            childrenNames[i] = ((Element) children.get(i)).getName();
        }
        return childrenNames;
    }

    /**
     * 设置属性
     *
     * @param name
     * @param value
     */
    public synchronized void setProperty(String name, String value) {
        if (!StringEscapeUtils.escapeXml(name).equals(name)) {
            throw new IllegalArgumentException("Property name cannot contain XML entities.");
        }
        if (name == null) {
            return;
        }
        if (value == null) {
            value = "";
        }
        propertyCache.put(name, value);

        String[] propName = parsePropertyName(name);
        Element element = document.getRootElement();
        for (String aPropName : propName) {
            if (element.element(aPropName) == null) {
                element.addElement(aPropName);
            }
            element = element.element(aPropName);
        }
        if (value.startsWith("<![CDATA[")) {
            Iterator<?> it = element.nodeIterator();
            while (it.hasNext()) {
                Node node = (Node) it.next();
                if (node instanceof CDATA) {
                    element.remove(node);
                    break;
                }
            }
            element.addCDATA(value.substring(9, value.length() - 3));
        } else {
            element.setText(value);
        }
    }

    /**
     * 删除属性
     *
     * @param name
     */
    public synchronized void deleteProperty(String name) {
        propertyCache.remove(name);
        String[] propName = parsePropertyName(name);
        Element element = document.getRootElement();
        for (int i = 0; i < propName.length - 1; i++) {
            element = element.element(propName[i]);
            if (element == null) {
                return;
            }
        }
        element.remove(element.element(propName[propName.length - 1]));
    }

    /**
     * 创建document
     *
     * @param in
     * @throws IOException
     */
    private void buildDoc(Reader in) throws IOException {
        try {
            SAXReader xmlReader = new SAXReader();
            xmlReader.setEncoding("UTF-8");
            document = xmlReader.read(in);
        } catch (Exception e) {
            log.error("Error reading XML properties", e);
            throw new IOException(e.getMessage());
        } finally {
            if (in != null) {
                in.close();
            }
        }
    }

    /**
     * 获取节点名数组
     *
     * @param name
     * @return
     */
    private String[] parsePropertyName(String name) {
        List<String> propName = new ArrayList<String>(5);
        StringTokenizer tokenizer = new StringTokenizer(name, ".");
        while (tokenizer.hasMoreTokens()) {
            propName.add(tokenizer.nextToken());
        }
        return propName.toArray(new String[propName.size()]);
    }

    /**
     * 设置属性值
     *
     * @param propertyMap
     */
    public void setProperties(Map<String, String> propertyMap) {
        for (String propertyName : propertyMap.keySet()) {
            String propertyValue = propertyMap.get(propertyName);
            setProperty(propertyName, propertyValue);
        }
    }

    /**
     * 输入文件复制到输出文件
     *
     * @param inFile
     * @param outFile
     * @throws IOException
     */
    @SuppressWarnings("unused")
    private static void copy(File inFile, File outFile) throws IOException {
        FileInputStream fin = null;
        FileOutputStream fout = null;
        try {
            fin = new FileInputStream(inFile);
            fout = new FileOutputStream(outFile);
            copy(fin, fout);
        } finally {
            try {
                if (fin != null) fin.close();
            } catch (IOException e) {
                // do nothing
            }
            try {
                if (fout != null) fout.close();
            } catch (IOException e) {
                // do nothing
            }
        }
    }

    /**
     * 输入流复制到输出流
     *
     * @param in
     * @param out
     * @throws IOException
     */
    private static void copy(InputStream in, OutputStream out) throws IOException {
        synchronized (in) {
            synchronized (out) {
                byte[] buffer = new byte[256];
                while (true) {
                    int bytesRead = in.read(buffer);
                    if (bytesRead == -1) break;
                    out.write(buffer, 0, bytesRead);
                }
            }
        }
    }
}
