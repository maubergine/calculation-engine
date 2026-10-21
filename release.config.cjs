// semantic-release config (.github/workflows/release.yml). Versions come from Conventional Commits: fix → patch,
// feat → minor, a breaking change → major. chore, ci, docs, test and refactor don't release.
//
// First release: tag the version already shipped (git tag v<x.y.z> on main) so numbering continues from there.
module.exports = {
  branches: ["main"],
  repositoryUrl: "https://github.com/maubergine/calculation-engine.git",
  tagFormat: "v${version}",
  plugins: [
    "@semantic-release/commit-analyzer",
    "@semantic-release/release-notes-generator",
    ["@semantic-release/changelog", { changelogFile: "CHANGELOG.md" }],
    // Keeps package.json's version in step. npmPublish stays false unless this is a published npm package.
    ["@semantic-release/npm", { npmPublish: false }],
    [
      "@semantic-release/exec",
      {
        // Writes the release version into both poms (the project's own <version> follows its artifactId).
        prepareCmd:
          "sed -i.bak -e '/<artifactId>calculation-engine<\\/artifactId>/{n;s|<version>[^<]*</version>|<version>${nextRelease.version}</version>|;}' pom.xml && " +
          "sed -i.bak -e '/<artifactId>calculation-engine-core<\\/artifactId>/{n;s|<version>[^<]*</version>|<version>${nextRelease.version}</version>|;}' core/pom.xml && " +
          "rm -f pom.xml.bak core/pom.xml.bak",
      },
    ],
    [
      "@semantic-release/git",
      {
        assets: ["package.json", "package-lock.json", "CHANGELOG.md", "pom.xml", "core/pom.xml"],
        message: "chore(release): ${nextRelease.version} [skip ci]",
      },
    ],
    ["@semantic-release/github", {}],
  ],
};
