package edu.hw6.task3;

import java.nio.file.DirectoryStream.Filter;
import java.nio.file.Path;

@FunctionalInterface
public interface AbstractFilter extends Filter<Path> {
    default AbstractFilter and(AbstractFilter filter) {
        return (entry) -> this.accept(entry) && filter.accept(entry);
    }
}
