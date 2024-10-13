package com.ryderbelserion.simpleflags;

import io.papermc.paper.plugin.loader.PluginClasspathBuilder;
import io.papermc.paper.plugin.loader.PluginLoader;
import io.papermc.paper.plugin.loader.library.impl.MavenLibraryResolver;
import org.eclipse.aether.artifact.DefaultArtifact;
import org.eclipse.aether.graph.Dependency;
import org.eclipse.aether.repository.RemoteRepository;
import org.jetbrains.annotations.NotNull;

public class LibraryLoader implements PluginLoader {

    @Override
    public void classloader(@NotNull PluginClasspathBuilder classpathBuilder) {
        MavenLibraryResolver resolver = new MavenLibraryResolver();

        resolver.addDependency(new Dependency(new DefaultArtifact("com.ryderbelserion.vital:paper:1.0.6"), null));

        resolver.addRepository(new RemoteRepository.Builder("crazycrew-libraries", "default", "https://repo.crazycrew.us/libraries").build());
        resolver.addRepository(new RemoteRepository.Builder("paper", "default", "https://repo.papermc.io/repository/maven-public").build());

        classpathBuilder.addLibrary(resolver);
    }
}