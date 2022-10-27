exec {
    commandLine = listOf("git", "config", "core.hooksPath", ".git-config/hooks")
    description = "Changing hookspath to project .githooks"
}
