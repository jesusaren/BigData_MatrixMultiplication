If we want to use a specific matrix we have to execute the matrix_processor.py file changing the matrix url.
To execute the MapReduce matrix multiplication we have to write this command in terminal: python .\matrix_multiplication.py .\matrices.txt
And if we want to see the result of the matrix multiplication it's necessary to remove this line: with job.make_runner() as runner:
