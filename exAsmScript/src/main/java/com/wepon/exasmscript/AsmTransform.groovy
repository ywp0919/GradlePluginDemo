package com.wepon.exasmscript

import com.android.build.api.transform.Context
import com.android.build.api.transform.QualifiedContent
import com.android.build.api.transform.Transform
import com.android.build.api.transform.TransformException
import com.android.build.api.transform.TransformInput
import com.android.build.api.transform.TransformOutputProvider
import org.gradle.api.Project

class AsmTransform extends Transform {
    Project project

    AsmTransform(Project project) {
        this.project = project
    }

    @Override
    String getName() {
        return "MyTransform"
    }
    //设置输入类型，我们是针对class文件处理
    @Override
    Set<QualifiedContent.ContentType> getInputTypes() {
        return TransformManager.CONTENT_CLASS
    }
    //设置输入范围，我们选择整个项目
    @Override
    Set<? super QualifiedContent.Scope> getScopes() {
        return TransformManager.SCOPE_FULL_PROJECT
    }

    @Override
    boolean isIncremental() {
        return true
    }
    //重点就是该方法，我们需要将修改字节码的逻辑就从这里开始
    @Override
    void transform(Context context,
                   Collection<TransformInput> inputs,
                   Collection<TransformInput> referencedInputs,
                   TransformOutputProvider outputProvider,
                   boolean isIncremental)
            throws IOException, TransformException, InterruptedException {

            }
}