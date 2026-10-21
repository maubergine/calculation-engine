// GitHub adds " (#123)" to a squash-merged pull request's title. Titles may be 100 characters before that suffix, so
// config-conventional's header-max-length (which counts the suffix) is replaced by a rule that ignores it.
const MAX_HEADER = 100;

module.exports = {
  extends: ["@commitlint/config-conventional"],
  plugins: [{
    rules: {
      "header-max-length-before-pr-number": ({ header }, when, max) => {
        const title = (header || "").replace(/ \(#\d+\)$/, "");
        return [title.length <= max, `header must not be longer than ${max} characters, not counting the " (#123)" GitHub adds on merge`];
      }
    }
  }],
  rules: {
    "header-max-length": [0],
    "header-max-length-before-pr-number": [2, "always", MAX_HEADER]
  }
};
