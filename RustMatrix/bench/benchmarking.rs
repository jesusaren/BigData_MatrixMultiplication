use criterion::{criterion_group, criterion_main, Criterion};
use matrixrust::matrix_multiplication;
use rand::Rng;

fn generate_random_matrix(n: usize) -> Vec<Vec<f64>> {
    let mut rng = rand::thread_rng();
    let mut matrix = vec![vec![0.0; n]; n];

    for i in 0..n {
        for j in 0..n {
            matrix[i][j] = rng.gen::<f64>();
        }
    }

    matrix
}

fn bench_matrix_multiplication(c: &mut Criterion) {
    let n = 1024;
    let a: Vec<Vec<f64>> = generate_random_matrix(n);
    let b: Vec<Vec<f64>> = generate_random_matrix(n);

    let a_clone = a.clone();
    let b_clone = b.clone();

    c.bench_function("matrix_multiplication", |b| {
        b.iter(|| {
            let _result = matrix_multiplication(&a_clone, &b_clone);
        });
    });
}

criterion_group!(benches, bench_matrix_multiplication);
criterion_main!(benches);