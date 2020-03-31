package com.wepon.exjavassistscript;

import com.android.build.api.transform.Context;
import com.android.build.api.transform.DirectoryInput;
import com.android.build.api.transform.Format;
import com.android.build.api.transform.JarInput;
import com.android.build.api.transform.QualifiedContent;
import com.android.build.api.transform.Transform;
import com.android.build.api.transform.TransformException;
import com.android.build.api.transform.TransformInput;
import com.android.build.api.transform.TransformInvocation;
import com.android.build.api.transform.TransformOutputProvider;
import com.android.build.gradle.internal.pipeline.TransformManager;
import com.android.utils.FileUtils;

import org.apache.commons.codec.digest.DigestUtils;
import org.apache.tools.ant.util.IdentityStack;

import java.io.File;
import java.io.IOException;
import java.util.Collection;
import java.util.Set;

/**
 * Author: Wepon
 * Date: 2020/03/24 上午9:58
 * Description:
 */
public class JavassistTransform extends Transform {

    @Override
    public String getName() {
        return "MyTransform";
    }

    @Override
    public Set<QualifiedContent.ContentType> getInputTypes() {
        return TransformManager.CONTENT_CLASS;
    }

    @Override
    public Set<? super QualifiedContent.Scope> getScopes() {
        return TransformManager.SCOPE_FULL_PROJECT;
    }

    @Override
    public boolean isIncremental() {
        return false;
    }


    @Override
    public void transform(Context context,
                          Collection<TransformInput> inputs,
                          Collection<TransformInput> referencedInputs,
                          TransformOutputProvider outputProvider,
                          boolean isIncremental) throws IOException, TransformException, InterruptedException {
//        super.transform(context, inputs, referencedInputs, outputProvider, isIncremental);
        System.out.println("javassist transform 开始");

        for (TransformInput input : inputs) {

            for (DirectoryInput directoryInput : input.getDirectoryInputs()) {
                System.out.println("javassist transform injectDir");
                MyJavassistInjects.injectDir(directoryInput.getFile().getAbsolutePath());
                File dest = outputProvider.getContentLocation(directoryInput.getName(),
                        directoryInput.getContentTypes(),
                        directoryInput.getScopes(),
                        Format.DIRECTORY);
                System.out.println("directory output dest: " + dest.getAbsolutePath());
                org.apache.commons.io.FileUtils.copyDirectory(directoryInput.getFile(), dest);
            }

            for (JarInput jarInput : input.getJarInputs()) {
                String jarName = jarInput.getName();
                System.out.println("jar:" + jarName);
                String md5Name = DigestUtils.md5Hex(jarInput.getFile().getAbsolutePath());
                if (jarName.endsWith(".jar")) {
                    jarName = jarName.substring(0, jarName.length() - 4);
                }
                File dest = outputProvider.getContentLocation(jarName + md5Name,
                        jarInput.getContentTypes(),
                        jarInput.getScopes(),
                        Format.JAR);
                System.out.println("jar output dest:" + dest.getAbsolutePath());
                org.apache.commons.io.FileUtils.copyFile(jarInput.getFile(), dest);
            }
        }

        System.out.println("javassist transform 结束");

    }
}
