package com.delaiwen.dlwreincarnation;

import com.delaiwen.dlwreincarnation.Utils.FileUtil;
import org.bukkit.configuration.file.YamlConfiguration;
import java.io.UnsupportedEncodingException;

public class InnerMessage {
    public static String author;
    public static String disable;
    public static String disabled;
    public static String enable;
    public static String enabled;
    public static String file_create;
    public static String file_error;
    public static String file_read;
    public static String folder_create_succeeded;
    public static String folder_create_failed;
    public static String server_jar;
    public static String server_version;
    public static String version;

    public static boolean set(DLWReincarnation rct){
        try {
            YamlConfiguration config;
            if (FileUtil.existFile("Message/message.yml"))
                config = FileUtil.getYaml(FileUtil.getFile("message/message.yml"));
            else
                config = FileUtil.getYaml(FileUtil.getInputStreamReader("message/message.yml", "UTF-8"));

            author = config.getString("author","§6  作者: %author%");
            disable = config.getString("disable","§b-----[%pluginName%]: 插件正在卸载-----");
            disabled = config.getString("disabled","§b-----[%pluginName%]: 插件卸载完毕-----");
            enable = config.getString("enable", "§b-----[%pluginName%]: 插件启动-----");
            enabled = config.getString("enabled","§b-----[%pluginName%]: 插件启动成功-----");
            file_create = config.getString("file-create", "§a-----创建文件: %file%");
            file_error = config.getString("file-error", "§c-----文件: %file% 创建失败");
            file_read = config.getString("file-read", "§6  读取文件: %file%");
            folder_create_failed = config.getString("folder-create-failed","§a-----创建%folder%文件夹失败");
            folder_create_succeeded = config.getString("folder-create-succeeded","§a-----创建%folder%文件夹成功");
            server_jar = config.getString("server-jar","§6  服务端核心: %server%");
            server_version = config.getString("server-version","§6-----服务器版本: %version%");
            version = config.getString("version", "§6  版本: %version%");
            return true;
        } catch (UnsupportedEncodingException e) {
            return false;
        }
    }
}
