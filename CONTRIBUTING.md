# Contributing

Calculation Engine is a Java command-line tool that runs UK tax calculations from YAML scenarios.

Bug reports and feature requests are welcome as [issues](https://github.com/maubergine/calculation-engine/issues). Pull requests are
welcome too; CI on a pull request from a fork may wait for the maintainer to approve it.

## Commits

Use [Conventional Commits](https://www.conventionalcommits.org/): `fix:`, `feat:`, `chore:`, `docs:` and so on. They
decide the next version: `fix` releases a patch, `feat` a minor version, and a breaking change a major one. CI checks
every commit (`commitlint.yml`). To check them locally before you push:

```sh
npm install   # installs commitlint and turns on .githooks/commit-msg
```

Commits to `main` must be signed.

## Tests

```sh
./mvnw -B verify          # JVM build and tests
./mvnw -Pnative -B package   # native build with GraalVM, as CI does
```

The native build needs GraalVM 25 with `native-image` installed.
