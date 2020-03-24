package com.wepon.exasmscript;

import org.gradle.api.Plugin;
import org.gradle.api.Project;

public class ExAsmScript implements Plugin<Project> {
    @Override
    public void apply(Project project) {
        System.out.println("exAsmScrip apply.....");

        project.task("exModuleScript").doLast(task -> {
            System.out.println("doLast exModuleScript");
        });
    }
}
