This project is a benchmark for measuring the performance overhead of the lsp4j library. It uses lsp4j to implement a simple Java language server that only supports the `textDocument/completion` request. It returns a fixed completion response (~3M size) without any computation. This way, the completion roundtrip time reflects mainly the lsp4j library’s cost, such as serialization.

To run the benchmark, follow these steps:

- Clone this repository and open it in VS Code.
- Run BenchmarkMain as the main application. You will see the completion time in the output.
```
Total completion request: 10
Total time: 373ms
Average time: 37ms
```
