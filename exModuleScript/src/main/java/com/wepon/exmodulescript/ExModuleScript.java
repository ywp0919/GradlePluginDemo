package com.wepon.exmodulescript;

import org.gradle.api.Plugin;
import org.gradle.api.Project;

public class ExModuleScript implements Plugin<Project> {
    @Override
    public void apply(Project project) {
        System.out.println("exModuleScrip apply.....");

        project.task("exModuleScript").doLast(task -> {
            System.out.println("doLast exModuleScript");
        });
    }
}
