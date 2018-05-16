# Building a Media Converter With AWS Lambda, s3, ffmpeg and Kotlin

 This is Step 2 - Customising the handler

## Deleting uneeded files

- Delete ApiGatewayResponse.kt, HelloResponse.kt and  Response.kt
- Replace your Handler.kt with the one in this repository
- Replace your Serveless.yml with the one in this repository
- Copy the utils folder from this repo and place it next to the Handler.kt
- Open S3Operations.kt and fill in your aws credentials, input and output buckets
- Open Serverless.yml and fill in your input bucket

Note: The credentials used must have read/write access to both the buckets

## Explanation

- All the source code is heavily commented to explain why anything is done the way it is
- For the Serverless.yml we have defined a deployment bucket, this bucket will be where we store our source code. It is recommended to define this because serverless will otherwise assign a random bucket for this purpose.
- The deployment bucket is used by aws to provision your container from a cold start. A cold start is the first call of a function after deployment or a function call after a long period of inactivity
- There are several strategies to warm up containers to reduce the impact of speed on cold start but it highly depends on your use case for the function

With that you have finished the second step and your working folder should look like the current branch of this repository

## Move to Step 3

```sh
git checkout Step-3-ffmpeg-build
```