package com.wepon.doccomplie;

import com.google.auto.service.AutoService;
import com.wepon.docannotation.GDoc;

import java.io.IOException;
import java.io.Writer;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import javax.annotation.processing.AbstractProcessor;
import javax.annotation.processing.Messager;
import javax.annotation.processing.ProcessingEnvironment;
import javax.annotation.processing.Processor;
import javax.annotation.processing.RoundEnvironment;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.Element;
import javax.lang.model.element.TypeElement;
import javax.tools.StandardLocation;

/**
 * Author: Wepon
 * Date: 2020/03/24 下午2:24
 * Description:
 */
@AutoService(Processor.class) //其中这个注解就是 auto-service 提供的SPI功能
public class DocProcessor extends AbstractProcessor {

    Writer docWriter;

    @Override
    public synchronized void init(ProcessingEnvironment processingEnvironment) {
        super.init(processingEnvironment);
        System.out.println("DocProcessor init");
    }

    @Override
    public Set<String> getSupportedAnnotationTypes() {
        //可处理的注解的集合
        HashSet<String> annotations = new HashSet<>();
        String canonicalName = GDoc.class.getCanonicalName();
        annotations.add(canonicalName);
        return annotations;
    }

    @Override
    public SourceVersion getSupportedSourceVersion() {
        return SourceVersion.latestSupported();
    }

    @Override
    public boolean process(Set<? extends TypeElement> annotations, RoundEnvironment env) {
        if (annotations == null || annotations.isEmpty()) {
            return false;
        }
        System.out.println("DocProcessor 开始");
        Messager messager = processingEnv.getMessager();
        Map<String, Entity> map = new HashMap<>();
        StringBuilder stringBuilder = new StringBuilder();
        for (Element e : env.getElementsAnnotatedWith(GDoc.class)) {
            GDoc annotation = e.getAnnotation(GDoc.class);
            Entity entity = new Entity();
            entity.name = annotation.name();
            entity.author = annotation.author();
            entity.time = annotation.time();
            map.put(e.getSimpleName().toString(), entity);

            stringBuilder.append(e.getSimpleName()).append("       ").append(entity.name).append("\n");
        }

        try {
            docWriter = processingEnv.getFiler().createResource(
                    StandardLocation.SOURCE_OUTPUT,
                    "",
                    "DescClassDoc.json"
            ).openWriter();

            //docWriter.append(JSON.toJSONString(map, SerializerFeature.PrettyFormat));
            String str = stringBuilder.toString();
            docWriter.append(str);
            System.out.println("DocProcessor write string:" + str);
            docWriter.flush();
            docWriter.close();
            System.out.println("DocProcessor docWriter close");
        } catch (IOException e) {
            //e.printStackTrace();
            //写入失败
            System.out.println("DocProcessor 写入失败：" + e.getMessage());
        }
        System.out.println("DocProcessor 结束");
        return true;
    }
}