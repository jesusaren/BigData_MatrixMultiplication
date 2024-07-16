import time
import numpy as np
from matrix_multiply import matrix_multiply

def generate_matrix(size):
    return np.random.rand(size, size)

def run_experiment(matrix_size, num_runs):
    A = generate_matrix(matrix_size)
    B = generate_matrix(matrix_size)
    
    times = []
    for _ in range(num_runs):
        start_time = time.time()
        matrix_multiply(A, B)
        end_time = time.time()
        times.append(end_time - start_time)
    
    avg_time = sum(times) / num_runs
    return avg_time

if __name__ == "__main__":
    sizes = [100, 200, 300, 400, 500]
    num_runs = 10

    for size in sizes:
        avg_time = run_experiment(size, num_runs)
        print(f"Average time for size {size}: {avg_time:.6f} seconds")