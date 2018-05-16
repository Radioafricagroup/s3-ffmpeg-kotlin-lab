# Building a Media Converter With AWS Lambda, s3, ffmpeg and Kotlin

 This is Step 4 - Setting up your credentials and deploying

- Follow the instructions here [https://docs.aws.amazon.com/cli/latest/userguide/installing.html](https://docs.aws.amazon.com/cli/latest/userguide/installing.html)

- Create your output bucket if you have not already, no need to make the input bucket or the deployment bucket

- Export your AWS credentials before deploying like so

```sh
export AWS_ACCESS_KEY_ID=********************
export AWS_SECRET_ACCESS_KEY=*************************************
```

- Unless you plan on using a CI engine or automated deploys, just use an administrative account for simplicity, but the policy that worked for me for this specific deploy is this:

```json
{
    "Version": "",
    "Statement": [
        {
            "Sid": "MyConverter",
            "Effect": "Allow",
            "Action": [
                "iam:GetPolicyVersion",
                "logs:*",
                "iam:CreateRole",
                "sns:Unsubscribe",
                "iam:PutRolePolicy",
                "xray:PutTraceSegments",
                "iam:ListAttachedRolePolicies",
                "sns:Subscribe",
                "iam:ListRolePolicies",
                "events:*",
                "iam:GetRole",
                "iam:GetPolicy",
                "cloudformation:*",
                "cloudwatch:*",
                "iot:ListPolicies",
                "iam:GetRolePolicy",
                "xray:PutTelemetryRecords",
                "tag:GetResources",
                "iam:PassRole",
                "s3:*",
                "iam:ListRoles",
                "lambda:*"
            ],
            "Resource": "*"
        }
    ]
}
```

- Then run this to deploy

```sh
./gradlew deploy
```

## Test your converter

- Drop any audio files and see them converted to .mp4 files :)
