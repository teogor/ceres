#!/bin/sh

set -e
set -x

# Validate required input
if [ -z "$SOURCE_FILE" ]; then
  echo "Error: Source file must be specified."
  exit 1
fi

# Set default values for optional variables
if [ -z "$GIT_SERVER" ]; then
  GIT_SERVER="github.com"
fi

if [ -z "$DESTINATION_BRANCH" ]; then
  DESTINATION_BRANCH="main"
fi

# Clone the destination Git repository
OUTPUT_BRANCH="$DESTINATION_BRANCH"

CLONE_DIR=$(mktemp -d)
echo "Cloning destination Git repository: $DESTINATION_REPO"

git config --global user.email "$USER_EMAIL"
git config --global user.name "$USER_NAME"

git clone --single-branch --branch $DESTINATION_BRANCH "https://x-access-token:$API_TOKEN_GITHUB@$GIT_SERVER/$DESTINATION_REPO.git" "$CLONE_DIR"

# Determine the destination file path
DEST_COPY="$CLONE_DIR/$DESTINATION_FOLDER/"

if [ ! -z "$RENAME" ]; then
  echo "Renaming file to: ${RENAME}"
  DEST_COPY="$CLONE_DIR/$DESTINATION_FOLDER/$RENAME"
fi

# Delete the previous folder if it exists
if [ -d "$DEST_COPY" ]; then
  echo "Deleting existing folder: $DEST_COPY"
  rm -rf "$DEST_COPY"
fi

# Copy the source file to the destination repository
echo "Copying contents to Git repo: $SOURCE_FILE"

# Move the source file instead of copying it to avoid creating duplicates
cp -r "$SOURCE_FILE" "$DEST_COPY"

# Check out the specified branch or create a new one
cd "$CLONE_DIR"

if [ ! -z "$DESTINATION_BRANCH_CREATE" ]; then
  echo "Creating new branch: $DESTINATION_BRANCH_CREATE"
  git checkout -b "$DESTINATION_BRANCH_CREATE"
  OUTPUT_BRANCH="$DESTINATION_BRANCH_CREATE"
fi

if [ -z "$COMMIT_MESSAGE" ]; then
  COMMIT_MESSAGE="Automated updates based on https://$GIT_SERVER/${GITHUB_REPOSITORY}/commit/${GITHUB_SHA}"
fi

# Add the copied file to the staging area
echo "Adding git commit"
git add .

# Commit changes if there are any
if git status | grep -q "Changes to be committed"; then
  echo "Committing changes with message: $COMMIT_MESSAGE"
  git commit --message "$COMMIT_MESSAGE"

  echo "Pushing git commit to branch: $OUTPUT_BRANCH"
  git push -u origin HEAD:"$OUTPUT_BRANCH"
else
  echo "No changes detected, skipping commit and push"
fi
