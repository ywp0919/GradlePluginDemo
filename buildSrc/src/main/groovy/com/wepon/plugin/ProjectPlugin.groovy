import org.gradle.api.Plugin
import org.gradle.api.Project

class ProjectPlugin implements Plugin<Project>{

    @Override
    void apply(Project project) {
        project.task("projectScript"){
            doLast {
                println("doLast projectScript")
            }
        }
    }
}