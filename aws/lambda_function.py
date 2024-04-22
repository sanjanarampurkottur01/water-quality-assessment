import json
import boto3

def lambda_handler(event, context):
    glue = boto3.client('glue')
    
    response = glue.start_job_run(
        JobName='Data Cleaning Job - CSCI5409'
    )
    
    print(response)

    # You can return a response if needed
    return {
        'statusCode': 200,
        'body': 'Glue job started successfully'
    }
