# HavenBags Preview

A client-side Fabric mod that lets you see the contents of [HavenBags](https://www.spigotmc.org/resources/havenbags.110637/) bags directly in the inventory tooltip — no need to open them.

Supports Minecraft 1.17 through 1.21.11.

## Download

Get it from [Modrinth](https://modrinth.com/mod/havenbagspreview).

Requires [Fabric API](https://modrinth.com/mod/fabric-api).

## Building

You'll need JDK 21 installed.

```
./gradlew buildAll
```

This builds the mod for every supported Minecraft version. The JARs will be in each subproject's `build/libs/` directory.

To build for a specific version only:

```
./gradlew :fabric-1.21.11:build
```

## Project Structure

The project is split into a multi-version Gradle build:

- `common/` — shared logic (bag data parsing, constants)
- `fabric-1.17/` through `fabric-1.21.11/` — version-specific implementations (mixins, tooltip rendering)
- `gradle/` — shared Gradle scripts applied by all subprojects

## Contributing

1. Fork and clone the repo
2. Make your changes
3. Test with `./gradlew :fabric-<version>:runClient`
4. Submit a pull request

If your change affects rendering or mixins, please test on at least two different Minecraft versions since the APIs vary across versions.

## License

[MIT](LICENSE) — originally by ZtereoHYPE and masecla22.
