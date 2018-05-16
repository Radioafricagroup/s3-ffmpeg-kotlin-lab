# Building a Media Converter With AWS Lambda, s3, ffmpeg and Kotlin

 This is Step 1 - Initializing the project with a serverless template

## Install node.js

Only do this step if you already don't have it installed, I recommend nvm for most new users of node.js because it makes life with node.js a bit easier. Installation can also be done from the node.js website.

## Installing Serverless and Initializing the Template

Run the commands below to achieve this but not on this repository path, do it in another directory. This is because the branches of this repo represent what you should have at the end of each step.

```sh
# Performs a global install of serverless
npm install -g serverless
# Creates the template in the specified path
serverless create --template aws-kotlin-nodejs-gradle --path s3-ffmpeg-kotlin-lab
```

With that you have finished the first step and your working folder should look like the current branch of this repository

## Move to Step 2

```sh
git checkout Step-2-Handler-Customisation
```