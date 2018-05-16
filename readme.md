# Building a Media Converter With AWS Lambda, s3, ffmpeg and Kotlin

 This is Step 3 - Building ffmpeg binary

## Install Docker

Install docker from here [https://docs.docker.com/install/](https://docs.docker.com/install/)

## Build process

Opern up your terminal, change directories to your resources folder like so

```sh
cd /path/to/lab/src/main/resources
docker run --rm -it -v $(pwd):/buildir -w /buildir amazonlinux /bin/bash
curl -L http://bit.ly/ffmpeg-deps | bash
cp /usr/bin/ffmpeg /buildir/ffmpeg
cp /usr/bin/ffprobe /buildir/ffprobe
```

If the above succeeds thank the heavens and move on, if it takes too long or you face some errors, just take the binaries included in this repo's src/main/resources folder and place them in your src/main/resources folder

## Move to Step 4

```sh
git checkout Step-4-aws-credential-setup
```