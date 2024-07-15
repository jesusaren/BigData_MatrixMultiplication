#include <iostream>
#include <cstdlib>
#include <chrono>
#include <vector>
#include "matrix_multiplication.hpp"

const int n = 1024; // Define matrix size

int main() {
    std::vector<std::vector<double>> a(n, std::vector<double>(n));
    std::vector<std::vector<double>> b(n, std::vector<double>(n));
    std::vector<std::vector<double>> c(n, std::vector<double>(n));

    const int iterations = 10;
    double minOps = std::numeric_limits<double>::max();
    double maxOps = 0;
    double totalOps = 0;
    double totalTime = 0;

    for (int iteration = 0; iteration < iterations; ++iteration) {
        for (int i = 0; i < n; ++i) {
            for (int j = 0; j < n; ++j) {
                a[i][j] = static_cast<double>(rand()) / RAND_MAX;
                b[i][j] = static_cast<double>(rand()) / RAND_MAX;
                c[i][j] = 0;
            }
        }

        auto startTime = std::chrono::high_resolution_clock::now();
        multiplyMatrices(a, b, c);
        auto endTime = std::chrono::high_resolution_clock::now();
        auto duration = std::chrono::duration_cast<std::chrono::microseconds>(endTime - startTime).count();
        double seconds = duration / 1e6;
        std::cout << "Duration per iteration: " << seconds << " seconds" << std::endl;

        totalTime += seconds;
    }
    double avgTime = totalTime / iterations;


    std::cout << "Average Time: " << avgTime << " seconds" << std::endl;


    return 0;
}

