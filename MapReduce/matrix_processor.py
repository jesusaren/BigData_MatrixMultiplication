class MatrixProcessor:
    def __init__(self, file_a, file_b):
        self.file_a = file_a
        self.file_b = file_b

    def processor_matrices(self, input_a, input_b, matrix_file):
        with open(matrix_file, 'w') as output_file:
            self.processor_file(input_a, self.file_a, output_file, 'A')
            self.processor_file(input_b, self.file_b, output_file, 'B')

    def processor_file(self, input, size_file, output_file, character):
        with open(input, 'r') as file:
            first_line = True
            for line in file:
                if not line.startswith('%'):
                    if first_line:
                        elements = line.split()
                        with open(size_file, 'w') as matrix_size_file:
                            matrix_size_file.write(f"{elements[0]},{elements[1]}\n")
                        first_line = False
                    else:
                        elements = line.split()
                        output_file.write(f"{character},{int(elements[0]) - 1},{int(elements[1]) - 1},{elements[2]}\n")

if __name__ == '__main__':
    matrices = MatrixProcessor('matrix_a.txt', 'matrix_b.txt')
    matrices.processor_matrices('resources/arc130.mtx', 'resources/arc130.mtx', 'matrices.txt')
