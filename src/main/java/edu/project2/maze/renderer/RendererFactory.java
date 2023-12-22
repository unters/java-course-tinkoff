package edu.project2.maze.renderer;

public class RendererFactory {
    public static Renderer getRenderer() {
        return getDefaultRenderer();
    }

    @SuppressWarnings("SwitchStatementWithTooFewBranches")
    public static Renderer getRenderer(String rendererName) {
        return switch (rendererName) {
            case "ansi" -> getCliAnsiRenderer();
            default -> throw new IllegalArgumentException("invalid renderer name");
        };
    }

    private static Renderer getDefaultRenderer() {
        return getCliAnsiRenderer();
    }

    private static Renderer getCliAnsiRenderer() {
        return CliAnsiRenderer.getInstance();
    }

    private RendererFactory() {
    }
}
