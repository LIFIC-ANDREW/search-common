package io.lific.search.common.utils.config;

import com.typesafe.config.Config;
import com.typesafe.config.ConfigFactory;
import com.typesafe.config.ConfigParseOptions;
import com.typesafe.config.ConfigSyntax;
import io.lific.search.common.exceptions.ConfigException;
import io.lific.search.common.utils.file.FileUtils;

import java.io.BufferedReader;
import java.io.File;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.reflect.InvocationTargetException;
import java.nio.file.Paths;


/*
    load config of typesafe style and transform to Configuration class
    utility class
 */
public class ConfigLoader {

    private ConfigLoader() {
    }

    public static Config load(File confFile) {
        return ConfigFactory.parseFile(confFile).resolve();
    }

    public static Config load(String confFilePath) {
        Config config = null;
        String confDir = FileUtils.getDirectoryPath(confFilePath);
        File confFile = Paths.get(confFilePath).toAbsolutePath().toFile();

        if (confFile.exists()) {
            config = load(confFile);
        } else if (confDir == null) {
            String baseNameOfConfigFile = confFilePath;
            int idx = baseNameOfConfigFile.lastIndexOf('.');
            if (idx != -1) {
                baseNameOfConfigFile = baseNameOfConfigFile.substring(0, idx);
            }
            config = ConfigFactory.load(baseNameOfConfigFile);
        } else {
            InputStream in = Thread.currentThread().getContextClassLoader().getResourceAsStream(confFilePath);
            if (in != null) {
                config = parse(FileUtils.getFileContent(new BufferedReader(new InputStreamReader(in))));
            }
        }

        return config;
    }

    public static Config parse(String config) {
        return ConfigFactory.parseString(config).resolve();
    }

    public static Config parseJson(String json) {
        return ConfigFactory.parseString(json, ConfigParseOptions.defaults().setSyntax(ConfigSyntax.JSON)).resolve();
    }

    public static Config parse(String config, ConfigSyntax syntax) {
        return ConfigFactory.parseString(config, ConfigParseOptions.defaults().setSyntax(syntax)).resolve();
    }

    public static Config merge(Config config1, Config config2) {
        return config1.withFallback(config2).resolve();
    }

    public static Configuration build(Class<? extends Configuration> configurationClass, String configFilePath) {
        try {
            return configurationClass.getConstructor(Config.class).newInstance(load(configFilePath));
        } catch (IllegalAccessException | InstantiationException | NoSuchMethodException | InvocationTargetException e) {
            throw new ConfigException(e);
        }
    }

    public static Configuration build(Class<? extends Configuration> configurationClass, File configFile) {
        try {
            return configurationClass.getConstructor(Config.class).newInstance(load(configFile));
        } catch (IllegalAccessException | InstantiationException | NoSuchMethodException | InvocationTargetException e) {
            throw new ConfigException(e);
        }
    }

    public static Configuration build(Class<? extends Configuration> configurationClass, Config config) {
        try {
            return configurationClass.getConstructor(Config.class).newInstance(config);
        } catch (IllegalAccessException | InstantiationException | NoSuchMethodException | InvocationTargetException e) {
            throw new ConfigException(e);
        }
    }

    public static Configuration build(Class<? extends Configuration> configurationClass, Object... args) {
        try {
            return configurationClass.getConstructor(args.getClass()).newInstance(args);
        } catch (IllegalAccessException | InstantiationException | NoSuchMethodException | InvocationTargetException e) {
            throw new ConfigException(e);
        }
    }

}
