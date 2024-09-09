package libparer;

import org.gradle.tooling.GradleConnector;
import org.gradle.tooling.ModelBuilder;
import org.gradle.tooling.ProjectConnection;
import org.gradle.tooling.model.DomainObjectSet;
import org.gradle.tooling.model.eclipse.EclipseProject;
import org.gradle.tooling.model.eclipse.EclipseExternalDependency;

import java.io.File;
import java.util.List;

public class LibParerMain {

  public static void main(String[] args) {
    // プロジェクトのルートディレクトリ（build.gradleのあるディレクトリ）
    File projectDir = new File("/Users/higo/git/FinerGit");

    // GradleConnectorを使ってGradleプロジェクトに接続
    ProjectConnection connection = GradleConnector.newConnector()
        .forProjectDirectory(projectDir)
        .connect();

    try {
      // EclipseProjectモデルを取得
      final ModelBuilder<EclipseProject> modelBuilder = connection.model(EclipseProject.class);
      final EclipseProject project = modelBuilder.get();

      // 外部依存関係を取得
      final DomainObjectSet<? extends EclipseExternalDependency> classpath = project.getClasspath();
      final List<? extends EclipseExternalDependency> all = classpath.getAll();
      for (final EclipseExternalDependency dep : all) {
        final File libFile = dep.getFile();
        System.out.println(libFile.getName());
      }

    } finally {
      // 接続を閉じる
      connection.close();
    }
  }
}