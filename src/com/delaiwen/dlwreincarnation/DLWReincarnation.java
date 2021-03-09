package com.delaiwen.dlwreincarnation;

import com.delaiwen.dlwreincarnation.Utils.FileUtil;
import org.bukkit.Bukkit;
import org.bukkit.Server;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.PluginDescriptionFile;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;

public class DLWReincarnation extends JavaPlugin {
    private static DLWReincarnation instance;
    // config 配置文件
    private static YamlConfiguration config;

    @Override
    public void onEnable() {
        // 保存实例
        instance = this;

        Server server = getServer();
        ConsoleCommandSender commandSender = server.getConsoleSender();
        PluginDescriptionFile descriptionFile = getDescription();

        // 设置插件文件夹
        if (!FileUtil.setDataFolder(getDataFolder())){
            commandSender.sendMessage("-----§cDLWReincarnation: 创建或设置插件文件夹（" + getDataFolder() + "）失败 ! ! !-----");
            return;
        }

        // 加载插件内部语言
        if(!InnerMessage.set(this)) {
            commandSender.sendMessage("-----§cDLWReincarnation: 加载语言文件失败 ! ! !-----");
            Bukkit.getPluginManager().disablePlugin(this);
            return;
        }

        // 插件启动
        //commandSender.sendMessage(InnerMessage.enable.replace("%pluginName%", descriptionFile.getName()));
        //commandSender.sendMessage("");
        commandSender.sendMessage("  _____       _       _                    ");
        commandSender.sendMessage(" |  __ \\     | |     (_)                   ");
        commandSender.sendMessage(" | |  | | ___| | __ _ ___      _____ _ __  ");
        commandSender.sendMessage(" | |  | |/ _ \\ |/ _` | \\ \\ /\\ / / _ \\ '_ \\ ");
        commandSender.sendMessage(" | |__| |  __/ | (_| | |\\ V  V /  __/ | | |");
        commandSender.sendMessage(" |_____/ \\___|_|\\__,_|_| \\_/\\_/ \\___|_| |_|");
        commandSender.sendMessage("");
        // 打印作者
        commandSender.sendMessage(InnerMessage.author.replace("%author%", descriptionFile.getAuthors().toString()));
        // 打印版本
        commandSender.sendMessage(InnerMessage.version.replace("%version%", descriptionFile.getVersion()));
        // 获取服务端核心
        String serverJar = server.getName();
        commandSender.sendMessage(InnerMessage.server_jar.replace("%server%", serverJar));
        // 获取MC版本
        String version = server.getVersion();
        version = version.substring(version.indexOf("MC") + 3, version.length() - 1).trim();
        // String subversion = version.substring(0, version.lastIndexOf('.'));
        commandSender.sendMessage(InnerMessage.server_version.replace("%version%", version));

        // 加载插件
        loadPlugin();
        // 插件启动完成
        commandSender.sendMessage(InnerMessage.enabled.replace("%pluginName%", descriptionFile.getName()));
    }

    @Override
    public void onDisable() {
        ConsoleCommandSender commandSender = getServer().getConsoleSender();
        PluginDescriptionFile descriptionFile = getDescription();
        commandSender.sendMessage(InnerMessage.disable.replace("%pluginName", descriptionFile.getName()));
        unloadPlugin();
        commandSender.sendMessage(InnerMessage.disabled.replace("%pluginName", descriptionFile.getName()));
    }

    /**
     * 加载插件
     */
    private void loadPlugin(){
        // 读取config配置文件
        config = FileUtil.getConfig("config.yml", "", "");

        // 语言文件夹
        File f = FileUtil.getFile("Message");
        if(!f.exists()){
            if (f.mkdir())
                Bukkit.getConsoleSender().sendMessage(InnerMessage.folder_create_succeeded.replace("%folder%", "Message"));
            else
                Bukkit.getConsoleSender().sendMessage(InnerMessage.folder_create_failed.replace("%folder%", "Message"));
        }
        YamlConfiguration lang = FileUtil.getConfig("message.yml", "Message", "message");
    }

    /**
     * 卸载插件
     */
    private void unloadPlugin(){

    }

    public static DLWReincarnation getInstance(){
        return instance;
    }
}
