import com.android.build.gradle.AppExtension
import com.android.build.gradle.AppPlugin
import com.android.tools.build.jetifier.core.config.Config
import org.gradle.api.GradleException
import org.gradle.api.Plugin
import org.gradle.api.Project

class ProjectPlugin implements Plugin<Project> {

    @Override
    void apply(Project project) {
        println("ProjectPlugin apply.")

        project.task("projectScript") {
            doLast {
                println("doLast projectScript")
            }
        }


        if (!project.plugins.hasPlugin(AppPlugin.class)) {
            throw GradleException("this plugin is not application")
        }

        // 获取build.gradle中的 android 闭包
        AppExtension android = project.extensions.getByType(AppExtension.class)
        project.extensions.create("config",ConfigExten.class)
        android.applicationVariants.all {

        }





















    }
}