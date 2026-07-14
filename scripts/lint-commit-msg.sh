#!/usr/bin/env sh
#
# Validate a commit message against the Conventional Commits format.
# Single source of truth shared by the .githooks/commit-msg hook and CI.
#
# Usage:
#   scripts/lint-commit-msg.sh <path-to-message-file>
#   <something> | scripts/lint-commit-msg.sh -      # read message from stdin
#
# Exit status is 0 when the message is valid, 1 otherwise.

set -eu

# Allowed commit types (Conventional Commits + this repo's "security" type).
TYPES='feat|fix|docs|style|refactor|perf|test|build|ci|chore|revert|security'
MAX_SUBJECT_LEN=100

# --- read the message -------------------------------------------------------
if [ "$#" -lt 1 ]; then
  echo "lint-commit-msg: missing argument (message file or '-')" >&2
  exit 2
fi

if [ "$1" = "-" ]; then
  msg=$(cat)
else
  msg=$(cat "$1")
fi

# First non-empty, non-comment line is the subject.
subject=$(printf '%s\n' "$msg" | grep -v '^#' | sed '/^[[:space:]]*$/d' | head -n1)

# --- skip messages that git generates or that are conventionally exempt -----
case "$subject" in
  "Merge "*|"Revert "*|"fixup! "*|"squash! "*|"amend! "*)
    exit 0
    ;;
esac

fail() {
  echo "✖ Invalid commit message:" >&2
  echo "    $subject" >&2
  echo "" >&2
  echo "  $1" >&2
  echo "" >&2
  echo "  Expected: <type>(optional-scope): <description>" >&2
  echo "  Types:    ${TYPES}" >&2
  echo "  Examples: feat(core): add round timer" >&2
  echo "            fix: prevent NPE on reload" >&2
  echo "            security!: rotate leaked token" >&2
  exit 1
}

[ -n "$subject" ] || fail "commit message is empty"

# <type>(scope)?!?: description
if ! printf '%s' "$subject" | grep -Eq "^(${TYPES})(\([a-z0-9._/-]+\))?!?: .+"; then
  fail "subject does not match the Conventional Commits format"
fi

len=$(printf '%s' "$subject" | wc -c | tr -d ' ')
if [ "$len" -gt "$MAX_SUBJECT_LEN" ]; then
  fail "subject is ${len} chars; keep it under ${MAX_SUBJECT_LEN}"
fi

exit 0
