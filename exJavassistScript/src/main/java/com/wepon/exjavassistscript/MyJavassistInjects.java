package com.wepon.exjavassistscript;

import java.io.File;
import java.io.IOException;
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
            File dir = new File(path);
            if (dir.isDirectory()) {
                for (File file : Objects.requireNonNull(dir.listFiles())) {
                    String filePath = file.getAbsolutePath();
                    if (filePath.endsWith("MainActivity.class")) {
                        String clsName = "com.wepon.gradleplugindemo.MainActivity";
                        CtClass c = pool.getCtClass(clsName);
                        if (c.isFrozen()) {
                            c.defrost();
                        }
                        CtConstructor[] cts = c.getDeclaredConstructors();
                        String injectStr = "System.out.println(\"Hello world!\" ); ";
                        if (cts == null || cts.length == 0) {
                            CtConstructor constructor = new CtConstructor(new CtClass[0], c);
                            constructor.insertBeforeBody(injectStr);
                            c.addConstructor(constructor);
                        } else {
                            cts[0].insertBeforeBody(injectStr);
                        }
                        c.writeFile(filePath);
                        System.out.println("writeFile:" + filePath);
                        c.detach();
                    }
                }
            }
        } catch (NotFoundException | CannotCompileException | IOException e) {
            e.printStackTrace();
        }
    }
}