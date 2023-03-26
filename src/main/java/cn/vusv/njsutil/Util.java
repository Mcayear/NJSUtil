package cn.vusv.njsutil;

import cn.nukkit.utils.Config;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.reflect.TypeToken;
import org.yaml.snakeyaml.DumperOptions;
import org.yaml.snakeyaml.Yaml;

import java.util.LinkedHashMap;
public class Util {
    //json与yaml互转
    /**
     * JSON字符串转YAML字符串
     * @param json 要转换的json字符串
     */
    final public String JSONtoYAML(String json){
        json = formatJSON(json);
        Config config = new Config(Config.YAML);
        config.setAll((LinkedHashMap)new Gson().fromJson(json, (new TypeToken<LinkedHashMap<String, Object>>() {}).getType()));
        DumperOptions dumperOptions = new DumperOptions();
        dumperOptions.setDefaultFlowStyle(DumperOptions.FlowStyle.BLOCK);
        Yaml yaml = new Yaml(dumperOptions);
        return yaml.dump(config.getRootSection());
    }
    /**
     * YAML字符串转JSON字符串
     * @param yaml 要转换的YAML字符串
     */
    final public String YAMLtoJSON(String yaml){
        Config config = new Config(Config.JSON);
        DumperOptions dumperOptions = new DumperOptions();
        dumperOptions.setDefaultFlowStyle(DumperOptions.FlowStyle.BLOCK);
        Yaml yamlObj = new Yaml(dumperOptions);
        config.setAll(yamlObj.loadAs(yaml, LinkedHashMap.class));
        return new GsonBuilder().setPrettyPrinting().create().toJson(config.getRootSection());
    }
    /**
     * 格式化JSON字符串（重排版）
     * @param json 要格式化的json字符串
     */
    final public String formatJSON(String json) {
        JsonParser jsonParser = new JsonParser();
        JsonObject jsonObject = jsonParser.parse(json).getAsJsonObject();
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        return gson.toJson(jsonObject);
    }
}