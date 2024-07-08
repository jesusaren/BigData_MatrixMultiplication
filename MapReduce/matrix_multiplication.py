import os
import time

from mrjob.job import MRJob
from mrjob.step import MRStep

class MatrixMultiplication(MRJob):

    def mapper(self, _, line):
        matrix, row, col, value = line.split(',')

        if matrix == 'A':
            for k in range(self.matrix_b_cols):
                yield (int(row), k), (matrix, int(col), float(value))
        else:
            for i in range(self.matrix_a_rows):
                yield (i, int(col)), (matrix, int(row), float(value))

    def reducer(self, key, values):
        result = 0.0
        matrix_a_values = []
        matrix_b_values = []

        for value in values:
            matrix, index, val = value

            if matrix == 'A':
                matrix_a_values.append((index, val))
            else:
                matrix_b_values.append((index, val))

        for a in matrix_a_values:
            for b in matrix_b_values:
                if a[0] == b[0]:
                    result += a[1] * b[1]
        yield key, result

    def steps(self):
        return [
            MRStep(mapper_init=self.mapper_init,
                   mapper=self.mapper,
                   reducer=self.reducer)
        ]

    def mapper_init(self):
        current_directory = os.path.dirname(os.path.realpath(__file__))
        with open(os.path.join(current_directory, 'matrix_a.txt'), 'r') as file:
            self.matrix_a_rows, self.matrix_a_cols = map(int, file.readline().split(','))

        with open(os.path.join(current_directory, 'matrix_b.txt'), 'r') as file:
            self.matrix_b_rows, self.matrix_b_cols = map(int, file.readline().split(','))


if __name__ == '__main__':
    start_time = time.time()
    job = MatrixMultiplication()
    with job.make_runner() as runner:
        runner.run()
    end_time = time.time()
    execution_time = end_time - start_time
    print(f"Calculation time: {execution_time} seconds")
