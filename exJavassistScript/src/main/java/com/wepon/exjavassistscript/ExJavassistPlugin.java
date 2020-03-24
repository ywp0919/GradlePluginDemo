package com.wepon.exjavassistscript;

import com.android.build.gradle.AppExtension;

import org.gradle.api.Plugin;
import org.gradle.api.Project;

public class ExJavassistPlugin implements Plugin<Project> {
    @Override
    public void apply(Project project) {
        System.out.println("ExJavassistPlugin apply.");
        AppExtension android = project.getExtensions().getByType(AppExtension.class);
        JavassistTransform myTransform = new JavassistTransform();
        android.registerTransform(myTransform);
    }
}
