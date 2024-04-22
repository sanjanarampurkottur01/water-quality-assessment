from flask import Flask, request, jsonify
import boto3
import time

app = Flask(__name__)

# AWS credentials and region
AWS_ACCESS_KEY_ID = ''
AWS_SECRET_ACCESS_KEY = ''
AWS_REGION = ''
AWS_SESSION_TOKEN = ''

# Initialize Boto3 Athena client
athena_client = boto3.client('athena',
                             aws_access_key_id=AWS_ACCESS_KEY_ID,
                             aws_secret_access_key=AWS_SECRET_ACCESS_KEY,
                             region_name=AWS_REGION,
                             aws_session_token=AWS_SESSION_TOKEN)

# Function to execute Athena query
def execute_athena_query(query):
    response = athena_client.start_query_execution(
        QueryString=query,
        QueryExecutionContext={
            'Database': 'ocean'
        },
        ResultConfiguration={
            'OutputLocation': 's3://b00957180oceanbucket/ocean_query_output/'
        }
    )
    query_execution_id = response['QueryExecutionId']
    return query_execution_id

# Function to get query results
def get_query_results(query_execution_id):
    while True:
        response = athena_client.get_query_execution(
            QueryExecutionId=query_execution_id
        )
        state = response['QueryExecution']['Status']['State']
        if state in ['SUCCEEDED', 'FAILED', 'CANCELLED']:
            break
        time.sleep(1)  # Wait for 1 second before checking again
        
    if state == 'SUCCEEDED':
        result_response = athena_client.get_query_results(
            QueryExecutionId=query_execution_id
        )
        # Extracting column names and data from the result set
        columns = [col['Label'] for col in result_response['ResultSet']['ResultSetMetadata']['ColumnInfo']]
        data = []
        for row in result_response['ResultSet']['Rows'][1:]:
            data.append([field['VarCharValue'] for field in row['Data']])
        return {'columns': columns, 'data': data}
    else:
        error_message = response['QueryExecution']['Status']['StateChangeReason']
        raise Exception(f'Query execution failed: {error_message}')

@app.route('/query', methods=['POST'])
def execute_query():
    data = request.get_json()
    query = data.get('query')
    if not query:
        return jsonify({'error': 'Query is required'}), 400
    
    try:
        query_execution_id = execute_athena_query(query)
        result = get_query_results(query_execution_id)
        return jsonify(result), 200
    except Exception as e:
        return jsonify({'error': str(e)}), 500

if __name__ == '__main__':
    app.run(host='0.0.0.0', port=80)
