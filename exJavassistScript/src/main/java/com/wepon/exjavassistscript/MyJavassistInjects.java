package com.wepon.exjavassistscript;

import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.Collection;
import java.util.Objects;

import javassist.CannotCompileException;
import javassist.ClassPool;
import javassist.CtClass;
import javassist.CtConstructor;
import javassist.NotFoundException;

/**
 * Author: Wepon
 * Date: 2020/03/24 上午9:40
 * Description: javassist 注入器
 */
public class MyJavassistInjects {

    private static ClassPool pool = ClassPool.getDefault();

    public static void injectDir(String path) {
        try {
            pool.appendClassPath(path);
            System.out.println("Inject path:" + path);
            File dir = new File(path);
            if (dir.isDirectory()) {
                Collection<File> files = FileUtils.listFiles(dir, new String[]{"class"}, true);
                System.out.println("Inject files: " + Arrays.toString(files.toArray()));

                for (File file : files) {
                    String filePath = file.getAbsolutePath();
                    System.out.println("Inject filePath: " + filePath);
                    if (filePath.endsWith("MainActivity.class")) {
                        String clsName = "com.wepon.gradleplugindemo.MainActivity";
                        CtClass c = pool.getCtClass(clsName);
                        if (c.isFrozen()) {
                            c.defrost();
                        }
                        CtConstructor[] cts = c.getDeclaredConstructors();
                        String injectStr = "System.out.println(\"Hello world!\" ); ";
                        System.out.println("Inject injectStr");

                        if (cts == null || cts.length == 0) {
                            CtConstructor constructor = new CtConstructor(new CtClass[0], c);
                            constructor.insertBeforeBody(injectStr);
                            c.addConstructor(constructor);
                        } else {
                            cts[0].insertBeforeBody(injectStr);
                        }
                        System.out.println("Inject writeFile:" + filePath);
                        c.writeFile(path);
                        c.detach();
                        System.out.println("Inject 结束");
                    }
                }
            }
        } catch (NotFoundException | CannotCompileException | IOException e) {
            e.printStackTrace();
            System.out.println("Inject error:" + e.getMessage());
        }
    }
}