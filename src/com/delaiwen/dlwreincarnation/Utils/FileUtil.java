package com.delaiwen.dlwreincarnation.Utils;

import com.delaiwen.dlwreincarnation.DLWReincarnation;
import com.delaiwen.dlwreincarnation.InnerMessage;
import org.bukkit.Bukkit;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.*;
import java.util.Collections;
import java.util.List;

/**
 * 文件帮助类
 */
public class FileUtil {
    /**
     * 插件文件夹
     */
    private static File dataFolder;

    /**
     * 默认编码
     */
    private static String encoding = "UTF-8";

    /**
     * 1.9以下默认编码为UTF-8的服务端核心
     */
    private static final List<String> UTF8_SERVER = Collections.singletonList("Uranium");

    /**
     * 判断文件是否存在
     * @param fileName 文件名
     * @return 存在返回true，否则返回false
     */
    public static boolean existFile(String fileName){
        return new File(dataFolder, fileName).exists();
    }

    /**
     * 获取文件对象
     * @param fileName 文件名
     * @return 返回File对象
     */
    public static File getFile(String fileName){
        return new File(dataFolder, fileName);
    }

    /**
     * 获取jar内文件
     * @param fileName 文件名
     * @return InputStream对象
     */
    public static InputStream getInputStream(String fileName){
        return DLWReincarnation.getInstance().getResource(fileName);
    }

    /**
     * 获取jar内文件的Reader，使用默认编码
     * @param fileName 文件名
     * @return 返回InputStreamReader对象
     * @throws UnsupportedEncodingException 编码格式不支持
     */
    public static InputStreamReader getInputStreamReader(String fileName) throws UnsupportedEncodingException {
        InputStream inputStream = DLWReincarnation.getInstance().getResource(fileName);
        if (inputStream != null)
            return new InputStreamReader(inputStream, encoding);
        else
            return null;
    }

    /**
     * 获取jar内文件Reader并指定编码
     * @param fileName 文件名
     * @param charset 编码哥特式
     * @return 返回InputStreamReader对象
     * @throws UnsupportedEncodingException 编码格式不支持
     */
    public static InputStreamReader getInputStreamReader(String fileName, String charset) throws UnsupportedEncodingException{
        InputStream inputStream = DLWReincarnation.getInstance().getResource(fileName);
        if (inputStream != null)
            return new InputStreamReader(inputStream, charset);
        else
            return null;
    }

    /**
     * 从File中读取一个yml文件
     * @param file 文件对象
     * @return 返回YamlConfiguration对象
     */
    public static YamlConfiguration getYaml(File file){
        return YamlConfiguration.loadConfiguration(file);
    }

    /**
     * 从Reader中读取一个yml
     * @param reader Reader对象
     * @return 返回YamlConfiguration对象
     */
    public static YamlConfiguration getYaml(Reader reader){
        return YamlConfiguration.loadConfiguration(reader);
    }

    /**
     * 获取配置文件（不存在则自动创建）
     * @param fileName 文件名
     * @param dir 插件文件夹下的目录
     * @param jar jar包内的目录
     * @return YamlConfiguration对象
     */
    public static YamlConfiguration getConfig(String fileName, String dir, String jar){
        File file = getFile(dir.equals("") ? fileName : (dir+"/"+fileName));
        // 如果文件不存在的创建
        if (!file.exists()){
            try {
                OutputStreamWriter osw;
                BufferedWriter bw;
                PrintWriter pw;
                try (InputStreamReader isr = getInputStreamReader(jar.equals("") ? fileName : (jar + "/" + fileName));
                    BufferedReader br = isr != null ? new BufferedReader(isr) : null){
                    if (br == null) return null;
                    osw = new OutputStreamWriter(new FileOutputStream(file), encoding);
                    bw = new BufferedWriter(osw);
                    pw = new PrintWriter(bw);
                    String temp;
                    while ((temp = br.readLine()) != null){
                        pw.println(temp);
                    }
                }
                bw.close();
                osw.close();
                pw.close();
                Bukkit.getConsoleSender().sendMessage(InnerMessage.file_create.replace("%file%", fileName));
            } catch (IOException e){
                Bukkit.getConsoleSender().sendMessage(InnerMessage.file_error.replace("%file%", fileName) + e.getMessage());
                return null;
            }
        }
        Bukkit.getConsoleSender().sendMessage(InnerMessage.file_read.replace("%file%", fileName));
        return getYaml(file);
    }

    /**
     * 设置插件文件夹
     * @param file 文件
     */
    public static boolean setDataFolder(File file){
        if (!file.isFile()){
            dataFolder = file;
            if (!dataFolder.exists())
                return dataFolder.mkdir();
            else
                return true;
        }else
            return false;
    }

    /**
     * 设置默认编码
     * @param server 服务端核心
     */
    public static void setEncoding(String server){
        if (!UTF8_SERVER.contains(server))
            encoding = System.getProperty("file.encoding");
    }
}
