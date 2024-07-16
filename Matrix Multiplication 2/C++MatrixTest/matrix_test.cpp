#include <iostream>
#include <vector>
#include <cstdlib>
#include <ctime>
#include <chrono>

using Matrix = std::vector<std::vector<double>>;

Matrix generate_matrix(int size) {
    Matrix matrix(size, std::vector<double>(size));
    for (int i = 0; i < size; ++i) {
        for (int j = 0; j < size; ++j) {
            matrix[i][j] = static_cast<double>(rand()) / RAND_MAX;
        }
    }
    return matrix;
}

double run_experiment(int matrix_size, int num_runs) {
    Matrix A = generate_matrix(matrix_size);
    Matrix B = generate_matrix(matrix_size);

    double total_time = 0;
    for (int i = 0; i < num_runs; ++i) {
        auto start = std::chrono::high_resolution_clock::now();
        matrix_multiply(A, B);
        auto end = std::chrono::high_resolution_clock::now();
        total_time += std::chrono::duration<double>(end - start).count();
    }

    return total_time / num_runs;
}

int main() {
    srand(static_cast<unsigned>(time(0)));
    int sizes[] = {100, 200, 300, 400, 500};
    int num_runs = 10;

    for (int size : sizes) {
        double avg_time = run_experiment(size, num_runs);
        std::cout << "Average time for size " << size << ": " << avg_time << " seconds" << std::endl;
    }

    return 0;
}



