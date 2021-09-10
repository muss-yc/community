package com.mousse.dto;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @author mousse
 * @data 2021/9/10
 */
public class TagCache {

    public static List<TagDTO> get(){
        List<TagDTO> tagDTOS = new ArrayList<>();

        TagDTO language = new TagDTO();
        language.setCategoryName("开发语言");
        language.setTags(Arrays.asList("Java","Python","C","C++","JavaScript","PHP","Go"));
        tagDTOS.add(language);

        TagDTO framework = new TagDTO();
        framework.setCategoryName("平台框架");
        framework.setTags(Arrays.asList("Spring","Mybatis","SpringMVC","SpringBoot","SpringCloud","Vue","React"));
        tagDTOS.add(framework);

        TagDTO server = new TagDTO();
        server.setCategoryName("服务器");
        server.setTags(Arrays.asList("Linux","Nginx","Docker","Apache","Ubuntu","Centos","Tomcat","Unix","缓存","负载均衡"));
        tagDTOS.add(server);

        TagDTO db = new TagDTO();
        db.setCategoryName("数据库");
        db.setTags(Arrays.asList("Mysql","SqlServer","Oracle","Redis","Mongodb","Sql"));
        tagDTOS.add(db);

        TagDTO tool = new TagDTO();
        tool.setCategoryName("开发工具");
        tool.setTags(Arrays.asList("Idea","Eclipse","Git","GitHub","Vim","Maven","Svn"));
        tagDTOS.add(tool);
        return tagDTOS;
    }

    public static String[] filterIllegal(String tag) {
        List<String> illegalStr = new ArrayList<>();
        String[] split = tag.split(",");
        List<String> tagDTOS = new ArrayList<>();
        for (TagDTO tagDTO : get()) {
            List<String> tags = tagDTO.getTags();
            tagDTOS.addAll(tags);
        }
        for (String s : split) {
            // 存在非法字符 便添加到illegalStr中
            if (!tagDTOS.contains(s)) illegalStr.add(s);
        }
        return illegalStr.toArray(new String[0]);
    }
}
